package com.app.mymainapp.remoteservice

import com.app.mymainapp.models.LoginRequest
import com.app.mymainapp.models.LoginResponses
import com.app.mymainapp.models.TestApiNestedModel
import com.app.mymainapp.models.TestApiResponseModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImplementation @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getPosts(): Response<List<TestApiResponseModel>> = apiService.getPosts()
    override suspend fun getNestedPosts(): Response<List<TestApiNestedModel>> =apiService.getNestedPosts()
    override suspend fun createUser(request: LoginRequest?): Response<LoginResponses> = apiService.createUser(request)!!
}