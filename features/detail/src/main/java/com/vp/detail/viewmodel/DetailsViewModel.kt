package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.core.datasource.model.FavouriteMovieRealm
import com.vp.detail.DetailActivity
import com.vp.detail.database.DetailDatabase
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import com.vp.detail.viewmodel.mapper.DetailMovieMapper
import com.vp.core.vm.model.FavouriteMovieModel
import io.realm.RealmResults
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class DetailsViewModel @Inject constructor(private val detailService: DetailService,
                                           private val detailDatabase: DetailDatabase) : ViewModel() {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails(movieId: String) {
        loadingState.value = LoadingState.IN_PROGRESS

        detailService.getMovie(movieId).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                details.postValue(response?.body())

                response?.body()?.title?.let {
                    title.postValue(it)
                }

                loadingState.value = LoadingState.LOADED
            }

            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                details.postValue(null)
                loadingState.value = LoadingState.ERROR
            }
        })
    }

    fun handleFavouriteButton() {
        details.value?.let {
            val favouriteMovie = DetailMovieMapper.mapFrom(details.value!!)
            detailDatabase.saveMovie(favouriteMovie)
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}