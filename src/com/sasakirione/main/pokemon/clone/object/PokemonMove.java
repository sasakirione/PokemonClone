package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.object.value.Type;

public class PokemonMove {
    private final String moveName;
    private final int moveClass;
    private final int[] real;
    private int moveDamage;
    private String moveType;
    private boolean typeMatched;
    private Type types;

    public PokemonMove(String name, int[] real , Type type) {
        this.moveName = name;
        this.moveClass = 1;
        this.real = real;
        this.types = type;
        if (name.equals("サンダープリズン")) {
            this.moveDamage = 80;
            this.moveDamage *= 1.5;
            this.moveType = "でんき";
        }
        if (name.equals("ぼうふう")) {
            this.moveDamage = 110;
            this.moveType = "ひこう";
        }
        if (name.equals("ハイドロポンプ")) {
            this.moveDamage = 110;
            this.moveType = "みず";
        }
        if (name.equals("げんしのちから")) {
            this.moveDamage = 60;
            this.moveType = "いわ";
        }

    }

    public String getMoveName() {
        return moveName;
    }

    public int getMoveClass() {
        return moveClass;
    }

    public int getMoveDamage() {
        return moveDamage;
    }

    public int getRealAttack() {
        if (moveClass == 0) {
            return real[1];
        } else {
            return real[3];
        }
    }

    public double getMagnification() {
        if (types.isTypeMatch(moveType)) {
            return (6144.0/4096.0);
        }
        return (1.0);
    }

    public String getMoveType() {
        return this.moveType;
    }
}