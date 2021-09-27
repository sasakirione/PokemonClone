package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.constant.MoveConst;
import com.sasakirione.main.pokemon.clone.data.PokemonDataGet;
import com.sasakirione.main.pokemon.clone.data.PokemonDataGetInterface;
import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.value.*;

import java.util.ArrayList;

/**
 * ポケモン自体を表すクラス
 */
public class Pokemon {
    /**
     * ポケモンの名前
     */
    private final String name;
    /**
     * ポケモンのステータス
     */
    private final Status status;
    /**
     * ポケモンのタイプ
     */
    private Type type;
    /**
     * ポケモンの初期タイプ
     */
    private final Type originalType;
    /**
     * ポケモンの特性
     */
    private Ability ability;
    /**
     * ポケモンがこだわってる場合のわざ
     */
    private String choiceMove = null;
    /**
     * ポケモンの状態異常(状態異常がなしの場合は空文字)
     */
    private String statusAilment = "";
    /**
     * ポケモンの道具
     */
    private Good good;
    /**
     * データ取得用インスタンス
     */
    PokemonDataGetInterface pokemonDataGet;

    /**
     * コンストラクタ(登録されてるポケモン以外用)
     * 登録されてるポケモン以外のポケモンやポケモンじゃないものを使うためのコンストラクタ
     *
     * @param name    ポケモンの名前
     * @param effort  ポケモンの努力値
     * @param base    ポケモンの種族値
     * @param good    ポケモンの道具
     * @param nature  ポケモンの性格
     * @param type1   ポケモンのタイプ1
     * @param type2   ポケモンのタイプ2
     * @param ability ポケモンの特性
     */
    public Pokemon(String name, int[] effort, int[] base, String good, String nature, String type1, String type2, String ability) {
        this.name = name;
        this.type = new Type(type1, type2);
        this.originalType = new Type(type1, type2);
        this.good = new Good(good);
        this.status = new Status(base, new Effort(effort), new Nature(nature));
        this.ability = new Ability(ability);
        this.pokemonDataGet = new PokemonDataGet();
    }

    /**
     * ポケモンの名前を返す
     * ポケモンの名前を返します。
     *
     * @return ポケモンの名前
     */
    public String getName() {
        return this.name;
    }

    /**
     * ポケモンのわざを出す(データベースにない定義済みの技用)
     * ポケモンのわざを担当するクラスをインスタンス化して返します。
     *
     * @param name わざの名前
     * @return ポケモンのわざクラスのインスタンス
     */
    public PokemonMove getDamage2(String name) {
        if (good.isChoice()) {
            choiceCheck(name);
        }
        return new PokemonMove(name, this);
    }

    /**
     * ポケモンのわざを出す
     * ポケモンのわざを担当するクラスをインスタンス化して返します。
     *
     * @param name わざの名前
     * @return ポケモンのわざクラスのインスタンス
     */
    public PokemonMove getDamage(String name) {
        if (good.isChoice()) {
            choiceCheck(name);
        }
        return pokemonDataGet.getMoveByName(name, this);
    }

    /**
     * こだわりチェック
     * こだわりアイテムを持ってる場合にこだわっているわざ以外のわざを使えないようにチェックします
     *
     * @param name わざの名前
     * @throws IllegalArgumentException こだわってるわざ以外を使おうとすると投げます
     */
    private void choiceCheck(String name) {
        if (choiceMove != null && !name.equals(this.choiceMove)) {
            throw new IllegalArgumentException("こだわっています！");
        }
        if (choiceMove == null) {
            this.choiceMove = name;
        }
    }

    /**
     * ポケモンのわざを受ける
     * ポケモンのわざクラスのインスタンスを受け取ってダメージ処理を行います。
     *
     * @param a 受ける技のインスタンス
     */
    public void takeDamage(PokemonMove a, boolean isTest) {
        if (!a.isMoveHit() && !isTest) {
            BattleLog.moveMiss();
            return;
        }
        if (a.isEnemyChangeMove()) {
            takeChange(a);
            return;
        }
        if (a.getMoveName().equals("ちきゅうなげ")) {
            this.status.constantDamage(50);
            return;
        }

        double power = getPower(a);
        int defenseChoice = getDefenseChoice(a);
        double typeMagnification = this.type.getTypeMagnification(a.getMoveType());
        double magnification = a.getMagnification() * typeMagnification;

        moveDecisionAll(a, power, defenseChoice, typeMagnification, magnification);
        a.endDecision();
        if (this.good.isGoodUsed()) {
            this.lostGood();
        }
    }

    private int getDefenseChoice(PokemonMove a) {
        int defenseChoice;
        if (a.isPhysicsMove()) {
            defenseChoice = 2;
        } else {
            defenseChoice = 4;
        }
        return defenseChoice;
    }

    private void moveDecisionAll(PokemonMove a, double power, int defenseChoice, double typeMagnification, double magnification) {
        int count = a.getCombCount();
        int vitalRank = a.getVitalRank();
        for (int i = 0; i < count; i++){
            if (ability.isBakekawa()) {
                ability.abilityOn();
                BattleLog.bakekawa(this.name);
                status.damageOneEighth();
                continue;
            }
            moveDecision(power, defenseChoice, typeMagnification, magnification, vitalRank);
            remainingDamageDecision();
        }
        if (a.isCombAttack()) {
            BattleLog.combAttack(count);
        }
    }

