package com.example.helloandroidagain.presentation.component.glide

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.helloandroidagain.App

@GlideModule
class CacheGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val glideComponent = (context.applicationContext as App).appComponent.glideSubcomponentFactory().create()
        registry.prepend(
            String::class.java,
            Bitmap::class.java,
            CustomCacheLoader.Factory(glideComponent)
        )
    }
}