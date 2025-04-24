package com.example.helloandroidagain.presentation.component.glide

import android.util.Log
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

object CustomActionIdleRequestListener :
//    CustomActionRequestListener(),
        IdlingResource {

        private var callback: IdlingResource.ResourceCallback? = null
        private val counter = AtomicInteger(0)

//    fun resetListenerWithAction(action: () -> Unit): RequestListener<Drawable> = apply {
//        setUpCustomAction { action() }
//    }

        fun increment() {
            val value = counter.incrementAndGet()
            Log.d("CustomActionIdleRequestListener", "incremented: $value")
        }

        private fun decrement(st: String) {
            val value = counter.decrementAndGet()
            Log.d("CustomActionIdleRequestListener", "$this[$st-decremented]: $value")
            if (value == 0) {
                callback?.onTransitionToIdle()
            }
        }

        override fun getName(): String = "CustomActionIdleRequestListener"

        override fun isIdleNow(): Boolean = counter.get() == 0

        override fun registerIdleTransitionCallback(cb: IdlingResource.ResourceCallback?) {
            callback = cb
        }

//    override fun onLoadFailed(
//        e: GlideException?,
//        model: Any?,
//        target: Target<Drawable>?,
//        isFirstResource: Boolean
//    ): Boolean {
//        decrement("failed")
//        return super.onLoadFailed(e, model, target, isFirstResource)
//    }
//
//    override fun onResourceReady(
//        resource: Drawable?,
//        model: Any?,
//        target: Target<Drawable>?,
//        dataSource: DataSource?,
//        isFirstResource: Boolean
//    ): Boolean {
//        decrement("loaded")
//        return super.onResourceReady(resource, model, target, dataSource, isFirstResource)
//    }
    }