package com.example.proyectofinalintmov.krankenwagen.apis


import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("search")
    suspend fun getAddress(
        @Query("format") format: String = "json",
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): List<NominatimResponse>

    @GET("search")
    suspend fun getCoordinates(
        @Query("format") format: String = "json",
        @Query("q") address: String
    ): List<NominatimResponse>
}