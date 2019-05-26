package com.vp.favorites.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.core.vm.model.FavouriteMovieModel
import com.vp.core.vm.model.ScreenStatus
import com.vp.favorites.R
import com.vp.favorites.ui.adapter.FavouritesMoviesAdapter
import com.vp.favorites.vm.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        initViewModel()
        observerFavouritesMovies()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
    }

    private fun observerFavouritesMovies() {
        viewModel.screenStatus.observe(this, Observer(::onScreenStateReceived))
    }

    private fun onScreenStateReceived(screenStatus: ScreenStatus) {
        when (screenStatus) {
            is ScreenStatus.LOADING -> showSpinner()
            is ScreenStatus.SUCCESS<*> ->
                showFavouritesMovies(screenStatus.element as List<FavouriteMovieModel>)
        }
    }

    private fun showSpinner() {
        activity_favorite_progress_bar.visibility = View.VISIBLE
        activity_favorite_recycler_view.visibility = View.GONE
    }

    private fun showFavouritesMovies(movies: List<FavouriteMovieModel>) {
        showRecyclerView()

        val adapter = FavouritesMoviesAdapter(movies)
        activity_favorite_recycler_view.adapter = adapter
        activity_favorite_recycler_view.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(this)
        activity_favorite_recycler_view.layoutManager = linearLayoutManager
    }

    private fun showRecyclerView() {
        activity_favorite_progress_bar.visibility = View.GONE
        activity_favorite_recycler_view.visibility = View.VISIBLE
    }
}