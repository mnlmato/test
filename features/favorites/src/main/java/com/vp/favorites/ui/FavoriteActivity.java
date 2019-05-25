package com.vp.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import com.vp.core.vm.model.FavouriteMovieModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        bindViews();
        populateMoviesrecyclerview();
    }

    private void bindViews() {
        this.recyclerView = findViewById(R.id.activity_favorite_recycler_view);
    }

    private void populateMoviesrecyclerview() {
        FavouritesMoviesAdapter moviesAdapter = new FavouritesMoviesAdapter(getMoviesFake());
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<FavouriteMovieModel> getMoviesFake() {
        List<FavouriteMovieModel> movies = new ArrayList<>();
        movies.add(new FavouriteMovieModel("1", "Title", "Lorem", "Ipsum", "", "", ""));

        return movies;
    }
}
