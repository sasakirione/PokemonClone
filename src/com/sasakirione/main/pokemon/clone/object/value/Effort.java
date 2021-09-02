package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.exception.EvArgumentException;

import java.util.Arrays;

/**
 * ポケモンの努力値を担当するクラス
 */
public class Effort {
    /** HPの努力値 */
    int h;
    /** 攻撃の努力値 */
    int a;
    /** 防御の努力値 */
    int b;
    /** 特攻の努力値 */
    int c;
    /** 特防の努力値 */
    int d;
    /** 素早さの努力値 */
    int s;

    /**
     * 努力値クラスのコンストラクタ
     * 努力値を設定します。
     * @param effort 努力値
     * @throws IllegalArgumentException 努力値の値が不正な時に投げられます
     */
    public Effort(int[] effort) {
        Arrays.stream(effort).filter(i -> i > 252).forEach(i -> {throw new EvArgumentException("努力値が不正です");});
        int sum = Arrays.stream(effort).sum();
        if (sum > 510) {
            throw new EvArgumentException("努力値の合計が510を超えています");
        }
        this.h = effort[0];
        this.a = effort[1];
        this.b = effort[2];
        this.c = effort[3];
        this.d = effort[4];
        this.s = effort[5];
    }

    /**
     * HPの努力値を返す
     * 設定された努力値のうちHPの努力値を返します。
     * @return HPの努力値
     */
    public int getH() {
        return h;
    }

    /**
     * 攻撃の努力値を返す
     * 設定された努力値のうち攻撃の努力値を返します。
     * @return 攻撃の努力値
     */
    public int getA() {
        return a;
    }

    /**
     * 防御の努力値を返す
     * 設定された努力値のうち防御の努力値を返します。
     * @return 防御の努力値
     */
    public int getB() {
        return b;
    }

    /**
     * 特攻の努力値を返す
     * 設定された努力値のうち特攻の努力値を返します。
     * @return 特攻の努力値
     */
    public int getC() {
        return c;
    }

    /**
     * 特防の努力値を返す
     * 設定された努力値のうち特防の努力値を返します。
     * @return 特防の努力値
     */
    public int getD() {
        return d;
    }

    /**
     * 素早さの努力値を返す
     * 設定された努力値のうち素早さの努力値を返します。
     * @return 素早さの努力値
     */
    public int getS() {
        return s;
    }
}
