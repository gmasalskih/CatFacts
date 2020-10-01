package com.example.catfacts.api

import com.example.catfacts.data.Repo
import com.example.catfacts.data.User
import com.example.catfacts.helper.TAG_LOG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber
import java.util.concurrent.TimeUnit

interface Service {
    @GET("users/{login}/repos")
    suspend fun getReposOfUser(@Path("login") login: String): Response<List<Repo>>

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}

private val log = object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.i("$TAG_LOG $message")
    }
}

private val logging = HttpLoggingInterceptor(log).apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

private val httpClient = OkHttpClient.Builder()
    .apply { addInterceptor(logging) }
    .connectTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

object ApiProvider {

    private const val BASE_URL = "https://api.github.com/"

    fun getAPI(): Service = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(Service::class.java)
}