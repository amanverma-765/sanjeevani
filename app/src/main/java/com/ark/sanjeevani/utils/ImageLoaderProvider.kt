package com.ark.sanjeevani.utils

import android.content.Context
import coil3.ImageLoader
import coil3.request.CachePolicy
import coil3.request.crossfade


object ImageLoaderProvider {
    lateinit var imageLoader: ImageLoader
        private set

    fun initialize(context: Context) {
        imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}


