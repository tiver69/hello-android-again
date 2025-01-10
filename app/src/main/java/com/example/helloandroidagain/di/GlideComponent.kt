package com.example.helloandroidagain.di

import com.example.helloandroidagain.presentation.component.glide.CustomCacheLoader
import dagger.Subcomponent

@Subcomponent(modules = [GlideModule::class])
interface GlideComponent {
    fun getCustomCacheLoader(): CustomCacheLoader

    @Subcomponent.Factory
    interface Factory {
        fun create(): GlideComponent
    }
}