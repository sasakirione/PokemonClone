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

}
