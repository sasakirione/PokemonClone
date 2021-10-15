package com.sasakirione.main.pokemon.clone.`object`.value

import com.sasakirione.main.pokemon.clone.constant.CalculationConst
import kotlin.math.floor

class HP(private val maxHP: Int) {
    var currentHP: Int
        private set

    val isDeath: Boolean
        get() = (currentHP == 0)

    val isOneThird: Boolean
        get() = if (currentHP == 0) {
                false
            } else (3 < (maxHP / currentHP))

    init {
        currentHP = maxHP
    }

    fun pruneHP(damage: Int) {
        val hp = currentHP - damage
        currentHP = hp.coerceAtLeast(0)
    }

    fun damageOneEighth() {
        val oneEighth = floor(maxHP * CalculationConst.ONE_EIGHTH).toInt()
        pruneHP(oneEighth)
    }

    fun recoveryOnePointSixteen() {
        val calculationHP = floor(maxHP * CalculationConst.ONE_POINT_SIXTEEN + currentHP).toInt()
        currentHP = calculationHP.coerceAtMost(maxHP)
    }

    override fun toString(): String {
        val half = maxHP / 2
        val quarter = maxHP / 4
        var now = "緑"
        if (currentHP <= half) {
            now = "黄色"
            if (currentHP <= quarter) {
                now = "赤色"
            }
        }
        return "$currentHP/$maxHP $now"
    }
}