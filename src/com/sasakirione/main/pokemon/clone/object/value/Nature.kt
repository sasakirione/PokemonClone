package com.sasakirione.main.pokemon.clone.`object`.value

import java.util.Arrays
import com.sasakirione.main.pokemon.clone.`object`.value.Nature
import java.lang.IllegalArgumentException

/**
 * ポケモンの性格を担当するクラス
 */
class Nature(nature: String) {
    /** 設定された性格  */
    private val nature: String

    /**
     * 性格の存在をチェックする
     * 与えられた性格が存在する性格かチェックしてその結果を返します。
     * @param nature 性格
     * @return 性格が存在する場合はtrue
     */
    private fun isNatureCheck(nature: String): Boolean {
        return listOf(*NATURE).contains(nature)
    }

    /**
     * 性格上昇補正を返す
     * 設定された性格で上昇するステータスを返します。
     * @return 攻撃が上昇する場合は1、防御が上昇する場合は2、特攻が上昇する場合は3、特防が上昇する場合は4、素早さが上昇する場合は5を返す
     * @throws IllegalArgumentException おまもり
     */
    fun plusNature(): Int {
        if (nature == "さみしがり" || nature == "いじっぱり" || nature == "やんちゃ" || nature == "ゆうかん") {
            return 1
        }
        if (nature == "ずぶとい" || nature == "わんぱく" || nature == "のうてんき" || nature == "のんき") {
            return 2
        }
        if (nature == "ひかえめ" || nature == "おっとり" || nature == "うっかりや" || nature == "れいせい") {
            return 3
        }
        if (nature == "おだやか" || nature == "おとなしい" || nature == "しんちょう" || nature == "なまいき") {
            return 4
        }
        if (nature == "おくびょう" || nature == "ようき" || nature == "せっかち" || nature == "むじゃき") {
            return 5
        }
        throw IllegalArgumentException("存在しない性格です")
    }

    /**
     * 性格下降補正を返す
     * 設定された性格で下降するステータスを返します。
     * @return 攻撃が下降する場合は1、防御が下降する場合は2、特攻が下降する場合は3、特防が下降する場合は4、素早さが下降する場合は5を返す
     * @throws IllegalArgumentException おまもり
     */
    fun minusNature(): Int {
        if (nature == "おくびょう" || nature == "ひかえめ" || nature == "ずぶとい" || nature == "おだやか") {
            return 1
        }
        if (nature == "さみしがり" || nature == "おっとり" || nature == "おとなしい" || nature == "せっかち") {
            return 2
        }
        if (nature == "ようき" || nature == "いじっぱり" || nature == "わんぱく" || nature == "しんちょう") {
            return 3
        }
        if (nature == "やんちゃ" || nature == "のうてんき" || nature == "うっかりや" || nature == "むじゃき") {
            return 4
        }
        if (nature == "ゆうかん" || nature == "のんき" || nature == "れいせい" || nature == "なまいき") {
            return 5
        }
        throw IllegalArgumentException("存在しない性格です")
    }

    /**
     * まじめ系の性格かチェックする
     * 設定された性格がまじめ系の性格かをチェックする。
     * @return まじめ系の性格(上昇下降補正なし)だった場合はtrue
     */
    val isMajime: Boolean
        get() = nature == "てれや" || nature == "がんばりや" || nature == "すなお" || nature == "きまぐれ" || nature == "まじめ"

    companion object {
        /** ポケモンの性格一覧  */
        private val NATURE = arrayOf(
            "さみしがり", "いじっぱり", "やんちゃ", "ゆうかん", "ずぶとい", "わんぱく", "のうてんき",
            "のんき", "ひかえめ", "おっとり", "うっかりや", "れいせい", "おだやか", "おとなしい", "しんちょう", "なまいき", "おくびょう", "せっかち",
            "ようき", "ようき", "むじゃき", "てれや", "がんばりや", "すなお", "きまぐれ", "まじめ"
        )
    }

    /**
     * 性格を設定します。
     */
    init {
        require(isNatureCheck(nature)) { "存在しない性格です" }
        this.nature = nature
    }
}