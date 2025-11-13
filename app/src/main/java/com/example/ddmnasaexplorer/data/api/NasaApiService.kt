package com.example.ddmnasaexplorer.data.api

import com.example.ddmnasaexplorer.data.models.ApodResponse

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"

// "construtor" do Retrofit
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// interface que define os endpoints
interface NasaApiService {

    @GET("planetary/apod") // O caminho do endpoint
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String
    ): ApodResponse

    @GET("planetary/apod")
    suspend fun getGaleria(
        @Query("api_key") apiKey: String,
        @Query("count") count: Int
    ): List<ApodResponse>
}

// 4. Um objeto "singleton" para expor a API
object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}