package com.example.proyectofinalintmov.krankenwagen.apis


import com.example.proyectofinalintmov.krankenwagen.data.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz del servicio de geolocalización
 */
interface GoogleGeocodingService {
    @GET("geocode/json")
    suspend fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GeocodingResponse
}
