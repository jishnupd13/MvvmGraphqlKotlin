package com.app.mymainapp.models


import com.google.gson.annotations.SerializedName

data class TestApiResponseModel(
    @SerializedName("body")
    val body: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?
)