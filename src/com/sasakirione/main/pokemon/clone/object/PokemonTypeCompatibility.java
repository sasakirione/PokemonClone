package com.sasakirione.main.pokemon.clone.object;

public class PokemonTypeCompatibility {
    public static double typeCompatibility(String attack, String defense) {
        double magnification = 1.0;

        if (attack.equals("ノーマル")) {
            if (defense.equals("いわ") || defense.equals("はがね")){
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("ゴースト")){
                magnification = 0.0;
                return magnification;
            }
            return magnification;
        }

        if (attack.equals("ほのお")) {
            if (defense.equals("くさ") || defense.equals("こおり") || defense.equals("むし") || defense.equals("はがね")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("ほのお") || defense.equals("みず") || defense.equals("いわ") || defense.equals("ドラゴン")) {
                magnification = 0.5;
                return magnification;
            }
            return magnification;
        }

        if (attack.equals("みず")) {
            if (defense.equals("ほのお") || defense.equals("じめん") || defense.equals("いわ")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("みず") || defense.equals("くさ") || defense.equals("ドラゴン")) {
                magnification = 0.5;
                return magnification;
            }
            return magnification;
        }

        if (attack.equals("でんき")) {
            if (defense.equals("みず") || defense.equals("ひこう")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("でんき") || defense.equals("くさ") || defense.equals("ドラゴン")) {
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("じめん")) {
                magnification = 0.0;
                return magnification;
            }
            return magnification;
        }

        if (attack.equals("くさ")) {
            if (defense.equals("みず") || defense.equals("じめん") || defense.equals("いわ")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("ほのお") || defense.equals("くさ") || defense.equals("どく") || defense.equals("ひこう")
                    || defense.equals("むし") || defense.equals("ドラゴン")  || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
            return magnification;
        }

        if (attack.equals("こおり")) {
            if (defense.equals("くさ") || defense.equals("じめん") || defense.equals("ひこう") || defense.equals("ドラゴン")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("ほのお") || defense.equals("みず") || defense.equals("こおり") || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
            return magnification;
        }

        if (attack.equals("かくとう")) {
            if (defense.equals("ノーマル") || defense.equals("こおり") || defense.equals("いわ") || defense.equals("あく")
                    || defense.equals("はがね")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("どく") || defense.equals("ひこう") || defense.equals("エスパー") || defense.equals("むし")
                    || defense.equals("フェアリー")) {
                magnification = 0.5;
                return magnification;
            }
        }

        return magnification;
    }

}
