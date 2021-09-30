package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.value.Field;

/**
 * ポケモンのバトル場を担当するクラス
 */
public class PokemonStadium {
    /** Aサイドのバトル場に出てるポケモン */
    private Pokemon pokemonInBattleA;
    /** Bサイドのバトル場に出てるポケモン */
    private Pokemon pokemonInBattleB;
    /** 試合が終了してるかの判定 */
    private boolean matchEndFlag = false;
    /** フィールド */
    private Field field;
    /** テストモード(技が必中になります) */
    private boolean testMode = false;
    /** Aサイドのバトル場に出てるポケモンの複数ターン技状態*/
    private Boolean isMultipleTurnMoveByA = false;
    /** Aサイドのバトル場に出てるポケモンの複数ターン技インスタンス*/
    private PokemonMove multipleTurnMoveByA;
    /** Bサイドのバトル場に出てるポケモンの複数ターン技状態*/
    private Boolean isMultipleTurnMoveByB = false;
    /** Bサイドのバトル場に出てるポケモンの複数ターン技インスタンス*/
    private PokemonMove multipleTurnMoveByB;

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
     * @param moveSideA Aサイドのポケモンのわざ
     * @param moveSideB Bサイドのポケモンのわざ
     */
    public void forwardTurn(String moveSideA, String moveSideB) {
        PokemonMove moveA = setMove(0, moveSideA);
        PokemonMove moveB = setMove(1, moveSideB);
        this.forwardTurn(moveA, moveB);
    }

    private PokemonMove setMove(int side, String moveName) {
        PokemonMove move;
        if (side == 0) {
            if (this.isMultipleTurnMoveByA) {
                move = this.multipleTurnMoveByA;
                this.multipleTurnMoveByA.forwardTurn();
                if (this.multipleTurnMoveByA.isMultipleTurnMoveEnd()) {
                    this.isMultipleTurnMoveByA = false;
                }
            } else {
                move = pokemonInBattleA.getDamage(moveName);
                if (move.isMultipleTurnMove()) {
                    this.isMultipleTurnMoveByA = true;
                    this.multipleTurnMoveByA = move;
                    move.forwardTurn();
                }
            }
        } else {
            if (this.isMultipleTurnMoveByB) {
                move = this.multipleTurnMoveByB;
                this.multipleTurnMoveByB.forwardTurn();
                if (this.multipleTurnMoveByB.isMultipleTurnMoveEnd()) {
                    this.isMultipleTurnMoveByB = false;
                }
            } else {
                move = pokemonInBattleB.getDamage(moveName);
                if (move.isMultipleTurnMove()) {
                    this.isMultipleTurnMoveByB = true;
                    this.multipleTurnMoveByB = move;
                    move.forwardTurn();
                }
            }
        }
        return move;
    }

    /**
     * ターンを進める(非推奨)
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
        turnEndDisposal();
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
        turnEndDisposal();
    }

    private void turnEndDisposal() {
        pokemonInBattleA.turnEndDisposal();
        pokemonInBattleB.turnEndDisposal();
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
            liberoDisposal(pokemonInBattleA, a);
            this.pokemonInBattleB.takeDamage(a, testMode);
        }
        if (pokemonInBattleB.isDead()) {
            BattleLog.death(pokemonInBattleB);
            this.matchEndFlag = true;
        }
    }

    private void liberoDisposal(Pokemon pokemon, PokemonMove move) {
        if (!pokemon.getAbility().isLibero()) {
            return;
        }
        if (!pokemon.getType().isTypeMatch(move.getMoveType())) {
            BattleLog.ability(pokemon.getName(), pokemon.getAbility());
            pokemon.changeType(move.getMoveType());
            move.libero();
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
            liberoDisposal(pokemonInBattleB, b);
            this.pokemonInBattleA.takeDamage(b, testMode);
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

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public String getPokemonName(int a) {
        if (a == 0) {
            return pokemonInBattleA.getName();
        } else {
            return pokemonInBattleB.getName();
        }
    }

    public void getChangeB(Pokemon pokemon, PokemonMove a) {
        getChange(pokemonInBattleB, pokemon, a, 1);
    }

    public void getChangeA(Pokemon pokemon, PokemonMove b) {
        getChange(pokemonInBattleA, pokemon, b, 0);
    }

    private void getChange(Pokemon pokemonFrom, Pokemon pokemonTo, PokemonMove enemyMove, int side) {
        pokemonFrom.changePokemon();
        String strSide = getStrSide(side);
        BattleLog.change(strSide, pokemonFrom.getName(), pokemonTo.getName());
        if (side == 0) {
            pokemonInBattleA = pokemonTo;
            attackSideB(enemyMove);
        } else {
            pokemonInBattleB = pokemonTo;
            attackSideA(enemyMove);
        }
    }

    private String getStrSide(int side) {
        return switch (side) {
            case 0 -> "A";
            case 1 -> "B";
            default -> "?";
        };
    }
}
