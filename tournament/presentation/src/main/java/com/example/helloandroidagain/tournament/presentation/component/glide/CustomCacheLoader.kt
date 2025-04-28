package com.example.helloandroidagain.tournament.presentation.component.glide

import android.content.Context
import android.database.sqlite.SQLiteException
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
import com.example.helloandroidagain.tournament.domain.repository.ImageCacheRepository
import com.example.helloandroidagain.tournament.presentation.component.glide.CustomCacheLoader.SQLiteCacheFetcher.Companion.SKIP_CUSTOM_CACHE
import com.example.helloandroidagain.tournament.presentation.di.GlideEntryPoint
import dagger.hilt.EntryPoints
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

class CustomCacheLoader @Inject constructor(
    private val context: Context,
    private val imageCacheRepository: ImageCacheRepository
) :
    ModelLoader<String, Bitmap> {

    override fun buildLoadData(
        model: String,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<Bitmap>? {
        return if (options.get(SKIP_CUSTOM_CACHE) == true) {
            null
        } else {
            ModelLoader.LoadData(
                ObjectKey(model),
                SQLiteCacheFetcher(model, context, imageCacheRepository)
            )
        }
    }

    override fun handles(model: String): Boolean = true

    class Factory(private val context: Context) : ModelLoaderFactory<String, Bitmap> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, Bitmap> {
            val entryPoint =
                EntryPoints.get(context.applicationContext, GlideEntryPoint::class.java)
            return entryPoint.getCustomCacheLoader()
        }

        override fun teardown() {
        }
    }

    class SQLiteCacheFetcher(
        private val url: String,
        private val context: Context,
        private val imageCacheRepository: ImageCacheRepository
    ) : DataFetcher<Bitmap> {
        private var cacheServiceJob = Job()

        override fun loadData(
            priority: Priority,
            callback: DataFetcher.DataCallback<in Bitmap>
        ) {
            CoroutineScope(cacheServiceJob).launch {
                try {
                    imageCacheRepository.loadImage(url)?.let {
                        callback.onDataReady(it)
                    } ?: loadImageToCache(callback)
                } catch (exc: IllegalStateException) {
                    callback.onLoadFailed(Exception("Failed to load image from custom cache"))
                } catch (exc: SQLiteException) {
                    loadImageToCache(callback)
                }
            }
        }

        private fun loadImageToCache(callback: DataFetcher.DataCallback<in Bitmap>) {
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
                        CoroutineScope(cacheServiceJob).launch {
                            try {
                                imageCacheRepository.saveImage(url, resource)
                                callback.onDataReady(resource)
                            } catch (exc: SQLiteException) {
                                callback.onLoadFailed(
                                    Exception("Failed to save image to custom cache")
                                )
                            }
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // No action required
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        callback.onLoadFailed(Exception("Failed to save image to custom cache"))
                    }
                })
        }

        override fun cleanup() {}
        override fun cancel() {
            cacheServiceJob.cancel()
        }

        override fun getDataClass() = Bitmap::class.java
        override fun getDataSource() = DataSource.LOCAL

        companion object {
            val SKIP_CUSTOM_CACHE: Option<Boolean> =
                Option.memory("skip_custom_cache")
        }
    }
}