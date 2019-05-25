package com.vp.detail.repository.mapper

import com.vp.core.datasource.model.FavouriteMovieRealm
import com.vp.core.mapper.BaseMapper
import com.vp.core.vm.model.FavouriteMovieModel

object DetailMovieMapper: BaseMapper<FavouriteMovieRealm, FavouriteMovieModel> {
    override fun mapFrom(type: FavouriteMovieRealm): FavouriteMovieModel =
            FavouriteMovieModel(
                    title = type.title,
                    year = type.year,
                    runtime = type.runtime,
                    director = type.director,
                    plot = type.plot,
                    poster = type.poster
            )
}