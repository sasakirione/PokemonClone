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
        String ability = pokemon.getAbility();
        if (ability.equals("サイコメーカー")) {
            this.field = new Field("サイコフィールド");
            BattleLog.expandPsychoMaker(pokemon.getName());
        }
    }

    /**
     * ターンを進める
     * 1ターン分のバトル処理を行います。
     * @param a Aサイドのポケモンのわざのインスタンス
     * @param b Bサイドのポケモンのわざのインスタンス
     * @return どちらかのポケモンが瀕死状態になるか最初から瀕死状態の時に「おわりだよ」と返します。
     */
    public String forwardTurn(PokemonMove a, PokemonMove b) {
        if (this.pokemonInBattleA.getCurrentHP() == 0 || this.pokemonInBattleB.getCurrentHP() == 0) {
            return "おわりだよ";
        }
        if (priorityDecision(a, b) == 1) {
            attackSideA(a);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideB(b);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            return "なにもなし";
        }
        if (priorityDecision(a, b) == 2) {
            attackSideB(b);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideA(a);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            return "なにもなし";
        }
        if (rapidityDecision() == 1) {
            attackSideB(b);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideA(a);
        } else {
            attackSideA(a);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideB(b);
        }
        return "何もなし";
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
        if (a.getMoveClass() == 2) {
           this.pokemonInBattleA.takeChange(a);
        } else {
            this.pokemonInBattleB.takeDamage(a);
            BattleLog.hp(pokemonInBattleB);
        }
        if (pokemonInBattleB.getCurrentHP() == 0) {
            BattleLog.death(pokemonInBattleB);
            this.matchEndFlag = true;
        }
    }

    private boolean psychofieldCheck(PokemonMove move) {
        return (0 < move.getPriority());
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
        if (b.getMoveClass() == 2) {
            this.pokemonInBattleB.takeChange(b);
        } else {
            this.pokemonInBattleA.takeDamage(b);
            BattleLog.hp(pokemonInBattleA);
        }
        if (pokemonInBattleA.getCurrentHP() == 0) {
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
}
