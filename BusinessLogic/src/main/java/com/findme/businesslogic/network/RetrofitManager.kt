package com.findme.businesslogic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    //https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=porcupine
    companion object {
        private const val BASE_URL = "https://api.flickr.com/"
    }

    /**
     * [isDebug] when true, we want to see the HttpLogging in the logcat, otherwise omit it.
     */
    fun buildWithBaseUrl(baseUrl: String = BASE_URL, isDebug: Boolean = false): Retrofit {

        val client = if (isDebug) {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder().addInterceptor(logging).build()
        } else {
            OkHttpClient.Builder().build()
        }
        return Retrofit.Builder().client(client).baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}