package com.vp.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.vp.core.ui.constants.IntentQueryKeys
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.run

class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var detailViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingActivityComponents()
        fetchFavouriteMovie()
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

    private fun observerMovieTitle() {
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
    }

    private fun fetchFavouriteMovie() {
        detailViewModel.fetchDetails(getMovieId())
    }

    private fun getMovieId(): String {
        return intent?.data?.getQueryParameter(IntentQueryKeys.IMDB_MOVIE_ID) ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
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
}
