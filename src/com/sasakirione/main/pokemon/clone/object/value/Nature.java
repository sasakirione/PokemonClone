package com.sasakirione.main.pokemon.clone.object.value;

import java.util.Arrays;

/**
 * ポケモンの性格を担当するクラス
 */
public class Nature {
    /** ポケモンの性格一覧 */
    private static final String[] NATURE = {"さみしがり","いじっぱり","やんちゃ","ゆうかん","ずぶとい","わんぱく","のうてんき",
        "のんき","ひかえめ","おっとり","うっかりや","れいせい","おだやか","おとなしい","しんちょう","なまいき","おくびょう","せっかち",
        "ようき","ようき","むじゃき","てれや","がんばりや","すなお","きまぐれ","まじめ"};
    /** 設定された性格 */
    private final String nature;

    /**
     * 性格クラスのコンストラクタ
     * 性格を設定します。
     * @param nature 性格
     * @throws IllegalArgumentException 存在しない性格を与えられた時に投げられます
     */
    public Nature(String nature) {
        if (!isNatureCheck(nature)) {
            throw new IllegalArgumentException("存在しない性格です");
        }
        this.nature = nature;
    }

    /**
     * 性格の存在をチェックする
     * 与えられた性格が存在する性格かチェックしてその結果を返します。
     * @param nature 性格
     * @return 性格が存在する場合はtrue
     */
    public boolean isNatureCheck(String nature) {
        return Arrays.asList(NATURE).contains(nature);
    }

    /**
     * 性格上昇補正を返す
     * 設定された性格で上昇するステータスを返します。
     * @return 攻撃が上昇する場合は1、防御が上昇する場合は2、特攻が上昇する場合は3、特防が上昇する場合は4、素早さが上昇する場合は5を返す
     * @throws IllegalArgumentException おまもり
     */
    public int plusNature() {
        if (nature.equals("さみしがり") || nature.equals("いじっぱり") || nature.equals("やんちゃ") || nature.equals("ゆうかん")) {
            return 1;
        }
        if (nature.equals("ずぶとい") || nature.equals("わんぱく") || nature.equals("のうてんき") || nature.equals("のんき")) {
            return 2;
        }
        if (nature.equals("ひかえめ") || nature.equals("おっとり") || nature.equals("うっかりや") || nature.equals("れいせい")) {
            return 3;
        }
        if (nature.equals("おだやか") || nature.equals("おとなしい") || nature.equals("しんちょう") || nature.equals("なまいき")) {
            return 4;
        }
        if (nature.equals("おくびょう") || nature.equals("ようき") || nature.equals("せっかち") || nature.equals("むじゃき")) {
            return 5;
        }
        throw new IllegalArgumentException("存在しない性格です");
    }

    /**
     * 性格下降補正を返す
     * 設定された性格で下降するステータスを返します。
     * @return 攻撃が下降する場合は1、防御が下降する場合は2、特攻が下降する場合は3、特防が下降する場合は4、素早さが下降する場合は5を返す
     * @throws IllegalArgumentException おまもり
     */
    public int minusNature() {
        if (nature.equals("おくびょう") || nature.equals("ひかえめ") || nature.equals("ずぶとい") || nature.equals("おだやか")) {
            return 1;
        }
        if (nature.equals("さみしがり") || nature.equals("おっとり") || nature.equals("おとなしい") || nature.equals("せっかち")) {
            return 2;
        }
        if (nature.equals("ようき") || nature.equals("いじっぱり") || nature.equals("わんぱく") || nature.equals("しんちょう")) {
            return 3;
        }
        if (nature.equals("やんちゃ") || nature.equals("のうてんき") || nature.equals("うっかりや") || nature.equals("むじゃき")) {
            return 4;
        }
        if (nature.equals("ゆうかん") || nature.equals("のんき") || nature.equals("れいせい") || nature.equals("なまいき")) {
            return 5;
        }
        throw new IllegalArgumentException("存在しない性格です");
    }

    /**
     * まじめ系の性格かチェックする
     * 設定された性格がまじめ系の性格かをチェックする。
     * @return まじめ系の性格(上昇下降補正なし)だった場合はtrue
     */
    public boolean isMajime() {
        return nature.equals("てれや") || nature.equals("がんばりや") || nature.equals("すなお") || nature.equals("きまぐれ") || nature.equals("まじめ");
    }
}
