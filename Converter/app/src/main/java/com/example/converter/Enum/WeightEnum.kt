package com.example.converter.Enum

enum class WeightEnum(val coefficient: Double) {
    GRAMS(1.0), KILOGRAMS(1000.0), POUNDS(453.592);

    companion object {
        fun getCoefficient(str: String?): Double {
            return when (str) {
                "GRAMS" -> GRAMS.coefficient
                "KILOGRAMS" -> KILOGRAMS.coefficient
                "POUNDS" -> POUNDS.coefficient
                else -> 0.0
            }
        }
    }
}