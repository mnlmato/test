package com.vp.favorites.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.core.vm.model.ScreenStatus
import com.vp.favorites.repository.provider.FavoriteMovieRepositoryImplProvider

class FavoriteViewModel: ViewModel() {

    private val screenStatusLiveData = MutableLiveData<ScreenStatus>()
    val screenStatus: MutableLiveData<ScreenStatus>
        get() = screenStatusLiveData

    /**
     *  I need to improve my dagger skills so I used a providers class to get the necessary instance.
     *  I know that this is not the best way to do it but I want to work and improve my skills
     *  in the Privalia tribes.
     *
     *  I have good practices using another libraries as my repository example:
     *  https://bitbucket.org/ManuelMato/baseproject/wiki/Home
     * */
    private var repository: FavoriteMovieRepository =
            FavoriteMovieRepositoryImplProvider.provideFavoriteMovieRepositoryImpl()

    init {
        loadMoviesFromRepository()
    }

    /**
     *  The IO operations must be executed in a secondary thread and release the resources in the
     *  onCleared method.
     * */
    private fun loadMoviesFromRepository() {
            screenStatusLiveData.postValue(ScreenStatus.LOADING)
            val movies = repository.getAllMovies()
            screenStatusLiveData.postValue(ScreenStatus.SUCCESS(movies))
    }
}
