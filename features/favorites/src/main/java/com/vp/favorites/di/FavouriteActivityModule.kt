package com.vp.favorites.di

import com.vp.favorites.ui.FavoriteActivity
import dagger.Module

@Module
abstract class FavouriteActivityModule {
    //@ContributesAndroidInjector(modules = [FavoriteViewModelModule::class])
    abstract fun bindDetailActivity(): FavoriteActivity
}
