package com.example.helloandroidagain.di

import com.example.helloandroidagain.presentation.component.glide.CustomCacheLoader
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GlideEntryPoint {
    fun getCustomCacheLoader(): CustomCacheLoader
}