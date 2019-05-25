package com.vp.core.datasource.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class FavouriteMovieRealm(@PrimaryKey var id: String? = "",
                               var title: String? = "",
                               var year: String? = "",
                               var runtime: String? = "",
                               var director: String? = "",
                               var plot: String? = "",
                               var poster: String? = ""
) : RealmObject()
