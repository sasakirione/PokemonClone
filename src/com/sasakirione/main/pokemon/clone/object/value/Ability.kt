package com.sasakirione.main.pokemon.clone.`object`.value

import com.sasakirione.main.pokemon.clone.`object`.PokemonMove
import com.sasakirione.main.pokemon.clone.constant.AbilityConst
import com.sasakirione.main.pokemon.clone.constant.CalculationConst

/**
 * ポケモンの特性を担当するクラス
 */
class Ability(private val name: String) {
    /** 特性が発動条件を満たしてるか、もしかくは一度だけの特性なら使用済みか  */
    private var abilityBool = false
    /** 化学変化ガス用の特性有効判定*/
    private var isAbilityInvalidation = true

    /**
     * 技の攻撃力をあげる特性か判定
     * 技の攻撃力をあげる特性かを判断し、その場合は倍率を返します
     * @param move 出そうとしてる技のインスタンス
     * @return 攻撃力に対する倍率(通常は1)
     */
    fun powerBoost(move: PokemonMove): Double {
        if (!isAbilityInvalidation) {
            return CalculationConst.ONE
        }
        if (checkTypeBoost(move.moveType)) {
            return CalculationConst.ONE_POINT_FIVE
        }
        return if (isTorrent(move.moveType)) {
            CalculationConst.ONE_POINT_FIVE
        } else CalculationConst.ONE
    }

    /**
     * 特定のタイプの攻撃力をあげる特性か判定
     * トランジスタやりゅうのあぎとなどの特定のタイプの攻撃力をあげる特性かを判定します
     * @param type 出そうとしてる技のタイプ
     * @return 対象の特性と技の組み合わせだったらtrue
     */
    private fun checkTypeBoost(type: String): Boolean {
        return (name == AbilityConst.TRANSISTOR && type == "でんき" || name == AbilityConst.DRAGONS_MAW && type == "ドラゴン") && isAbilityInvalidation
    }

    /**
     * サイコメイカー判定
     * 特性がサイコフィールを展開する特性かどうか判定します
     * ex) サイコメイカー
     * @return 合致する特性だったらtrue
     */
    val isPsychoMaker: Boolean
        get() = (name == AbilityConst.PSYCHO_MAKER) && isAbilityInvalidation

    /**
     * リベロ判定
     * 特性が自分のタイプを技のタイプに合わせて変化させる特性かどうかを判定します
     * ex) リベロ、へんげんじざい
     * @return 合致する特性だったらtrue
     */
    val isLibero: Boolean
        get() = (name == AbilityConst.LIBERO || name == AbilityConst.PROTEAN) && isAbilityInvalidation

    /**
     * げきりゅう判定
     * 特性がピンチ(HPが1/3以下)の時に対応するタイプの技の攻撃力が上がる特性かどうかとHPが適用条件を満たしてるかどうかを判定します
     * ex) げきりゅう、もうか、しんりょく、むしのしらせ
     * @param moveType 技のタイプ
     * @return 合致する特性だったらtrue
     */
    private fun isTorrent(moveType: String): Boolean {
        return if (!abilityBool) {
            false
        } else name == AbilityConst.TORRENT && moveType == "みず" || name == AbilityConst.BLAZE && moveType == "ほのお" ||
                name == AbilityConst.OVER_GROW && moveType == "くさ" || name == AbilityConst.SWARM && moveType == "むし"
    }

    /**
     * HP起動
     * HPが1/3以下になった時に呼ばれて、対応する特性だった場合にはabilityBoolをtrueにします
     * ex) げきりゅう、もうか、しんりょく、むしのしらせ
     */
    fun doOneThird() {
        if (name == AbilityConst.TORRENT || name == AbilityConst.BLAZE ||
                name == AbilityConst.OVER_GROW || name == AbilityConst.SWARM) {
            abilityOn()
        }
    }

    /**
     * 特性起動判定
     * 特性が使える状態かどうか、特性が使用済みかを操作します
     */
    fun abilityOn() {
        abilityBool = true
    }

    /**
     * 特性のtoString
     * 特性の名前を返します
     * @return 特性名
     */
    override fun toString(): String {
        return name
    }

    /**
     * ばかかわ判定
     * 相手の攻撃を無効化する特性かどうか、それが使える状態かどうかを判定します
     * @return 条件に合致した場合にtrue
     */
    val isBakekawa: Boolean
        get() = (name == AbilityConst.DISGUISE && !abilityBool) && isAbilityInvalidation

    /**
     * てんねん判定
     * 相手の能力変化を無効化する特性かどうかを判定します
     * @return 条件に合致した場合にtrue
     */
    val isUnware: Boolean
        get() = (name == AbilityConst.UNWARE) && isAbilityInvalidation
}