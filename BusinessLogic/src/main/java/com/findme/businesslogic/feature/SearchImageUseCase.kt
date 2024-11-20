package com.findme.businesslogic.feature

import com.findme.businesslogic.api.FlickrAPI
import com.findme.businesslogic.core.exception.Failure
import com.findme.businesslogic.core.functional.Either
import com.findme.businesslogic.core.interactor.UseCase
import com.findme.businesslogic.model.SearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit

abstract class SearchImageUseCase(
    val retrofit: Retrofit,
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher
) : UseCase<SearchResponse, SearchImageUseCase.Params>(scope, dispatcher) {

    override suspend fun run(params: Params): Either<Failure, SearchResponse> {

        if (!isNetworkAvailable()) {
            return Either.Left(Failure.NetworkConnection)
        }

        val earthquakeApi = retrofit.create(FlickrAPI::class.java)
        val response = try {
            earthquakeApi.getListEarthquakeAt(params.tags).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            return Either.Left(Failure.ServerError)
        }
        response.body()?.let {
            return if (it.items.isNotEmpty()) {
                Either.Right(it)
            } else {
                Either.Left(SearchImageUseCaseError("Result from the response was empty"))
            }
        }
        return Either.Left(SearchImageUseCaseError("SearchImageUseCaseError didn't work!!"))
    }

    /**
     * checking network connection state is android specific, therefore it should be delegated to
     * Framework code, whose job is to provide implementation fot his function
     */
    abstract fun isNetworkAvailable(): Boolean

    /**
     * this remaining type contains half of the URL which is used to make API CALL
     */
    data class Params(val tags: String)

    /**
     * If there is an error specific to this Use case, throw this error
     */
    class SearchImageUseCaseError(error: String) : Failure.UseCaseFailure()
}