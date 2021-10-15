package com.sasakirione.main.pokemon.clone.`object`.value

import com.sasakirione.main.pokemon.clone.`object`.PokemonTypeCompatibility.typeCompatibility

/**
 * ポケモンのタイプを担当するクラス
 */
class Type {
    /** ポケモンが持ってるタイプ  */
    private val types: Array<String>

    /**
     * コンストラクタ(複合タイプ)
     */
    constructor(type1: String, type2: String) {
        if (type2 == "" && !isTypeErrorCheck(type1)) {
            types = arrayOf(type1)
            return
        }
        require(!isTypeErrorCheck(type1)) { "存在しないタイプが含まれています" }
        require(!isTypeErrorCheck(type2)) { "存在しないタイプが含まれています" }
        types = arrayOf(type1, type2)
    }

    /**
     * コンストラクタ(単一タイプ)
     */
    constructor(type1: String) {
        require(!isTypeErrorCheck(type1)) { "存在しないタイプが含まれています" }
        types = arrayOf(type1)
    }

    /**
     * コンストラクタ(ハロウィンorもりののろい+複合タイプ)
     */
    constructor(type1: String, type2: String, type3: String) {
        require(!isTypeErrorCheck(type1)) { "存在しないタイプが含まれています" }
        require(!isTypeErrorCheck(type2)) { "存在しないタイプが含まれています" }
        require(!isTypeErrorCheck(type3)) { "存在しないタイプが含まれています" }
        types = arrayOf(type1, type2, type3)
    }

    private constructor(types: Array<String>) {
        this.types = types
    }

    /**
     * タイプが存在するかの判定
     * 入力されたStringがタイプとして存在するならTrue、そうでないならFalseを返す。
     * @param type タイプの文字列
     * @return タイプが存在するならtrue
     */
    private fun isTypeErrorCheck(type: String): Boolean {
        return !isContemporaryTypeCheck(TYPE, type)
    }

    /**
     * 相性倍率の計算
     * 相性倍率を計算してそれを返します
     * @param attackType 攻撃技のタイプ
     * @return double型の相性倍率
     */
    fun getTypeMagnification(attackType: String): Double {
        var magnification = 1.0
        for (type in types) {
            magnification *= typeCompatibility(attackType, type)
        }
        return magnification
    }

    /**
     * タイプ一致の判定
     * 出そうとする技はタイプ一致技かどうかを判定します。
     * @param type 攻撃技のタイプ
     * @return 攻撃技のタイプが設定されたタイプに含まれるならtrue
     */
    fun isTypeMatch(type: String): Boolean {
        return isContemporaryTypeCheck(types, type)
    }

    /**
     * まひチェック
     * まひになるタイプかチェックします。
     * @return まひにならないタイプ(でんきタイプ)ならtrue
     */
    val isPARCheck: Boolean
        get() = isContemporaryTypeCheck(types, "でんき")

    /**
     * タイプの判定
     * 様々なタイプの判定の共通化を行っています。
     * @param type1 判定するタイプ
     * @param type2 判定されるタイプ
     * @return type1の中にtype2が含まれていたらtrue
     */
    private fun isContemporaryTypeCheck(type1: Array<String>, type2: String): Boolean {
        return listOf(*type1).contains(type2)
    }

    /**
     * タイプのコピー
     * タイプをディープコピーします
     * @return タイプのディープコピー
     */
    fun copy(): Type {
        return Type(types.clone())
    }

    override fun toString(): String {
        val type1 = types[0]
        val type2: String =
            if (types.size == 1) {
                ""
            } else {
                types[1]
            }
        return type1 + type2
    }

    companion object {
        /** ポケモンのタイプ一覧  */
        private val TYPE = arrayOf("ノーマル", "ほのお", "みず", "でんき", "くさ", "こおり", "かくとう", "どく", "じめん",
                "ひこう", "エスパー", "むし", "いわ", "ゴースト", "ドラゴン", "あく", "はがね", "フェアリー")
    }
}