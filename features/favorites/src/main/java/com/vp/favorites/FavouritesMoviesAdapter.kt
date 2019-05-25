package com.vp.favorites

import android.view.View
import com.vp.core.ui.adapter.BaseAdapter
import com.vp.core.vm.model.FavouriteMovieModel
import kotlinx.android.synthetic.main.item_favourite_movie.view.*

class FavouritesMoviesAdapter constructor(movies: List<FavouriteMovieModel>):
        BaseAdapter<FavouriteMovieModel>(movies, R.layout.item_favourite_movie) {

    override fun View.bind(item: FavouriteMovieModel) {
        item_favourite_detail_title.text = item.title
        item_favourite_detail_year.text = item.year
        item_favourite_detail_director.text = item.director
        item_favourite_detail_plot.text = item.plot
        item_favourite_detail_runtime.text = item.runtime
    }
}