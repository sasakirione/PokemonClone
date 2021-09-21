package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.object.PokemonTypeCompatibility;

import java.util.Arrays;

/**
 * ポケモンのタイプを担当するクラス
 */
public class Type {
    /** ポケモンが持ってるタイプ */
    private final String[] types;
    /** ポケモンのタイプ一覧 */
    private static final String[] TYPE = {"ノーマル","ほのお","みず","でんき","くさ","こおり","かくとう","どく","じめん",
            "ひこう","エスパー","むし","いわ","ゴースト","ドラゴン","あく","はがね","フェアリー"};

    /**
     * コンストラクタ(複合タイプ)
     */
    public Type(String type1, String type2) {
        if (isTypeErrorCheck(type1)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        if (isTypeErrorCheck(type2)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        types = new String[]{type1, type2};
    }

    /**
     * コンストラクタ(単一タイプ)
     */
    public Type(String type1) {
        if (isTypeErrorCheck(type1)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        types = new String[]{type1};
    }

    /**
     * コンストラクタ(ハロウィンorもりののろい+複合タイプ)
     */
    public Type(String type1, String type2, String type3) {
        if (isTypeErrorCheck(type1)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        if (isTypeErrorCheck(type2)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        if (isTypeErrorCheck(type3)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        types = new String[]{type1, type2, type3};
    }

    private Type(String[] types) {
        this.types = types;
    }

    /**
     * タイプが存在するかの判定
     * 入力されたStringがタイプとして存在するならTrue、そうでないならFalseを返す。
     * @param type タイプの文字列
     * @return タイプが存在するならtrue
     */
    public boolean isTypeErrorCheck(String type) {
        return !isContemporaryTypeCheck(TYPE, type);
    }

    /**
     * 相性倍率の計算
     * 相性倍率を計算してそれを返します
     * @param attackType 攻撃技のタイプ
     * @return double型の相性倍率
     */
    public double getTypeMagnification(String attackType) {
        double magnification = 1.0;
        for (String type : types) {
            magnification *= PokemonTypeCompatibility.typeCompatibility(attackType, type);
        }
        return magnification;
    }

    /**
     * タイプ一致の判定
     * 出そうとする技はタイプ一致技かどうかを判定します。
     * @param type 攻撃技のタイプ
     * @return 攻撃技のタイプが設定されたタイプに含まれるならtrue
     */
    public boolean isTypeMatch(String type) {
        return isContemporaryTypeCheck(this.types, type);
    }

    /**
     * まひチェック
     * まひになるタイプかチェックします。
     * @return まひにならないタイプ(でんきタイプ)ならtrue
     */
    public boolean isPARCheck() {
        return isContemporaryTypeCheck(this.types, "でんき");
    }

    /**
     * タイプの判定
     * 様々なタイプの判定の共通化を行っています。
     * @param type1 判定するタイプ
     * @param type2 判定されるタイプ
     * @return type1の中にtype2が含まれていたらtrue
     */
    private boolean isContemporaryTypeCheck(String[] type1, String type2) {
        return Arrays.asList(type1).contains(type2);
    }


    public Type copy() {
        return new Type(this.types.clone());
    }

    @Override
    public String toString() {
        String type1 = types[0];
        String type2;
        if (types.length == 1) {
            type2 = "";
        } else {
            type2 = types[1];
        }
        return type1 + type2;
    }
}
