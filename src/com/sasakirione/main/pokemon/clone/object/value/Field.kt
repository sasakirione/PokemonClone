package com.sasakirione.main.pokemon.clone.`object`.value

import java.util.Arrays
import java.lang.IllegalArgumentException

class Field(fieldName: String) {
    private val field: String
    private var remainingTurn: Int
    val isDisablingPriority: Boolean
        get() = this.field == "サイコフィールド"
    val isEndField: Boolean
        get() = remainingTurn == 0
    val isPsychofield: Boolean
        get() = this.field == "サイコフィールド"

    companion object {
        private val fieldList = arrayOf("エレキフィールド", "グラスフィールド", "サイコフィールド", "ミストフィールド")
    }

    init {
        fieldCheck(fieldName)
        field = fieldName
        remainingTurn = 5
    }

    private fun fieldCheck(fieldName: String) {
        require(
            listOf(*fieldList).contains(fieldName)
        ) { "存在しないフィールドです" }
    }

    fun forwardTurn() {
        remainingTurn -= 1
    }
}