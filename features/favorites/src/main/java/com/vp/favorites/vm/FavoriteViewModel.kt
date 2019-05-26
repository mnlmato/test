package com.vp.favorites.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.core.datasource.RealmProvider
import com.vp.core.datasource.model.FavouriteMovieRealm
import com.vp.core.vm.model.FavouriteMovieModel
import com.vp.core.vm.model.ScreenStatus
import com.vp.favorites.vm.mapper.MoviesRealmResultMapper
import io.realm.RealmResults

class FavoriteViewModel: ViewModel() {

    private val screenStatusLiveData = MutableLiveData<ScreenStatus>()

    val screenStatus: MutableLiveData<ScreenStatus>
        get() = screenStatusLiveData

    init {
        loadMoviesFromRepository()
    }

    private fun loadMoviesFromRepository() {
        screenStatusLiveData.postValue(ScreenStatus.LOADING)

        val movies = getMoviesFromRepository()

        screenStatusLiveData.postValue(ScreenStatus.SUCCESS(movies))
    }

    /**
     *  It is necessary to create a class Repository and Database class but I am not a Dagger master
     *  to do the setup.
     * */
    private fun getMoviesFromRepository(): List<FavouriteMovieModel> {
        val moviesDatabase = getMoviesFromDatabase()

        return MoviesRealmResultMapper.mapFrom(moviesDatabase)
    }

    private fun getMoviesFromDatabase(): RealmResults<FavouriteMovieRealm> =
            RealmProvider.getRealmInstance().where(FavouriteMovieRealm::class.java).findAll()
}
