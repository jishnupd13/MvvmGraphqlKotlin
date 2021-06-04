package com.app.mymainapp.remoteservice

import com.app.mymainapp.models.LoginRequest
import com.app.mymainapp.models.LoginResponses
import com.app.mymainapp.models.TestApiNestedModel
import com.app.mymainapp.models.TestApiResponseModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("posts")
    suspend fun getPosts():Response<List<TestApiResponseModel>>

    @GET("posts")
    suspend fun getNestedPosts():Response<List<TestApiNestedModel>>

    @POST("user/create-user")
    suspend fun createUser(@Body request: LoginRequest?): Response<LoginResponses>?



}