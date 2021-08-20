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

        if (attack.equals("どく")) {
            if (defense.equals("くさ") || defense.equals("フェアリー")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("どく") || defense.equals("じめん") || defense.equals("いわ") || defense.equals("ゴースト")) {
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("はがね")) {
                magnification = 0.0;
                return magnification;
            }
        }

        if (attack.equals("じめん")) {
            if (defense.equals("ほのお") || defense.equals("でんき") || defense.equals("どく") || defense.equals("いわ") ||
                    defense.equals("はがね")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("くさ") || defense.equals("むし")) {
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("ひこう")) {
                magnification = 0.0;
                return magnification;
            }
        }

        if (attack.equals("ひこう")) {
            if (defense.equals("くさ") || defense.equals("かくとう") || defense.equals("むし")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("でんき") || defense.equals("いわ") || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
        }

        if (attack.equals("エスパー")) {
            if (defense.equals("かくとう") || defense.equals("どく")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("エスパー") || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("あく")) {
                magnification = 0.0;
                return magnification;
            }
        }

        if (attack.equals("むし")) {
            if (defense.equals("くさ") || defense.equals("エスパー") || defense.equals("あく")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("ほのお") || defense.equals("かくとう") || defense.equals("どく") || defense.equals("ひこう")
                    || defense.equals("ゴースト") || defense.equals("はがね") || defense.equals("フェアリー")) {
                magnification = 0.5;
                return magnification;
            }
        }

        if (attack.equals("いわ")) {
            if (defense.equals("ほのお") || defense.equals("ひこう") || defense.equals("こおり") || defense.equals("むし")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("かくとう") || defense.equals("じめん") || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
        }

        if (attack.equals("ゴースト")) {
            if (defense.equals("エスパー") || defense.equals("ゴースト")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("あく")) {
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("ノーマル")) {
                magnification = 0.0;
                return magnification;
            }
        }

        if (attack.equals("ドラゴン")) {
            if (defense.equals("ドラゴン")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
            if (defense.equals("フェアリー")) {
                magnification = 0.0;
                return magnification;
            }
        }

        if (attack.equals("あく")) {
            if (defense.equals("エスパー") || defense.equals("ゴースト")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("かくとう") || defense.equals("あく") || defense.equals("フェアリー")) {
                magnification = 0.5;
                return magnification;
            }
        }

        if (attack.equals("はがね")) {
            if (defense.equals("こおり") || defense.equals("いわ") || defense.equals("フェアリー")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("ほのお") || defense.equals("みず") || defense.equals("でんき") || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
        }

        if (attack.equals("フェアリー")) {
            if (defense.equals("かくとう") || defense.equals("あく") || defense.equals("ドラゴン")) {
                magnification = 2.0;
                return magnification;
            }
            if (defense.equals("ほのお") || defense.equals("どく") || defense.equals("はがね")) {
                magnification = 0.5;
                return magnification;
            }
        }

        return magnification;
    }

}
