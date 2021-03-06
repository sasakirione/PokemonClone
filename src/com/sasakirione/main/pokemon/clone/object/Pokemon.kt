package com.sasakirione.main.pokemon.clone.`object`

import com.sasakirione.main.pokemon.clone.`object`.value.*
import com.sasakirione.main.pokemon.clone.constant.CalculationConst
import com.sasakirione.main.pokemon.clone.constant.MoveConst
import com.sasakirione.main.pokemon.clone.data.PokemonDataGet
import com.sasakirione.main.pokemon.clone.data.PokemonDataGetInterface
import com.sasakirione.main.pokemon.clone.loggin.BattleLog
import java.util.ArrayList
import java.util.function.Consumer
import kotlin.math.roundToInt

/**
 * ポケモン自体を表すクラス
 */
class Pokemon(
    val name: String,
    effort: IntArray,
    base: IntArray,
    good: String?,
    nature: String,
    type1: String,
    type2: String,
    ability: String) {

    /**
     * ポケモンのステータス
     */
    val status: Status

    /**
     * ポケモンのタイプ
     */
    var type: Type
        private set

    /**
     * ポケモンの初期タイプ
     */
    private val originalType: Type

    /**
     * ポケモンの特性
     */
    private val ability: Ability

    /**
     * ポケモンがこだわってる場合のわざ
     */
    private var choiceMove: String? = null

    /**
     * ポケモンの状態異常(状態異常がなしの場合は空文字)
     */
    private var statusAilment = ""

    /**
     * ポケモンの道具
     */
    private var good: Good?

    /**
     * データ取得用インスタンス
     */
    private var pokemonDataGet: PokemonDataGetInterface

    /**
     * ポケモンのわざを出す(データベースにない定義済みの技用)
     * ポケモンのわざを担当するクラスをインスタンス化して返します。
     *
     * @param name わざの名前
     * @return ポケモンのわざクラスのインスタンス
     */
    fun getDamage2(name: String): PokemonMove {
        if (good?.isChoice == true) {
            choiceCheck(name)
        }
        return PokemonMove(name, this)
    }

    /**
     * ポケモンのわざを出す
     * ポケモンのわざを担当するクラスをインスタンス化して返します。
     *
     * @param name わざの名前
     * @return ポケモンのわざクラスのインスタンス
     */
    fun getDamage(name: String): PokemonMove {
        if (good?.isChoice == true) {
            choiceCheck(name)
        }
        return pokemonDataGet.getMoveByName(name, this)
    }

    /**
     * こだわりチェック
     * こだわりアイテムを持ってる場合にこだわっているわざ以外のわざを使えないようにチェックします
     *
     * @param name わざの名前
     */
    private fun choiceCheck(name: String) {
        require(!(choiceMove != null && name != choiceMove)) { "こだわっています！" }
        if (choiceMove == null) {
            choiceMove = name
        }
    }

    /**
     * ポケモンのわざを受ける
     * ポケモンのわざクラスのインスタンスを受け取ってダメージ処理を行います。
     *
     * @param a 受ける技のインスタンス
     */
    fun takeDamage(a: PokemonMove, isTest: Boolean) {
        if (!a.isMoveHit && !isTest) {
            BattleLog.moveMiss()
            return
        }
        if (a.isEnemyChangeMove) {
            takeChange(a)
            return
        }
        if (a.moveName == "ちきゅうなげ") {
            status.constantDamage(50)
            return
        }
        val power = getPower(a)
        val defenseChoice = getDefenseChoice(a)
        val typeMagnification = type.getTypeMagnification(a.moveType)
        val magnification = a.magnification * typeMagnification
        moveDecisionAll(a, power, defenseChoice, typeMagnification, magnification)
        a.endDecision()
        if (good?.isGoodUsed == true) {
            lostGood()
        }
    }

    /**
     * 防御を特殊か物理どちらで計算するかを返します
     *
     * @param a 受ける技
     * @return 2だったら物理、4だったら特殊
     */
    private fun getDefenseChoice(a: PokemonMove): Int {
        val defenseChoice: Int = if (a.isPhysicsMove) {
            2
        } else {
            4
        }
        return defenseChoice
    }

    /**
     *  技を受ける処理
     *
     *  @param a 受ける技のインスタンス
     *  @param power 受ける技の計算済み威力
     *  @param defenseChoice 物理で計算するか特殊で計算するか
     *  @param typeMagnification タイプ相性による倍率
     *  @param magnification タイプ相性以外の倍率
     */
    private fun moveDecisionAll(a: PokemonMove, power: Double, defenseChoice: Int, typeMagnification: Double, magnification: Double) {
        val count = a.combCount
        val vitalRank = a.vitalRank
        for (i in 0 until count) {
            if (ability.isBakekawa) {
                ability.abilityOn()
                BattleLog.bakekawa(name)
                status.damageOneEighth()
                continue
            }
            moveDecision(power, defenseChoice, typeMagnification, magnification, vitalRank)
            remainingDamageDecision()
        }
        if (a.isCombAttack) {
            BattleLog.combAttack(count)
        }
    }

    /**
     * 技の計算済み威力を取得する
     *
     * @param a 受ける技のインスタンス
     * @return 技の計算済み威力
     */
    private fun getPower(a: PokemonMove): Double {
        val power: Double = if (ability.isUnware) {
            a.power2
        } else {
            a.power
        }
        return power
    }

    /**
     * 実際の技の処理
     *
     *  @param power 受ける技の計算済み威力
     *  @param defenseChoice 物理で計算するか特殊で計算するか
     *  @param typeMagnification タイプ相性による倍率
     *  @param magnification タイプ相性以外の倍率
     *  @param vitalRank 急所ランク
     */
    private fun moveDecision(power: Double, defenseChoice: Int, typeMagnification: Double, magnification: Double, vitalRank: Int) {
        status.damageCalculation(power, defenseChoice, magnification, vitalRank)
        BattleLog.typeMagnification(typeMagnification)
        BattleLog.hp(this)
    }

    /**
     * 残りHPによって発動する道具や特性に関する処理を行います。
     */
    private fun remainingDamageDecision() {
        if (status.isOneThird) {
            ability.doOneThird()
        }
    }

    /**
     * まひになった時にその処理を行います。
     */
    private val pAR: Unit
        get() {
            if (statusAilment != "") {
                BattleLog.statusAilmentError()
                return
            }
            if (type.isPARCheck) {
                BattleLog.parError()
                return
            }
            statusAilment = "まひ"
            status.getPAR()
            BattleLog.par(name)
        }
    val isDead: Boolean
        get() = status.isDead

    /**
     * やけど処理を行います
     */
    private fun brn() {
        if (statusAilment != "") {
            BattleLog.statusAilmentError()
            return
        }
        if (type.isPARCheck) {
            BattleLog.parError()
            return
        }
        statusAilment = "やけど"
        status.getBAR()
        BattleLog.brn(name)
    }

    /**
     * 残りHP実数値を返す
     * ポケモンの残りHP実数値の数字だけを返します。
     *
     * @return 残りHP実数値
     */
    val currentHP: Int
        get() = status.currentHP

    /**
     * 現在のHPを返す
     * ポケモンの現在のHP・最大HP・ステータスバーの色を返します。
     *
     * @return 現在のHP
     */
    val currentHP2: String
        get() = status.currentHP2

    /**
     * ランク変化を行う
     * 技等でランク変化が起こった時にその処理を行います。
     *
     * @param item 変化するステータス(1:攻撃、2:防御、3:特攻、4:特防、5:素早さ)
     * @param i    プラスマイナス何段階変化するか
     */
    private fun rankUp(item: Int, i: Int) {
        status.rankUp(item, i)
        BattleLog.rankUp(name, item, i)
    }

    /**
     * 素早さを返す
     * ポケモンの素早さ実数値を返します。
     *
     * @return ポケモンの素早さ実数値
     */
    val s: Int
        get() {
            var realSpeed = status.s
            if (good?.isSpeedBoost == true) {
                realSpeed = (realSpeed * CalculationConst.ONE_POINT_FIVE).roundToInt()
            }
            return realSpeed
        }

    /**
     * 特性を返す
     * ポケモンの特性を返します。
     *
     * @return ポケモンの特性
     */
    fun getAbility(): Ability {
        return ability
    }

    /**
     * 道具所持判定
     * ポケモンが道具を持っているかを返します
     * @return ポケモンが道具を持っていたらtrue
     */
    fun hasGood(): Boolean {
        return good != null
    }

    /**
     * 道具消失処理
     * 道具をなくす処理を行います
     */
    private fun lostGood() {
        good = null
    }

    /**
     * タイプ変換処理
     * タイプを変換する処理を行います
     *
     * @param moveType 変更するタイプ
     */
    fun changeType(moveType: String) {
        type = Type(moveType)
        BattleLog.changeType(name, moveType)
    }

    /**
     * ポケモン交代処理
     * ポケモンを交代する際に交代されるポケモンのリセットを行います
     */
    fun changePokemon() {
        if (ability.isLibero) {
            type = originalType.copy()
        }
        status.rankReset()
    }

    /**
     * グッズ所持を返します
     * @return グッズインスタンス
     */
    fun getGood(): Good? {
        return good
    }

    /**
     * リベロやへんげんじざいによるタイプ変化を行う(出した技のタイプに変わる)
     *
     * @param type 出す技のタイプ
     */
    fun libero(type: Type) {
        this.type = type
    }

    /**
     * ターン終了時の処理を行う
     */
    fun turnEndDisposal() {
        if (good?.isLeftOvers == true) {
            BattleLog.LeftOvers(name)
            status.recoveryOnePointSixteen()
        }
        if (good?.isGoodUsed == true) {
            lostGood()
        }
    }

    /**
     * 変化技の処理をする
     * 自分にかかった変化技の処理を行います。
     *
     * @param a 自分に向けられた変化技のインスタンス
     */
    fun takeChange(a: PokemonMove) {
        val whiteHerb = ArrayList<Int>()
        if (a.isMoveNameCheck(MoveConst.SHELL_SMASH)) {
            rankUp(2, -1)
            whiteHerb.add(2)
            rankUp(4, -1)
            whiteHerb.add(4)
            rankUp(1, 2)
            rankUp(3, 2)
            rankUp(5, 2)
        }
        if (a.isMoveNameCheck(MoveConst.EERIE_IMPULSE)) {
            rankUp(3, -2)
            whiteHerb.add(3)
        }
        if (a.isMoveNameCheck(MoveConst.THUNDER_WAVE)) {
            pAR
        }
        if (a.isMoveNameCheck(MoveConst.CALM_MIND)) {
            rankUp(3, 1)
            rankUp(4, 1)
        }
        if (a.isMoveNameCheck(MoveConst.HARDEN)) {
            rankUp(2, 1)
        }
        if (a.isMoveNameCheck(MoveConst.SOAK)) {
            type = Type("みず")
            BattleLog.changeType(name, "みず")
        }
        if (a.isMoveNameCheck(MoveConst.WILL_O_WISP)) {
            brn()
        }
        if (good?.isWhiteHerb == true && whiteHerb.isNotEmpty()) {
            whiteHerb.forEach(Consumer { i: Int? -> status.rankReset(i) })
            BattleLog.whiteHerb(name)
            good!!.goodUsed()
        }
    }


    /**
     * コンストラクタ(登録されてるポケモン以外用)
     * 登録されてるポケモン以外のポケモンやポケモンじゃないものを使うためのコンストラクタ
     */
    init {
        type = Type(type1, type2)
        originalType = Type(type1, type2)
        this.good = good?.let { Good(it) }
        status = Status(base, Effort(effort), Nature(nature))
        this.ability = Ability(ability)
        pokemonDataGet = PokemonDataGet()
    }
}