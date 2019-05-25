package com.vp.detail.repository

import com.vp.detail.viewmodel.model.FavouriteMovieModel

interface DetailRepository {

    fun saveMovie(favouriteMovie: FavouriteMovieModel)
}