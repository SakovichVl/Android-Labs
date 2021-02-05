package com.example.converter.Enum

enum class DistanceEnum(val coefficient: Double) {
    METERS(1.0), KILOMETERS(1000.0), MILES(1609.6);

    companion object {
        fun getCoefficient(str: String?): Double {
            return when (str) {
                "METERS" -> METERS.coefficient
                "KILOMETERS" -> KILOMETERS.coefficient
                "MILES" -> MILES.coefficient
                else -> 0.0
            }
        }
    }
}