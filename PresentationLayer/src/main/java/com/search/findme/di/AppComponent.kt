package com.search.findme.di

import com.search.findme.view.FindMeActivity
import com.search.findme.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, UseCaseModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(findMeActivity: FindMeActivity)

}