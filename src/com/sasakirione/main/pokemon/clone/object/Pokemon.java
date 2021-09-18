package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.data.PokemonDataGet;
import com.sasakirione.main.pokemon.clone.data.PokemonDataGetInterface;
import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.value.*;

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
        if (type2.equals("")) {
            this.type = new Type(type1);
        } else {
            this.type = new Type(type1, type2);
        }
        if (type2.equals("")) {
            this.originalType = new Type(type1);
        } else {
            this.originalType = new Type(type1, type2);
        }
        this.status = new Status(base, new Effort(effort), good, new Nature(nature), this.getName());
        this.ability = new Ability(ability);
        this.good = new Good(good);
        pokemonDataGet = new PokemonDataGet();
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
        double power = a.getPower();
        int defenseChoice;
        double typeMagnification = this.type.getTypeMagnification(a.getMoveType());
        double magnification = a.getMagnification() * typeMagnification;

        if (a.isPhysicsMove()) {
            defenseChoice = 2;
        } else {
            defenseChoice = 4;
        }
        this.status.damageCalculation(power, defenseChoice, magnification, a.getMoveType());
        BattleLog.typeMagnification(typeMagnification);
        BattleLog.hp(this);
        a.endDecision();
        remainingDamageDecision();
    }

    private void remainingDamageDecision() {
        if (ability.isTorrent() && status.isOneThird()) {
            ability.abilityOn();
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
        double realSpeed = this.status.getS();
        if (good.isSpeedBoost()) {
            realSpeed = realSpeed * CalculationConst.ONE_POINT_FIVE;
        }
        if (status.isParCheck()) {
            realSpeed = realSpeed * CalculationConst.HALF;
        }
        return (int) Math.round(realSpeed);
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

    private boolean hasGood() {
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
    }


    /**
     * 変化技の処理をする
     * 自分にかかった変化技の処理を行います。
     *
     * @param a 自分に向けられた変化技のインスタンス
     */
    public void takeChange(PokemonMove a) {
        if (a.isMoveNameCheck("からをやぶる")) {
            rankUp(1, 2);
            rankUp(3, 2);
            rankUp(5, 2);
        }
        if (a.isMoveNameCheck("かいでんぱ")) {
            rankUp(3, -2);
        }
        if (a.isMoveNameCheck("でんじは")) {
            getPAR();
        }
        if (a.isMoveNameCheck("めいそう")) {
            rankUp(3, 1);
            rankUp(4, 1);
        }
    }
}
