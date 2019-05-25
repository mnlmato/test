package com.vp.detail.di

import com.vp.detail.database.DetailDatabase
import dagger.Module
import dagger.Provides

@Module
class DetailDatabaseModule {

    @Provides
    fun providesDetailDatabase() = DetailDatabase()
}