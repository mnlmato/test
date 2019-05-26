package com.vp.detail.database

import com.vp.core.datasource.RealmProvider
import com.vp.core.datasource.model.FavouriteMovieRealm
import io.realm.Realm

class DetailDatabase {

    private var realm: Realm = RealmProvider.getRealmInstance()

    fun saveMovie(movie: FavouriteMovieRealm) {
        realm.executeTransaction {
            it.copyToRealmOrUpdate(movie)
        }
    }
}