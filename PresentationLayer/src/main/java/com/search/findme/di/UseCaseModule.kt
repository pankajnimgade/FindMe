package com.search.findme.di

import android.content.Context
import com.findme.frameworklayer.usecase.SearchImageUseCaseImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesSearchImageUseCaseImpl(
        context: Context, retrofit: Retrofit, scope: CoroutineScope, dispatcher: CoroutineDispatcher
    ): SearchImageUseCaseImpl {
        return SearchImageUseCaseImpl(context, retrofit, scope, dispatcher)
    }
}