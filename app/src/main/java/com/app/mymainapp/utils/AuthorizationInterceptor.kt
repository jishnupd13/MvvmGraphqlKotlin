package com.app.mymainapp.utils

import com.app.mymainapp.preferences.PreferenceHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(val preferenceHandler: PreferenceHandler) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }


    private fun Request.signedRequest() = when (AuthorizationType.fromRequest(this)) {
        AuthorizationType.USER_TOKEN -> {

            Timber.e(preferenceHandler.userToken)

            newBuilder()
                .header("useraccesstoken", preferenceHandler.userToken)
                .build()
        }
        AuthorizationType.NONE -> this
    }


    enum class AuthorizationType {
        USER_TOKEN,
        NONE;

        companion object {
            fun fromRequest(request: Request): AuthorizationType =
                request.tag(AuthorizationType::class.java) ?: NONE
        }
    }


}