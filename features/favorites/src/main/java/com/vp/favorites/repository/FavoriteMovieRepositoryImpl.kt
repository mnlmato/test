package com.vp.favorites.repository

import com.vp.core.vm.model.FavouriteMovieModel
import com.vp.favorites.datasource.FavoriteMovieDatabase
import com.vp.favorites.repository.mapper.RealmMoviesMapper
import com.vp.favorites.vm.FavoriteMovieRepository

class FavoriteMovieRepositoryImpl constructor(
        private val favoriteMovieDatabase: FavoriteMovieDatabase
): FavoriteMovieRepository {

    override fun getAllMovies(): List<FavouriteMovieModel> =
            RealmMoviesMapper.mapFrom(favoriteMovieDatabase.getMoviesFromDatabase()!!)
}
