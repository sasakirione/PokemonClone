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
    effort: IntArray?,
    base: IntArray?,
    good: String?,
    nature: String?,
    type1: String?,
    type2: String?,
    ability: String?) {

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
        if (good?.isChoice!!) {
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
        if (good?.isChoice!!) {
            choiceCheck(name)
        }
        return pokemonDataGet.getMoveByName(name, this)
    }

    /**
     * こだわりチェック
     * こだわりアイテムを持ってる場合にこだわっているわざ以外のわざを使えないようにチェックします
     *
     * @param name わざの名前
     * @throws IllegalArgumentException こだわってるわざ以外を使おうとすると投げます
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
        if (good?.isGoodUsed!!) {
            lostGood()
        }
    }

    private fun getDefenseChoice(a: PokemonMove): Int {
        val defenseChoice: Int = if (a.isPhysicsMove) {
            2
        } else {
            4
        }
        return defenseChoice
    }

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

    private fun getPower(a: PokemonMove): Double {
        val power: Double = if (ability.isUnware) {
            a.power2
        } else {
            a.power
        }
        return power
    }

    private fun moveDecision(power: Double, defenseChoice: Int, typeMagnification: Double, magnification: Double, vitalRank: Int) {
        status.damageCalculation(power, defenseChoice, magnification, vitalRank)
        BattleLog.typeMagnification(typeMagnification)
        BattleLog.hp(this)
    }

    /**
     * 残りHP処理
     * 残りHPによって発動する道具や特性に関する処理を行います。
     */
    private fun remainingDamageDecision() {
        if (status.isOneThird) {
            ability.doOneThird()
        }
    }

    /**
     * まひ処理を行う
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
            if (good?.isSpeedBoost!!) {
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

    fun hasGood(): Boolean {
        return good != null
    }

    private fun lostGood() {
        good = null
    }

    fun changeType(moveType: String?) {
        type = Type(moveType)
        BattleLog.changeType(name, moveType)
    }

    fun changePokemon() {
        if (ability.isLibero()) {
            type = originalType.copy()
        }
        status.rankReset()
    }

    fun getGood(): Good? {
        return good
    }

    fun libero(type: Type) {
        this.type = type
    }

    fun turnEndDisposal() {
        if (good?.isLeftOvers()!!) {
            BattleLog.LeftOvers(name)
            status.recoveryOnePointSixteen()
        }
        if (good!!.isGoodUsed()) {
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
        if (good?.isWhiteHerb!! && whiteHerb.isNotEmpty()) {
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
        this.good = Good(good)
        status = Status(base, Effort(effort), Nature(nature))
        this.ability = Ability(ability)
        pokemonDataGet = PokemonDataGet()
    }
}