package com.sasakirione.main.pokemon.clone.loggin;

import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;

import java.util.ArrayList;

public class BattleLog {
    private static ArrayList<String> battleLog = new ArrayList<>();

    public static void start() {
        battleLog = new ArrayList<>();
    }

    public static void startBattle(String tn, Pokemon pokemon) {
        battleLog.add(tn + "は " + pokemon.getName() + " を繰り出した！");
    }

    public static void attack(Pokemon pokemon, PokemonMove move) {
        battleLog.add(pokemon.getName()+" の " + move.getMoveName()+" のこうげきだ！");
    }

    public static void death(Pokemon pokemon) {
        battleLog.add(pokemon.getName() + " はたおれた");
    }

    public static void typeMagnification(double typeMagnification) {
        if (typeMagnification == 0.0) {
            battleLog.add("こうかがないようだ");
        }
        if (typeMagnification < 1.0) {
            battleLog.add("こうかいまひとつだ！");
        }
        if (1.0 < typeMagnification) {
            battleLog.add("こうかばつぐんだ！");
        }
    }

    public static void hp(Pokemon pokemon) {
        battleLog.add(pokemon.getCurrentHP2());
    }

    public static void getLogAll() {
        battleLog.forEach(System.out::println);
    }

    public static String getLog(int i) {
        return battleLog.get(i);
    }

    public static void vitals() {
        battleLog.add("きゅうしょにあたった！");
    }

    public static void statusAilmentError() {
        battleLog.add("すでに状態異常にかかっています");
    }

    public static void parError() {
        battleLog.add("効果がないようだ");
    }

    public static void par(String name) {
        battleLog.add(name+" は麻痺して技が出にくくなった！");
    }

    public static void rankUp(String name, int item, int i) {
        if (item == 1 && i == 12) {
            battleLog.add(name + " は体力を 削って パワーぜんかいになった");
            return;
        }
        String message2 = switch (item) {
            case 1 -> "攻撃";
            case 2 -> "防御";
            case 3 -> "特攻";
            case 4 -> "特防";
            case 5 -> "素早さ";
            default -> "error";
        };
        String message3 = switch (i) {
            case 3,4,5,6 -> "が　ぐぐーんとあがった";
            case 2 -> "が ぐーんとあがった";
            case 1 -> "が あがった";
            case -1 -> "が さがった";
            case -2 -> "が がくっとさがった";
            case -3,-4,-5,-6 -> "が がくーんとさがった";
            default -> "error";
        };
        battleLog.add(name + "の "+ message2 + message3 + "!");
    }
}
