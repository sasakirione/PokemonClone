package com.sasakirione.main.pokemon.clone.`object`.value

import java.util.Arrays
import com.sasakirione.main.pokemon.clone.exception.EvArgumentException
import kotlin.math.floor

/**
 * ポケモンの努力値を担当するクラス
 */
class Effort(effort: IntArray) {
    /** HPの努力値  */
    var h: Int
    /** 攻撃の努力値  */
    var a: Int
    /** 防御の努力値  */
    var b: Int
    /** 特攻の努力値  */
    var c: Int
    /** 特防の努力値  */
    var d: Int
    /** 素早さの努力値  */
    var s: Int

    fun realCalculation(base: IntArray): IntArray {
        val hp = realCalculationHP(base[0], h)
        val a = realCalculationEtc(base[1], a)
        val b = realCalculationEtc(base[2], b)
        val c = realCalculationEtc(base[3], c)
        val d = realCalculationEtc(base[4], d)
        val s = realCalculationEtc(base[5], s)
        return intArrayOf(hp, a, b, c, d, s)
    }

    private fun realCalculationHP(base: Int, effort: Int): Int {
        return floor((base * 2.0 + 31 + floor(effort / 4.0)) * (50.0 / 100.0) + 50 + 10).toInt()
    }

    private fun realCalculationEtc(base: Int, effort: Int): Int {
        return floor((base * 2.0 + 31 + floor(effort / 4.0)) * (50.0 / 100.0) + 5).toInt()
    }

    init {
        Arrays.stream(effort).filter { i: Int -> i > 252 }.forEach { _: Int -> throw EvArgumentException("努力値が不正です") }
        val sum = Arrays.stream(effort).sum()
        if (sum > 510) {
            throw EvArgumentException("努力値の合計が510を超えています")
        }
        h = effort[0]
        a = effort[1]
        b = effort[2]
        c = effort[3]
        d = effort[4]
        s = effort[5]
    }
}