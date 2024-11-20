package com.findme.frameworklayer.usecase

import android.content.Context
import android.net.ConnectivityManager
import com.findme.businesslogic.feature.SearchImageUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit

class SearchImageUseCaseImpl(
    private val context: Context?,
    retrofit: Retrofit,
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher
) : SearchImageUseCase(retrofit, scope, dispatcher){

    override fun isNetworkAvailable(): Boolean {
        var isNetworkAvailable = false
        context?.let {
            val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            isNetworkAvailable = cm.isDefaultNetworkActive
        }
        return isNetworkAvailable
    }
}