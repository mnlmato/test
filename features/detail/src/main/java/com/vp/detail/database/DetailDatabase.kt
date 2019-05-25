package com.vp.detail.database

import com.vp.core.datasource.FavouriteMovieRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class DetailDatabase {

    private var realm: Realm

    init {
        realm = Realm.getInstance(realmConfiguration())
    }

    private fun realmConfiguration() = RealmConfiguration.Builder()
            .name(Realm.DEFAULT_REALM_NAME)
            .deleteRealmIfMigrationNeeded()
            .build()

    fun saveMovie(movie: FavouriteMovieRealm) {
        realm.executeTransaction {
            it.copyToRealmOrUpdate(movie)
        }
    }

    fun getAllFavouritesMovie(): RealmResults<FavouriteMovieRealm> {
        return realm.where(FavouriteMovieRealm::class.java).findAll()
    }
}