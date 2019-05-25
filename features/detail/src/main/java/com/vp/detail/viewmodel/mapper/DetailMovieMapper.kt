package com.vp.detail.viewmodel.mapper

import com.vp.core.datasource.FavouriteMovieRealm
import com.vp.core.mapper.BaseMapper
import com.vp.detail.model.MovieDetail

object DetailMovieMapper : BaseMapper<MovieDetail, FavouriteMovieRealm> {

    override fun mapFrom(type: MovieDetail): FavouriteMovieRealm =
            FavouriteMovieRealm(
                    id = type.id,
                    title = type.title,
                    year = type.year,
                    runtime = type.runtime,
                    director = type.director,
                    plot = type.plot,
                    poster = type.poster
            )
}