package com.app.mymainapp.injection

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.app.mymainapp.localdatabaseservice.AppLocalRoomDatabaseDao
import com.app.mymainapp.localdatabaseservice.LocalRoomDatabase
import com.app.mymainapp.preferences.PreferenceHandler
import com.app.mymainapp.remoteservice.ApiHelper
import com.app.mymainapp.remoteservice.ApiHelperImplementation
import com.app.mymainapp.remoteservice.ApiService
import com.app.mymainapp.utils.Constants.Companion.BASE_URL
import com.app.mymainapp.utils.Constants.Companion.GRAPHQL
import com.app.mymainapp.utils.Constants.Companion.ROOM_DATABASE_NAME
import com.app.mymainapp.utils.Constants.Companion.SHARED_PREFERENCE_KEY
import com.app.mymainapp.utils.StylishToastyUtils
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context) = context


    @Provides
    @Named("RetrofitBaseUrl")
    fun provideRetrofitBaseUrl() = BASE_URL

    @Provides
    @Named("GraphQlBaseUrl")
    fun provideGraphQlBaseUrl() = GRAPHQL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("RetrofitBaseUrl") BASE_URL: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImplementation): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, AppCompatActivity.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedEditor(sharedPreferences: SharedPreferences) = sharedPreferences.edit()

    @Singleton
    @Provides
    fun providePreferenceHandler(sharedPreferences: SharedPreferences) =
        PreferenceHandler(sharedPreferences)


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): LocalRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            LocalRoomDatabase::class.java,
            ROOM_DATABASE_NAME
        ).build()
    }


    @Provides
    fun provideAppLocalRoomDatabaseDao(appDatabase: LocalRoomDatabase): AppLocalRoomDatabaseDao {
        return appDatabase.appLocalRoomDatabaseDao()
    }

    @Singleton
    @Provides
    fun provideStylishToastyUtils(@ApplicationContext context: Context) =
        StylishToastyUtils(context)

    @Singleton
    @Provides
    fun providesApolloClient(@Named("GraphQlBaseUrl") graphQl: String,  okHttpClient: OkHttpClient) =
        ApolloClient.builder()
            .serverUrl(graphQl)
            .okHttpClient(okHttpClient)
            .build()
}