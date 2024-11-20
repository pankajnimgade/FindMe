package com.findme.businesslogic.api

import com.findme.businesslogic.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrAPI {
    //https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=porcupine

    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    fun getListEarthquakeAt(
        @Query("tags") tags: String
    ): Call<SearchResponse>
}