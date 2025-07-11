package com.ark.sanjeevani

import android.app.Application
import com.ark.sanjeevani.koin.appModule
import com.ark.sanjeevani.koin.supabaseModule
import com.ark.sanjeevani.utils.DefaultImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SanjeevaniApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DefaultImageLoader.initialize(this)
        startKoin {
            androidLogger()
            androidContext(this@SanjeevaniApp)
            modules(appModule, supabaseModule)
        }
    }
}
