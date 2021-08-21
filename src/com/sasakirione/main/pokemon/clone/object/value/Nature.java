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
        if (nature.equals("ひかえめ")) {
            return 3;
        }
        if (nature.equals("おくびょう") || nature.equals("ようき")) {
            return 5;
        }
        throw new IllegalArgumentException("存在しない性格です");
    }

    public int minusNature() {
        if (nature.equals("おくびょう") || nature.equals("ひかえめ")) {
            return 1;
        }
        if (nature.equals("ようき")) {
            return 3;
        }
        throw new IllegalArgumentException("存在しない性格です");
    }
}
