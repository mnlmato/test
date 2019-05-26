package com.vp.favorites.datasource

import com.vp.core.datasource.RealmProvider
import com.vp.core.datasource.model.FavouriteMovieRealm
import io.realm.Realm
import io.realm.RealmResults

class FavoriteMovieDatabase constructor(private val realm: Realm) {

    /**
     *  The IO operations must be run in a secondary thread but there is not any library as
     *  RxJava, RxKotlin or Corotuines (It is better Kotlin 1.3, not 1.2.60)
     *
     *  This operation can be block the UI thread and freeze it
     * */
    fun getMoviesFromDatabase(): RealmResults<FavouriteMovieRealm> {
        realm.where(FavouriteMovieRealm::class.java).findAllAsync()

        return RealmProvider.getRealmInstance().where(FavouriteMovieRealm::class.java).findAll()
    }
}