package com.example.converter.Enum

enum class CurrencyEnum(val coefficient: Double) {
    RUBLES(1.0), DOLLARS(2.58), EURO(3.17);

    companion object {
        fun getCoefficient(str: String?): Double {
            return when (str) {
                "RUBLES" -> RUBLES.coefficient
                "DOLLARS" -> DOLLARS.coefficient
                "EURO" -> EURO.coefficient
                else -> 0.0
            }
        }
    }
}