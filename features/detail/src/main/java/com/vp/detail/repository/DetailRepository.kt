package com.vp.detail.repository

import com.vp.core.vm.model.FavouriteMovieModel

interface DetailRepository {

    fun saveMovie(favouriteMovie: FavouriteMovieModel)
}