package com.search.findme

import android.app.Application
import com.search.findme.di.AppComponent
import com.search.findme.di.AppModule
import com.search.findme.di.DaggerAppComponent
import com.search.findme.di.RetrofitModule

class FindMeApplication : Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        initializeDependencyInversion()
    }

    private fun initializeDependencyInversion() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this@FindMeApplication))
            .retrofitModule(RetrofitModule(BuildConfig.API_BASE_URL))
            .build()
    }
}