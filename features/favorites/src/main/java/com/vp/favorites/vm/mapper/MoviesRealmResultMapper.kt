package com.vp.favorites.vm.mapper

import com.vp.core.datasource.model.FavouriteMovieRealm
import com.vp.core.mapper.BaseMapper
import com.vp.core.vm.model.FavouriteMovieModel
import io.realm.RealmResults

object MoviesRealmResultMapper: BaseMapper<RealmResults<FavouriteMovieRealm>,List<FavouriteMovieModel>> {

    override fun mapFrom(type: RealmResults<FavouriteMovieRealm>): List<FavouriteMovieModel> {
        val movies = mutableListOf<FavouriteMovieModel>()

        type.forEach {
            movies.add(
                    FavouriteMovieModel(
                            id = it.id,
                            title = it.title,
                            year = it.year,
                            runtime = it.runtime,
                            director = it.director,
                            plot = it.plot,
                            poster = it.poster
                    )
            )
        }

        return movies
    }
}