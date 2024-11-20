package com.search.findme.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.findme.businesslogic.core.exception.Failure
import com.findme.businesslogic.feature.SearchImageUseCase
import com.findme.businesslogic.model.SearchResponse
import com.findme.frameworklayer.usecase.SearchImageUseCaseImpl
import com.google.gson.Gson
import javax.inject.Inject

class FindMeViewModel @Inject constructor(
    private val searchImageUseCaseImpl: SearchImageUseCaseImpl
) : ViewModel() {

    companion object {
        val TAG = FindMeViewModel::class.java.simpleName
    }

    private val searchImageResponse: MutableLiveData<SearchResponse> = MutableLiveData()
    val errorPublisher: MutableLiveData<Boolean> = MutableLiveData()
    val noState: MutableLiveData<Boolean> = MutableLiveData()

    init {
        errorPublisher.postValue(false)
        noState.postValue(true)
    }

    fun searchForTag(tag: String): LiveData<SearchResponse> {
        val params = SearchImageUseCase.Params(tags = tag)
        searchImageUseCaseImpl(params) { result ->
            result.either(::handleFailure, ::handleSuccess)
        }
        return searchImageResponse
    }

    private fun handleSuccess(searchResponse: SearchResponse?) {
        searchResponse?.let {
            searchImageResponse.postValue(it)
            Log.d(TAG, "handleSuccess: ${Gson().toJson(it)}")
        } ?: run {
            errorPublisher.postValue(true)
        }
    }

    private fun handleFailure(failure: Failure) {
        if (failure.error.isNotEmpty()) {
            errorPublisher.postValue(true)
        }
    }

}