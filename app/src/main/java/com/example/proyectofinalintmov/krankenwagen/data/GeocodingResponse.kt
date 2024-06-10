package com.example.proyectofinalintmov.krankenwagen.data

import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta de una API de geocodificación.
 *
 * @property results La lista de resultados de geocodificación.
 */
data class GeocodingResponse(
    @SerializedName("results") val results: List<GeocodingResult>
)

/**
 * Representa un resultado de geocodificación.
 *
 * @property geometry La información de la geometría del resultado de geocodificación.
 */
data class GeocodingResult(
    @SerializedName("geometry") val geometry: Geometry
)

/**
 * Representa la información de la geometría de un resultado de geocodificación.
 *
 * @property location Los detalles de la ubicación que contienen la latitud y la longitud.
 */
data class Geometry(
    @SerializedName("location") val location: Location
)

/**
 * Representa los detalles de la ubicación con latitud y longitud.
 *
 * @property lat La latitud de la ubicación.
 * @property lng La longitud de la ubicación.
 */
data class Location(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)



