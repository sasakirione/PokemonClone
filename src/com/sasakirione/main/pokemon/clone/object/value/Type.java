package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.object.PokemonTypeCompatibility;

import java.util.Arrays;

public class Type {
    private final String[] types;
    private static final String[] TYPE = {"ノーマル","ほのお","みず","でんき","くさ","こおり","かくとう","どく","じめん",
            "ひこう","エスパー","むし","いわ","ゴースト","ドラゴン","あく","はがね","フェアリー"};


    public Type(String type1, String type2) {
        if (!isTypeCheck(type1)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        if (!isTypeCheck(type2)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        types = new String[]{type1, type2};

    }

    public Type(String type1) {
        if (!isTypeCheck(type1)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        types = new String[]{type1};
    }

    public Type(String type1, String type2, String type3) {
        if (!isTypeCheck(type1)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        if (!isTypeCheck(type2)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        if (!isTypeCheck(type3)) {
            throw new IllegalArgumentException("存在しないタイプが含まれています");
        }
        types = new String[]{type1, type2, type3};
    }

    public boolean isTypeCheck(String type) {
        return isContemporaryTypeCheck(TYPE, type);
    }

    public double getTypeMagnification(String attackType) {
        double magnification = 1.0;
        for (String type : types) {
            magnification *= PokemonTypeCompatibility.typeCompatibility(attackType, type);
        }
        return magnification;
    }

    public boolean isTypeMatch(String type) {
        return isContemporaryTypeCheck(this.types, type);
    }

    public boolean isPARCheck() {
        return isContemporaryTypeCheck(this.types, "でんき");
    }

    private boolean isContemporaryTypeCheck(String[] type1, String type2) {
        boolean typeCheck = Arrays.asList(type1).contains(type2);
        return typeCheck;
    }


}
