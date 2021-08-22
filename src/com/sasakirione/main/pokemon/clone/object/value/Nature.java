package com.sasakirione.main.pokemon.clone.object.value;

import java.util.Arrays;

public class Nature {
    private static final String[] NATURE = {"さみしがり","いじっぱり","やんちゃ","ゆうかん","ずぶとい","わんぱく","のうてんき",
        "のんき","ひかえめ","おっとり","うっかりや","れいせい","おだやか","おとなしい","しんちょう","なまいき","おくびょう","せっかち",
        "ようき","ようき","むじゃき","てれや","がんばりや","すなお","きまぐれ","まじめ"};
    String nature;

    public Nature(String nature) {
        if (!isNatureCheck(nature)) {
            throw new IllegalArgumentException("存在しない性格です");
        }
        this.nature = nature;
    }

    public boolean isNatureCheck(String type) {
        boolean natureCheck = false;
        int count = (int) Arrays.stream(NATURE).filter(s -> s.equals(type)).count();
        if (count == 1) {
            natureCheck = true;
        }
        return natureCheck;
    }

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

    public boolean isMajime() {
        return nature.equals("てれや") || nature.equals("がんばりや") || nature.equals("すなお") || nature.equals("きまぐれ") || nature.equals("まじめ");
    }
}
