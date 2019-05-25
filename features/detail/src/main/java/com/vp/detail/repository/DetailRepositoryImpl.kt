package com.vp.detail.repository

import com.vp.detail.database.DetailDatabase
import com.vp.detail.viewmodel.model.FavouriteMovieModel
import javax.inject.Inject

class DetailRepositoryImpl (private val detailDatabase: DetailDatabase): DetailRepository {

    override fun saveMovie(favouriteMovie: FavouriteMovieModel) {
        detailDatabase.getAllFavouritesMovie()
    }
}