package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.core.datasource.FavouriteMovieRealm
import com.vp.detail.DetailActivity
import com.vp.detail.database.DetailDatabase
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import com.vp.detail.viewmodel.mapper.DetailMovieMapper
import com.vp.detail.viewmodel.model.FavouriteMovieModel
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

    /**
     *  Is not necessary that the activity calls this method, so it is better to convert fetchDetails
     *  in private method and fetch the data when the activity initialize the viewModel
     *
     *  Is better to create too a Sealed class with the response type because in this case only is
     *  necessary one livedata
     *
     *  The architecture is not good because the presenter layer cannot to call directly the datasource layer.
     *  Is necessary a repository layer and a business layer, but I don´t know if in the MVVM architecture,
     *  the viewModel can to do the business logic.
     * */
    init {
        fetchDetails()
    }

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    private fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(DetailActivity.queryProvider.getMovieId()).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
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

    private fun mapper(realmMovies: RealmResults<FavouriteMovieRealm>): List<FavouriteMovieModel> {
        val movies = mutableListOf<FavouriteMovieModel>()

        realmMovies.forEach {
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

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}