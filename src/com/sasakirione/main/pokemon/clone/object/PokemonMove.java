package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.object.value.*;
import com.sasakirione.main.pokemon.clone.utility.CalculationUtility;

import java.util.Random;

/**
 * ポケモンのわざを担当するクラス
 */
public class PokemonMove {
    /** 技の名前 */
    private final String moveName;
    /** ポケモン */
    private final Pokemon pokemon;
    /** 技の種類 */
    private MoveClass moveClass;
    /** 技の威力 */
    private int moveDamage;
    /** 技のタイプ */
    private String moveType;
    /** 技の優先度 */
    private final int priority;
    /** 技の命中率 */
    private final int accuracy;

    public PokemonMove(String name, Pokemon pokemon, MoveClass moveClass, int moveDamage, String moveType,
                       int priority, int accuracy) {
        this.moveName = name;
        this.moveClass = moveClass;
        this.priority = priority;
        this.moveDamage = moveDamage;
        this.moveType = moveType;
        this.accuracy = accuracy;
        this.pokemon = pokemon;
    }

    /**
     * コンストラクタ
     * 技クラスのコンストラクタです。
     * @param name わざの名前
     */
    public PokemonMove(String name, Pokemon pokemon) {
        this.moveName = name;
        this.priority = 0;
        this.accuracy = 100;
        this.pokemon = pokemon;

        if (name.equals("サンダープリズン")) {
            this.moveClass = MoveClass.SPECIAL;
            this.moveDamage = 80;
            this.moveType = "でんき";
        }
        if (name.equals("からをやぶる")) {
            this.moveClass = MoveClass.SELF_CHANGE;
            this.moveType = "ノーマル";
        }
        if (name.equals("ちきゅうなげ")) {
            this.moveClass = MoveClass.PHYSICS;
            this.moveType = "ノーマル";
        }
        if (name.equals("かいでんぱ")) {
            this.moveClass = MoveClass.ENEMY_CHANGE;
            this.moveType = "でんき";
        }
        if (name.equals("でんじは")) {
            this.moveClass = MoveClass.ENEMY_CHANGE;
            this.moveType = "でんき";
        }
        if (name.equals("めいそう")) {
            this.moveClass = MoveClass.SELF_CHANGE;
            this.moveType = "エスパー";
        }

    }

    /**
     * わざの名前を返す
     * わざの名前を返します。
     * @return わざの名前
     */
    public String getMoveName() {
        return moveName;
    }

    /**
     * わざの種類を返す(テスト用)
     * わざの種類を返します。
     * @return わざの種類(0:物理技、1:特殊技、2：自分にかかる変化技、3:相手にかかる変化技、4：場にかかる変化技)
     */
    public MoveClass getMoveClass() {
        return moveClass;
    }

    /**
     * 攻撃実数値を返す
     * 攻撃または特防の実数値を返します
     * @return 物理技の場合は攻撃実数値、特殊技の場合は特攻実数値
     */
    public int getRealAttack() {
        if (moveClass.equals(MoveClass.PHYSICS)) {
            return this.pokemon.getStatus().getA();
        } else {
            return this.pokemon.getStatus().getC();
        }
    }

    /**
     * タイプ一致倍率を返す
     * タイプ一致わざの場合にタイプ一致の倍率を返します
     * @return タイプ一致わざの場合は1.5
     */
    public double getMagnification() {
        if (pokemon.getType().isTypeMatch(moveType)) {
            return (6144.0/4096.0);
        }
        return (1.0);
    }

    /**
     * わざのタイプを返す
     * わざのタイプを返します。
     * @return わざのタイプ
     */
    public String getMoveType() {
        return this.moveType;
    }

    /**
     * わざの優先度を返す
     * わざの優先度を返します。
     * @return わざの優先度
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * 自分にかかる変化技判定
     * 自分にかかる変化技かを判定します
     * @return 自分にかかる変化技だった場合はtrue
     */
    public boolean isSelfChangeMove() {
        return this.moveClass.equals(MoveClass.SELF_CHANGE);
    }

    /**
     * 相手にかかる変化技判定
     * 相手にかかる変化技かを判定します
     * @return 相手にかかる変化技だった場合はtrue
     */
    public boolean isEnemyChangeMove() { return this.moveClass.equals(MoveClass.ENEMY_CHANGE); }

    /**
     * 物理技判定
     * 物理技かを判定します
     * @return 物理技だった場合はtrue
     */
    public boolean isPhysicsMove() {return this.moveClass.equals(MoveClass.PHYSICS); }

    /**
     * 技の名前判定
     * 入力された技の名前か判定します
     * @param name わざの名前
     * @return 入力された技の名前と同じ技だった場合はtrue
     */
    public boolean isMoveNameCheck(String name) {
        return this.moveName.equals(name);
    }

    /**
     * サイコフィールド処理
     * 対象の技に倍率をかけます
     */
    public void psychoBoost() {
        if (this.moveType.equals("エスパー")) {
            fieldBoost();
        }
    }

    /**
     * エレキフィールド処理
     * 対象の技に倍率をかけます
     */
    public void electricBoost() {
        if (this.moveType.equals("でんき")) {
            fieldBoost();
        }
    }

    /**
     * フィールドバフ処理
     * フィールドにより技の威力にバフをかけます
     */
    private void fieldBoost() {
        if (this.moveClass.equals(MoveClass.PHYSICS) || this.moveClass.equals(MoveClass.SPECIAL)) {
            this.moveDamage = (int) Math.round(this.moveDamage * CalculationConst.ONE_POINT_THREE);
        }
    }

    public double getPower() {
        double a = Math.floor(50 * 0.4 + 2);
        double b = a * moveDamage * getRealAttack();
        return CalculationUtility.fiveOutOverFiveIn(b * this.pokemon.getAbility().powerBoost(this) * this.pokemon.getGood().powerBoost(moveClass));
    }

    public void libero() {
        pokemon.libero(new Type(moveType));
    }

    public boolean isMoveHit() {
        return  randomForAccuracy() < this.accuracy;
    }

    public int randomForAccuracy() {
        Random random = new Random();
        return random.nextInt(100);
    }

    public void endDecision() {
        if (this.pokemon.getGood().isDamageOneEighth()) {
            this.pokemon.getStatus().damageOneEighth();
        }
    }
}