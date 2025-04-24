package com.example.helloandroidagain.tournament_presentation.di

import com.example.helloandroidagain.tournament_presentation.component.glide.CustomCacheLoader
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GlideEntryPoint {
    fun getCustomCacheLoader(): CustomCacheLoader
}