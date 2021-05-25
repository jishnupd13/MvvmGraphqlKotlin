package com.app.mymainapp.repository

import com.app.mymainapp.remoteservice.ApiHelper
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getPosts() = apiHelper.getPosts()
}