package com.app.mymainapp.baseresult

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloNetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import retrofit2.Response
import java.net.UnknownHostException

/** Created by Jishnu P Dileep on 27-05-2021 */

data class BaseResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): BaseResult<T> {
            return BaseResult(Status.SUCCESS, data, "")
        }

        fun <T> error(message: String, data: T? = null): BaseResult<T> {
            return BaseResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): BaseResult<T> {
            return BaseResult(Status.LOADING, data, "")
        }
    }
}


sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failure(val message: String, val errorType: ErrorType) : ResultWrapper<Nothing>()
}

enum class ErrorType {
    NETWORK_ERROR,
    JSON_PARSE_ERROR,
    UNKNOWN
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful && response.body() != null) {
                ResultWrapper.Success(response.body()!!)
            } else {
                ResultWrapper.Failure(response.message() ?: "", ErrorType.UNKNOWN)
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is JSONException -> ResultWrapper.Failure(
                    throwable.message ?: "",
                    ErrorType.JSON_PARSE_ERROR
                )
                is UnknownHostException -> ResultWrapper.Failure(
                    "No internet",
                    ErrorType.NETWORK_ERROR
                )
                else -> ResultWrapper.Failure("${throwable.message}", ErrorType.UNKNOWN)
            }
        }
    }
}


suspend fun <T> safeApolloCall(
    apiCall: suspend () -> ApolloCall<T>
): ResultWrapper<T> {
    try {
        val response = apiCall.invoke().await()
        return if (response.hasErrors()) {
            ResultWrapper.Failure(
                response.errors?.getOrNull(0)?.message ?: "",
                ErrorType.UNKNOWN
            )
        } else {
            ResultWrapper.Success(response.data!!)
        }
    } catch (e: ApolloException) {
        // handle protocol errors
        return when (e) {
            is ApolloNetworkException -> ResultWrapper.Failure(
                "No internet",
                ErrorType.NETWORK_ERROR
            )
            else -> ResultWrapper.Failure(
                e.message ?: "",
                ErrorType.UNKNOWN
            )
        }
    }
}
