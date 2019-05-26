package com.vp.favorites.repository.provider

import com.vp.favorites.datasource.provider.FavoriteMovieDatabaseProvider
import com.vp.favorites.repository.FavoriteMovieRepositoryImpl

object FavoriteMovieRepositoryImplProvider {

    fun provideFavoriteMovieRepositoryImpl() =
            FavoriteMovieRepositoryImpl(FavoriteMovieDatabaseProvider.provideFavoriteMovieDatabase())
}