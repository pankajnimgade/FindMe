package com.findme.businesslogic.core.interactor

import com.findme.businesslogic.core.exception.Failure
import com.findme.businesslogic.core.functional.Either
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params>(
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) where Type : Any {

    private lateinit var onResult: (Either<Failure, Type>) -> Unit

    /**
     * Methods to be executed in another scope (coroutines...)
     */
    protected abstract suspend fun run(params: Params): Either<Failure, Type>

    /**
     * Only method which needs to be called
     */
    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        this.onResult = onResult
        scope.launch {
            val result = run(params = params)
            withContext(dispatcher) { onResult(result) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun status(result: Any) {
        onResult(result as Either<Failure, Type>)
    }

    fun cancel(cause: CancellationException) {
        scope.cancel(cause)
    }
}