package com.vp.favorites.di

import com.vp.favorites.FavoriteActivity

abstract class FavouriteActivityModule {
    //@ContributesAndroidInjector(modules = [])
    abstract fun bindDetailActivity(): FavoriteActivity
}