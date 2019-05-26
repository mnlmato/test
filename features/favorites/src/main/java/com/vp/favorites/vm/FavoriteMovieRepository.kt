package com.vp.favorites.vm

import com.vp.core.vm.model.FavouriteMovieModel

interface FavoriteMovieRepository {
    fun getAllMovies(): List<FavouriteMovieModel>
}