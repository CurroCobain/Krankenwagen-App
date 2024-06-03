package com.example.proyectofinalintmov.krankenwagen.apis


import com.example.proyectofinalintmov.krankenwagen.data.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para el servicio de geolocalización
 */
interface GeocodingService {
    @GET("search")
    suspend fun getCoordinates(
        @Query("format") format: String,
        @Query("q") address: String
    ): List<GeocodingResponse>
}