    private double getPower(PokemonMove a) {
        double power;
        if (this.ability.isUnware()) {
            power = a.getPower2();
        } else {
            power = a.getPower();
        }
        return power;
    }

    private void moveDecision(double power, int defenseChoice, double typeMagnification, double magnification, int vitalRank) {
        this.status.damageCalculation(power, defenseChoice, magnification, vitalRank);
        BattleLog.typeMagnification(typeMagnification);
        BattleLog.hp(this);
    }

    /**
     * 残りHP処理
     * 残りHPによって発動する道具や特性に関する処理を行います。
     */
    private void remainingDamageDecision() {
        if (status.isOneThird()) {
            ability.doOneThird();
        }
    }

    /**
     * まひ処理を行う
     * まひになった時にその処理を行います。
     */
    private void getPAR() {
        if (!statusAilment.equals("")) {
            BattleLog.statusAilmentError();
            return;
        }
        if (this.type.isPARCheck()) {
            BattleLog.parError();
            return;
        }
        statusAilment = "まひ";
        status.getPAR();
        BattleLog.par(this.name);
    }

    public boolean isDead() {
        return status.isDead();
    }

    /**
     * 残りHP実数値を返す
     * ポケモンの残りHP実数値の数字だけを返します。
     *
     * @return 残りHP実数値
     */
    public int getCurrentHP() {
        return status.getCurrentHP();
    }

    /**
     * 現在のHPを返す
     * ポケモンの現在のHP・最大HP・ステータスバーの色を返します。
     *
     * @return 現在のHP
     */
    public String getCurrentHP2() {
        return status.getCurrentHP2();
    }

    /**
     * ランク変化を行う
     * 技等でランク変化が起こった時にその処理を行います。
     *
     * @param item 変化するステータス(1:攻撃、2:防御、3:特攻、4:特防、5:素早さ)
     * @param i    プラスマイナス何段階変化するか
     */
    public void rankUp(int item, int i) {
        status.rankUp(item, i);
        BattleLog.rankUp(this.name, item, i);
    }

    /**
     * 素早さを返す
     * ポケモンの素早さ実数値を返します。
     *
     * @return ポケモンの素早さ実数値
     */
    public int getS() {
        int realSpeed = this.status.getS();
        if (good.isSpeedBoost()) {
            realSpeed = (int) Math.round(realSpeed * CalculationConst.ONE_POINT_FIVE);
        }
        return realSpeed;
    }

    /**
     * 特性を返す
     * ポケモンの特性を返します。
     *
     * @return ポケモンの特性
     */
    public Ability getAbility() {
        return ability;
    }

    public boolean hasGood() {
        return this.good != null;
    }

    private void lostGood() {
        this.good = null;
    }

    public Type getType() {
        return this.type;
    }

    public void changeType(String moveType) {
        this.type = new Type(moveType);
        BattleLog.changeType(this.name, moveType);
    }

    public void changePokemon() {
        if (ability.isLibero()) {
           type = originalType.copy();
        }
        status.rankReset();
    }

    public Good getGood() {
        return this.good;
    }

    public void libero(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return this.status;
    }

    public void turnEndDisposal() {
        if (good.isLeftOvers()) {
            BattleLog.LeftOvers(this.name);
            this.status.recoveryOnePointSixteen();
        }
        if (good.isGoodUsed()) {
            lostGood();
        }
    }


    /**
     * 変化技の処理をする
     * 自分にかかった変化技の処理を行います。
     *
     * @param a 自分に向けられた変化技のインスタンス
     */
    public void takeChange(PokemonMove a) {
        ArrayList<Integer> whiteHerb = new ArrayList<>();
        if (a.isMoveNameCheck(MoveConst.SHELL_SMASH)) {
            rankUp(2, -1);
            whiteHerb.add(2);
            rankUp(4, -1);
            whiteHerb.add(4);
            rankUp(1, 2);
            rankUp(3, 2);
            rankUp(5, 2);
        }
        if (a.isMoveNameCheck(MoveConst.EERIE_IMPULSE)) {
            rankUp(3, -2);
            whiteHerb.add(3);
        }
        if (a.isMoveNameCheck(MoveConst.THUNDER_WAVE)) {
            getPAR();
        }
        if (a.isMoveNameCheck(MoveConst.CALM_MIND)) {
            rankUp(3, 1);
            rankUp(4, 1);
        }
        if (a.isMoveNameCheck(MoveConst.HARDEN)) {
            rankUp(2, 1);
        }
        if (a.isMoveNameCheck(MoveConst.SOAK)) {
            this.type = new Type("みず");
            BattleLog.changeType(this.name, "みず");
        }
        if (this.good.isWhiteHerb() && !whiteHerb.isEmpty()) {
            whiteHerb.forEach(i -> getStatus().rankReset(i));
            BattleLog.whiteHerb(this.name);
            this.good.goodUsed();
        }
    }
}
