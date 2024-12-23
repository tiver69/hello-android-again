package com.example.helloandroidagain.component.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.example.helloandroidagain.component.glide.CustomCacheLoader.SQLiteCacheFetcher.Companion.SKIP_CUSTOM_CACHE
import com.example.helloandroidagain.service.ImageCacheService

class CustomCacheLoader(private val context: Context) : ModelLoader<String, Bitmap> {
    private val imageCacheService = ImageCacheService(context)

    override fun buildLoadData(
        model: String,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<Bitmap>? {
        val shouldSkipCustomCache = options.get(SKIP_CUSTOM_CACHE) ?: false
        return if (shouldSkipCustomCache) {
            null
        } else {
            ModelLoader.LoadData(
                ObjectKey(model),
                SQLiteCacheFetcher(model, context, imageCacheService)
            )
        }
    }

    override fun handles(model: String): Boolean = true

    class Factory(private val context: Context) : ModelLoaderFactory<String, Bitmap> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, Bitmap> {
            return CustomCacheLoader(context)
        }

        override fun teardown() {
        }
    }

    class SQLiteCacheFetcher(
        private val url: String,
        private val context: Context,
        private val imageCacheService: ImageCacheService
    ) : DataFetcher<Bitmap> {

        override fun loadData(
            priority: Priority,
            callback: DataFetcher.DataCallback<in Bitmap>
        ) {
            val cachedImage = imageCacheService.loadImage(url)
            if (cachedImage != null) {
                callback.onDataReady(cachedImage)
            } else {
                Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(RequestOptions().set(SKIP_CUSTOM_CACHE, true))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .circleCrop()
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            imageCacheService.saveImage(url, resource)
                            callback.onDataReady(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // No action required
                        }
                    })
                callback.onLoadFailed(Exception("Failed to load image from custom cache"))
            }
        }

        override fun cleanup() {}
        override fun cancel() {}
        override fun getDataClass() = Bitmap::class.java
        override fun getDataSource() = DataSource.LOCAL

        companion object {
            val SKIP_CUSTOM_CACHE: Option<Boolean> =
                Option.memory("skip_custom_cache")
        }

    }
}