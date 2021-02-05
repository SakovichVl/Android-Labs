package com.example.converter

import android.annotation.SuppressLint
import com.example.converter.Enum.CurrencyEnum
import com.example.converter.Enum.DistanceEnum
import com.example.converter.Enum.WeightEnum

object Converter {
    @SuppressLint("DefaultLocale")
    fun convert(nameEnum: String?, inputValue: String?, inputS: String?, outputS: String?): String {
        val inputCoefficient: Double
        val outputCoefficient: Double
        when (nameEnum) {
            "d" -> {
                inputCoefficient = DistanceEnum.getCoefficient(inputS)
                outputCoefficient = DistanceEnum.getCoefficient(outputS)
            }
            "w" -> {
                inputCoefficient = WeightEnum.getCoefficient(inputS)
                outputCoefficient = WeightEnum.getCoefficient(outputS)
            }
            "c" -> {
                inputCoefficient = CurrencyEnum.getCoefficient(inputS)
                outputCoefficient = CurrencyEnum.getCoefficient(outputS)
            }
            else -> {
                inputCoefficient = 0.0
                outputCoefficient = 0.0
            }
        }
        return String.format("%.3f", inputCoefficient / outputCoefficient * Double.parseDouble(inputValue)).replace(",", ".")
    }
}