package com.sasakirione.main.pokemon.clone.object;

public class PokemonMove {
    private final String moveName;
    private final int moveClass;
    private final int[] real;
    private int moveDamage;
    private String moveType;
    private boolean typeMatched;

    public PokemonMove(String name, int[] real) {
        this.moveName = name;
        this.moveClass = 1;
        this.real = real;
        if (name.equals("サンダープリズン")) {
            this.moveDamage = 80;
            this.moveDamage *= 1.5;
            this.moveType = "でんき";
        }
        if (name.equals("ぼうふう")) {
            this.moveDamage = 110;
            this.moveType = "ひこう";
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
        return (6144.0/4096.0);
    }

    public String getMoveType() {
        return this.moveType;
    }
}