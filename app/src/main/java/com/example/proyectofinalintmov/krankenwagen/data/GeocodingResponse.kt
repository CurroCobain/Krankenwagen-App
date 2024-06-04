package com.example.proyectofinalintmov.krankenwagen.data

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    @SerializedName("results") val results: List<GeocodingResult>
)

data class GeocodingResult(
    @SerializedName("geometry") val geometry: Geometry
)

data class Geometry(
    @SerializedName("location") val location: Location
)

data class Location(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)


