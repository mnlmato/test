package com.vp.detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vp.detail.model.MovieDetail
import javax.inject.Inject

class FavouriteMovieViewModel @Inject constructor() : ViewModel() {

    fun getMovies(): List<MovieDetail>? {
        Log.i("test", "test")

        return null
    }
}
