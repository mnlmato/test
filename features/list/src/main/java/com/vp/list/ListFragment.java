package com.vp.list;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.vp.list.viewmodel.SearchResult;
import com.vp.list.viewmodel.ListViewModel;

import javax.inject.Inject;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.android.support.AndroidSupportInjection;

public class ListFragment extends Fragment implements GridPagingScrollListener.LoadMoreItemsListener,
        ListAdapter.OnItemClickListener {

    public static final String TAG = "ListFragment";
    private static final String CURRENT_QUERY = "current_query";

    @Inject
    ViewModelProvider.Factory factory;

    private ListViewModel listViewModel;

    private GridPagingScrollListener gridPagingScrollListener;
    private ListAdapter listAdapter;
    private ViewAnimator viewAnimator;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String currentQuery = "Interview";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        initViewModel();
        initCurrentQuery(savedInstanceState);
    }

    private void initViewModel() {
        listViewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);
        setListeners(view);
        initMoviesList();
        handleScreenMoviesLoad();
    }

    private void bindViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        viewAnimator = view.findViewById(R.id.viewAnimator);
        progressBar = view.findViewById(R.id.progressBar);
        errorTextView = view.findViewById(R.id.errorText);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
    }

    private void setListeners(@NonNull View view) {
        swipeRefreshLayout.setOnRefreshListener(this::handleSwipeRefreshLayout);
        setBottomNavigationListener(view);
    }

    private void setBottomNavigationListener(@NonNull View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onFavoritesBottomClick);
    }

    private boolean onFavoritesBottomClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.favorites) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("app://movies/favorites"));
            intent.setPackage(requireContext().getPackageName());
            startActivity(intent);
        }

        return true;
    }

    private void handleSwipeRefreshLayout() {
        listViewModel.searchMoviesByTitle(currentQuery, 1);
        recyclerView.smoothScrollToPosition(0);
        swipeRefreshLayout.setRefreshing(false);
        errorTextView.setVisibility(View.INVISIBLE);
    }

    private void initCurrentQuery(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentQuery = savedInstanceState.getString(CURRENT_QUERY);
        }
    }

    private void initMoviesList() {
        listAdapter = new ListAdapter();
        listAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);

        // Pagination
        gridPagingScrollListener = new GridPagingScrollListener(layoutManager);
        gridPagingScrollListener.setLoadMoreItemsListener(this);
        recyclerView.addOnScrollListener(gridPagingScrollListener);
    }

    private void handleScreenMoviesLoad() {
        listViewModel.searchMoviesByTitle(currentQuery, 1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        observeMoviesLiveData();
    }

    private void observeMoviesLiveData() {
        listViewModel.observeMovies().observe(this, this::onSearchResultReceived);
    }

    private void onSearchResultReceived(@NonNull SearchResult searchResult) {
        switch (searchResult.getListState()) {
            case LOADED: {
                setItemsData(searchResult);
                showList();
                break;
            }
            case IN_PROGRESS: {
                showProgressBar();
                break;
            }
            default: {
                showError();
            }
        }
        gridPagingScrollListener.markLoading(false);
    }

    private void setItemsData(@NonNull SearchResult searchResult) {
        listAdapter.setItems(searchResult.getItems());

        if (searchResult.getTotalResult() <= listAdapter.getItemCount()) {
            gridPagingScrollListener.markLastPage(true);
        }
    }

    private void showList() {
        errorTextView.setVisibility(View.INVISIBLE);
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(recyclerView));
    }

    private void showProgressBar() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(progressBar));
    }

    private void showError() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(errorTextView));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_QUERY, currentQuery);
    }

    @Override
    public void loadMoreItems(int page) {
        gridPagingScrollListener.markLoading(true);
        listViewModel.searchMoviesByTitle(currentQuery, page);
    }

    void submitSearchQuery(@NonNull final String query) {
        currentQuery = query;
        listAdapter.clearItems();
        listViewModel.searchMoviesByTitle(query, 1);

        showProgressBar();
    }

    @Override
    public void onItemClick(String imdbID) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(buildDetailDestination(imdbID)));
        intent.setPackage(requireContext().getPackageName());
        startActivity(intent);
    }

    private String buildDetailDestination(String imdbID) {
        return new Uri.Builder()
                .scheme("app")
                .authority("movies")
                .appendPath("detail")
                .appendQueryParameter("imdbID", imdbID)
                .fragment("section-name")
                .toString();
    }
}
