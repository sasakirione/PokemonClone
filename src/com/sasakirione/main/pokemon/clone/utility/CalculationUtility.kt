package com.sasakirione.main.pokemon.clone.utility

import java.math.BigDecimal
import java.math.RoundingMode

object CalculationUtility {
    @JvmStatic
    fun fiveOutOverFiveIn(i: Double): Int {
        val bigDecimal = BigDecimal(i.toString())
        val resBD = bigDecimal.setScale(0, RoundingMode.HALF_DOWN)
        return resBD.toDouble().toInt()
    }
}