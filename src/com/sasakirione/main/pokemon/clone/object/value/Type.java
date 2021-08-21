package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.object.PokemonTypeCompatibility;

import java.util.Arrays;

public class Type {
    private String[] types;
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
        boolean typeCheck = false;
        int count = (int) Arrays.stream(TYPE).filter(s -> s.equals(type)).count();
        if (count == 1) {
            typeCheck = true;
        }
        return typeCheck;
    }

    public double getTypeMagnification(String attackType) {
        double magnification = 1.0;
        for (String type : types) {
            magnification *= PokemonTypeCompatibility.typeCompatibility(attackType, type);
        }
        return magnification;
    }

    public boolean isTypeMatch(String type) {
        boolean typeMatch = false;
        int count = (int) Arrays.stream(types).filter(s -> s.equals(type)).count();
        if (count == 1) {
            typeMatch = true;
        }
        return typeMatch;
    }


}
