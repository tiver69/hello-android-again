package com.thefork.challenge.search.di

import com.thefork.challenge.search.SearchContract
import com.thefork.challenge.search.SearchPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SearchModule {

    @Singleton
    @Binds
    fun provideSearchPresenter(impl: SearchPresenter): SearchContract.Presenter
}
