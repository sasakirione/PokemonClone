package com.sasakirione.main.pokemon.clone.`object`.value

import com.sasakirione.main.pokemon.clone.constant.GoodConst
import com.sasakirione.main.pokemon.clone.`object`.code.MoveClass
import com.sasakirione.main.pokemon.clone.constant.CalculationConst

/**
 * ポケモンの道具を担当するクラス
 */
class Good(private val goodName: String) {
    /** 道具の使用判定  */
    var isGoodUsed = false
        private set

    /**
     * こだわりアイテム判定
     * 技にこだわりが生じるアイテムだった場合にtrueを返します。
     * @return 道具がこだわりアイテムだった場合にtrue
     */
    val isChoice: Boolean
        get() = (goodName == GoodConst.CHOICE_SPECS || goodName == GoodConst.CHOICE_BAND || goodName == GoodConst.CHOICE_SCARF)

    /**
     * S強化アイテム判定
     * Sが1.5倍になるアイテムだった場合はtrueを返します。
     * @return 道具がSを1.5倍にするアイテムだった場合にtrue
     */
    val isSpeedBoost: Boolean
        get() = (goodName == GoodConst.CHOICE_SCARF || goodName == GoodConst.NOT_CHOICE_SCARF)

    /**
     * 攻撃力強化アイテム判定
     * 攻撃力を増やすアイテムだった場合にその倍率を返します。
     * @param moveClass 技のタイプ(特殊攻撃技か物理攻撃技かそれ以外か)
     * @return 攻撃力の倍率(デフォルトは1)
     */
    fun powerBoost(moveClass: MoveClass): Double {
        if (goodName == GoodConst.CHOICE_BAND && moveClass == MoveClass.PHYSICS ||
                goodName == GoodConst.CHOICE_SPECS && moveClass == MoveClass.SPECIAL) {
            return CalculationConst.ONE_POINT_FIVE
        }
        return if (goodName == GoodConst.LIFE_ORB && (moveClass == MoveClass.SPECIAL || moveClass == MoveClass.PHYSICS)) {
            CalculationConst.ONE_POINT_THREE_ORB
        } else CalculationConst.ONE
    }

    /**
     * 1/8の反動がくる道具か判定
     * アイテムがいのちのたまだった場合にtrueを返します。
     * @return 道具がいのちのたまだった場合にtrue
     */
    val isDamageOneEighth: Boolean
        get() = isLeftOvers

    /**
     * いのちのたま判定
     * アイテムがいのちのたまだった場合にtrueを返します。
     * @return 道具がいのちのたまだった場合にtrue
     */
    val isLeftOvers: Boolean
        get() = (goodName == GoodConst.LEFT_OVERS)

    /**
     * 道具の消費
     * 消費アイテムの使用判定をtrueにします。
     */
    fun goodUsed() {
        isGoodUsed = true
    }

    /**
     * しろいハーブ判定
     * アイテムがしろいハーブだったバイアにtrueを返します。
     * @return 道具がしろいハーブだった場合にtrue
     */
    val isWhiteHerb: Boolean
        get() = (goodName == GoodConst.WHITE_HERB)
}