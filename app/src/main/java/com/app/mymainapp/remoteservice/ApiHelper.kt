package com.app.mymainapp.remoteservice

import com.app.mymainapp.models.TestApiResponseModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getPosts(): Response<List<TestApiResponseModel>>


}