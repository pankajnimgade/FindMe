package com.search.findme.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context = application

    @Provides
    @Singleton
    fun providesScope(): CoroutineScope {
        return object : CoroutineScope {
            override val coroutineContext: CoroutineContext
                get() = Dispatchers.IO + Job()
        }
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

}