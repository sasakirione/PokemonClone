package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.object.value.Status;
import com.sasakirione.main.pokemon.clone.object.value.Type;

/**
 * ポケモンのわざを担当するクラス
 */
public class PokemonMove {
    /** 技の名前 */
    private final String moveName;
    /** 0:物理技、1:特殊技、2：自分にかかる変化技、3:相手にかかる変化技、4：場にかかる変化技　*/
    private int moveClass;
    /** 攻撃・特攻の実数値 */
    private final int[] real;
    /** 技の威力 */
    private int moveDamage;
    /** 技のタイプ */
    private String moveType;
    /** 技を出すポケモンのタイプ */
    private final Type types;
    /** 技を出すポケモンのタイプ */
    private int priority;

    /**
     * コンストラクタ
     * 技クラスのコンストラクタです。
     * @param name わざの名前
     * @param status 攻撃側のステータス
     * @param type 攻撃側のタイプ
     */
    public PokemonMove(String name, Status status, Type type) {
        this.moveName = name;
        this.real = new int[]{status.getA(), status.getC()};
        this.types = type;
        this.priority = 0;

        if (name.equals("サンダープリズン")) {
            this.moveClass = 1;
            this.moveDamage = 80;
            this.moveDamage *= 1.5;
            this.moveType = "でんき";
        }
        if (name.equals("ぼうふう")) {
            this.moveClass = 1;
            this.moveDamage = 110;
            this.moveType = "ひこう";
        }
        if (name.equals("ハイドロポンプ")) {
            this.moveClass = 1;
            this.moveDamage = 110;
            this.moveType = "みず";
        }
        if (name.equals("げんしのちから")) {
            this.moveClass = 1;
            this.moveDamage = 60;
            this.moveType = "いわ";
        }
        if (name.equals("シャドーボール")) {
            this.moveClass = 1;
            this.moveDamage = 80;
            this.moveType = "ゴースト";
        }
        if (name.equals("からをやぶる")) {
            this.moveClass =2;
            this.moveType = "ノーマル";
        }
        if (name.equals("ちきゅうなげ")) {
            this.moveClass = 0;
            this.moveType = "ノーマル";
        }
        if (name.equals("かいでんぱ")) {
            this.moveClass =3;
            this.moveType = "でんき";
        }
        if (name.equals("でんじは")) {
            this.moveClass = 3;
            this.moveType = "でんき";
        }
        if (name.equals("かげうち")) {
            this.moveClass = 0;
            this.moveDamage = 40;
            this.moveType = "ゴースト";
            this.priority = 1;
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
     * わざの種類を返す
     * わざの種類を返します。
     * @return わざの種類(0:物理技、1:特殊技、2：自分にかかる変化技、3:相手にかかる変化技、4：場にかかる変化技)
     */
    public int getMoveClass() {
        return moveClass;
    }

    /**
     * わざの威力を返す
     * わざの威力を返します。
     * @return わざの威力
     */
    public int getMoveDamage() {
        return moveDamage;
    }

    /**
     * 攻撃実数値を返す
     * 攻撃または特防の実数値を返します
     * @return 物理技の場合は攻撃実数値、特殊技の場合は特攻実数値
     */
    public int getRealAttack() {
        if (moveClass == 0) {
            return real[0];
        } else {
            return real[1];
        }
    }

    /**
     * タイプ一致倍率を返す
     * タイプ一致わざの場合にタイプ一致の倍率を返します
     * @return タイプ一致わざの場合は1.5
     */
    public double getMagnification() {
        if (types.isTypeMatch(moveType)) {
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
}