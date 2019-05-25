package com.vp.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.run

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var detailViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initQueryProvider()
        bindingActivityComponents()
        observerMovieTitle()
    }

    private fun bindingActivityComponents() {
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        initViewModel()

        binding.viewModel = detailViewModel
        binding.setLifecycleOwner(this)
    }

    private fun initViewModel() {
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
    }

    private fun initQueryProvider() {
        queryProvider = this
    }

    private fun observerMovieTitle() {
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun getMovieId(): String {
        return intent?.data?.getQueryParameter("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == R.id.star) {
                handleFavouriteButton()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleFavouriteButton() {
        detailViewModel.handleFavouriteButton()
    }

    /**
     *  Initialize here the queryProvider is an error because the DetailViewModel doesn´t must to kwon
     *  the activities.  If the ViewModel needs a QueryProvider, dagger must be to provide it.
     *
     *  If the DetailsViewModel is shared by other activities, then could have a memory leak when
     *  the activities is destroyed.
     * */
    companion object {
        lateinit var queryProvider: QueryProvider
    }
}
