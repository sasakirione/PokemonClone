package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.value.Field;

/**
 * ポケモンのバトル場を担当するクラス
 */
public class PokemonStadium {
    /** Aサイドのバトル場に出てるポケモン */
    private final Pokemon pokemonInBattleA;
    /** Bサイドのバトル場に出てるポケモン */
    private final Pokemon pokemonInBattleB;
    /** 試合が終了してるかの判定 */
    private boolean matchEndFlag = false;
    /** フィールド */
    private Field field;

    /**
     * バトル場クラスのコンストラクタ
     * ポケモンをバトル場に設定します。
     * @param pokemonA Aサイドのバトル場に出すポケモンのインスタンス
     * @param pokemonB Bサイドのバトル場に出すポケモンのインスタンス
     */
    public PokemonStadium(Pokemon pokemonA, Pokemon pokemonB) {
        this.pokemonInBattleA = pokemonA;
        this.pokemonInBattleB = pokemonB;
        BattleLog.start();
        BattleLog.startBattle("A",pokemonA);
        BattleLog.startBattle("B",pokemonB);
        setField(true,true);
    }

    /**
     * メーカーでフィールドの展開
     * サイコメーカー、ミストメーカー、エレキメーカー、グラスメーカーの特性がある場合にフィールドを展開する。
     * @param a a側のフィールドを展開するか
     * @param b b側のフィールドを展開するか
     */
    private void setField(Boolean a, Boolean b) {
        if (a) {
            makeField(pokemonInBattleA);
        }
        if (b) {
            makeField(pokemonInBattleB);
        }
    }

    /**
     * メーカーでフィールドの展開
     * サイコメーカー、ミストメーカー、エレキメーカー、グラスメーカーの特性がある場合にフィールドを展開する。
     * @param pokemon ポケモンのインスタンス
     */
    private void makeField(Pokemon pokemon) {
        if (pokemon.getAbility().isPsychoMaker()) {
            this.field = new Field("サイコフィールド");
            BattleLog.expandPsychoMaker(pokemon.getName());
        }
    }

    /**
     * ターンを進める
     * 1ターン分のバトル処理を行います。
     * @param a Aサイドのポケモンのわざのインスタンス
     * @param b Bサイドのポケモンのわざのインスタンス
     */
    public void forwardTurn(PokemonMove a, PokemonMove b) {
        if (this.matchEndFlag) {
            return;
        }
        fieldWeatherBoost(a,b);
        if (priorityDecision(a, b) == 1) {
            attackAAfterB(a, b);
            return;
        }
        if (priorityDecision(a, b) == 2) {
            attackBAfterA(a, b);
            return;
        }
        if (rapidityDecision() == 1) {
            attackBAfterA(a, b);
        } else {
            attackAAfterB(a, b);
        }
    }

    private void attackBAfterA(PokemonMove a, PokemonMove b) {
        attackSideB(b);
        if (matchEndFlag) {
            return;
        }
        attackSideA(a);
        if (matchEndFlag) {
            return;
        }
        turnEndFieldDisposal();
    }

    private void attackAAfterB(PokemonMove a, PokemonMove b) {
        attackSideA(a);
        if (matchEndFlag) {
            return;
        }
        attackSideB(b);
        if (matchEndFlag) {
            return;
        }
        turnEndFieldDisposal();
    }

    private void fieldWeatherBoost(PokemonMove a, PokemonMove b) {
        if (this.field != null) {
            if (this.field.isPsychofield()) {
                a.psychoBoost();
                b.psychoBoost();
            }
        }
    }

    private void turnEndFieldDisposal() {
        if (this.field != null) {
            field.forwardTurn();
            if (this.field.isEndField()) {
                BattleLog.endField(this.field);
                this.field = null;
            }
        }
    }

    /**
     * 攻撃を行う(Aサイド)
     * Aサイドのポケモンの攻撃処理を行います。
     * @param a ポケモンのわざのインスタンス
     */
    private void attackSideA (PokemonMove a) {
        BattleLog.attack(pokemonInBattleA, a);
        if (psychofieldCheck(a)) {
            BattleLog.psychofieldPriority(pokemonInBattleB.getName());
            return;
        }
        if (a.isSelfChangeMove()) {
           this.pokemonInBattleA.takeChange(a);
        } else {
            this.pokemonInBattleB.takeDamage(a);
            BattleLog.hp(pokemonInBattleB);
        }
        if (pokemonInBattleB.isDead()) {
            BattleLog.death(pokemonInBattleB);
            this.matchEndFlag = true;
        }
    }

    private boolean psychofieldCheck(PokemonMove move) {
        return ((0 < move.getPriority()) && (this.field != null) && (this.field.isPsychofield()));
    }

    /**
     * 攻撃を行う(Bサイド)
     * Bサイドのポケモンの攻撃処理を行います。
     * @param b ポケモンのわざのインスタンス
     */
    private void attackSideB(PokemonMove b) {
        BattleLog.attack(pokemonInBattleB, b);
        if (psychofieldCheck(b)) {
            BattleLog.psychofieldPriority(pokemonInBattleA.getName());
            return;
        }
        if (b.isSelfChangeMove()) {
            this.pokemonInBattleB.takeChange(b);
        } else {
            this.pokemonInBattleA.takeDamage(b);
            BattleLog.hp(pokemonInBattleA);
        }
        if (pokemonInBattleA.isDead()) {
            BattleLog.death(pokemonInBattleA);
            this.matchEndFlag = true;
        }
    }

    /**
     * 素早さを比較する
     * ポケモンの素早さ実数値を比較します。
     * @return AサイドのポケモンがBサイドのポケモンより速い場合は0、逆の場合は1
     */
    private int rapidityDecision() {
        if (pokemonInBattleA.getS() < pokemonInBattleB.getS()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 技の優先度を比較する
     * 技の優先度を比較します。
     * @param a Aサイドのポケモンのわざのインスタンス
     * @param b Bサイドのポケモンのわざのインスタンス
     * @return Aサイドの優先度が高い場合は1、Bサイドの優先度が高い場合は2、優先度が同じ場合は0
     */
    private int priorityDecision(PokemonMove a, PokemonMove b) {
        if (b.getPriority() < a.getPriority()) {
            return 1;
        }
        if (a.getPriority() < b.getPriority()) {
            return 2;
        }
        return 0;
    }

    public boolean getEndFlag() {
        return this.matchEndFlag;
    }
}
