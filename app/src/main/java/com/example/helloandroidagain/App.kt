package com.example.helloandroidagain

import android.app.Application
import com.example.helloandroidagain.di.AppComponent
import com.example.helloandroidagain.di.AppModule
import com.example.helloandroidagain.di.DaggerAppComponent

class App : Application() {
    var appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
}