package com.app.mymainapp.remoteservice

import com.app.mymainapp.models.TestApiResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<TestApiResponseModel>>
}