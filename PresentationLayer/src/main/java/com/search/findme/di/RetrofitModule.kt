package com.search.findme.di

import com.findme.businesslogic.network.RetrofitManager
import com.search.findme.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule(private val baseUrl: String) {

    @Singleton
    @Provides
    fun providesRetrofitManager(): RetrofitManager {
        return RetrofitManager()
    }

    @Singleton
    @Provides
    fun providesRetrofit(retrofitManager: RetrofitManager): Retrofit {
        return retrofitManager.buildWithBaseUrl(baseUrl, BuildConfig.DEBUG)
    }
}