package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.object.value.Status;
import com.sasakirione.main.pokemon.clone.object.value.Type;

public class PokemonMove {
    // 技の名前
    private final String moveName;
    // 0:物理技、1:特殊技、2：自分にかかる変化技、3:相手にかかる変化技、4：場にかかる変化技
    private int moveClass;
    // 技を出す側の実数値
    private final int[] real;
    // 技のダメージ
    private int moveDamage;
    // 技のタイプ
    private String moveType;
    // 技を出す側のタイプ
    private final Type types;

    public PokemonMove(String name, Status status , Type type) {
        this.moveName = name;
        this.real = new int[]{status.getA(), status.getC()};
        this.types = type;

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
            return real[0];
        } else {
            return real[1];
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