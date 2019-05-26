package com.vp.favorites.datasource.provider

import com.vp.core.datasource.RealmProvider
import com.vp.favorites.datasource.FavoriteMovieDatabase

object FavoriteMovieDatabaseProvider {

    fun provideFavoriteMovieDatabase() =
            FavoriteMovieDatabase(RealmProvider.getRealmInstance())
}