package com.app.mymainapp.repository

import com.apollographql.apollo.ApolloClient
import com.app.mymainapp.LaunchListQuery
import com.app.mymainapp.baseresult.safeApiCall
import com.app.mymainapp.baseresult.safeApolloCall
import com.app.mymainapp.localdatabaseservice.AppLocalRoomDatabaseDao
import com.app.mymainapp.localdatabaseservice.entities.StudentEntity
import com.app.mymainapp.remoteservice.ApiHelper
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appLocalRoomDatabaseDao: AppLocalRoomDatabaseDao,
    private val apolloClient: ApolloClient

) {

    //retrofit
    suspend fun getPosts() = safeApiCall { apiHelper.getPosts() }

    //for room DataBase
    suspend fun insertStudentData(student: StudentEntity) = appLocalRoomDatabaseDao.insert(student)
    suspend fun fetchStudents() = appLocalRoomDatabaseDao.fetch()

    suspend fun fetchLists() = safeApolloCall {
        apolloClient.query(LaunchListQuery())
    }

}