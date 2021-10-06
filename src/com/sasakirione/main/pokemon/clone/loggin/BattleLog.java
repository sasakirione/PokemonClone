package com.sasakirione.main.pokemon.clone.loggin;

import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;
import com.sasakirione.main.pokemon.clone.object.value.Ability;
import com.sasakirione.main.pokemon.clone.object.value.Field;
import com.sasakirione.main.pokemon.clone.object.value.Status;

import java.util.ArrayList;

public class BattleLog {
    private static ArrayList<String> battleLog = new ArrayList<>();

    private BattleLog() { throw new AssertionError("これはインスタンス化しないで！"); }

    public static void start() {
        battleLog = new ArrayList<>();
    }

    public static int count() { return battleLog.size();}

    public static void startBattle(String tn, Pokemon pokemon) {
        add(tn + "は " + pokemon.getName() + " を繰り出した！");
    }

    public static void attack(Pokemon pokemon, PokemonMove move) {
        add(pokemon.getName()+" の " + move.getMoveName()+" のこうげきだ！");
    }

    public static void death(Pokemon pokemon) {
        add(pokemon.getName() + " はたおれた");
    }

    public static void typeMagnification(double typeMagnification) {
        if (typeMagnification == 0.0) {
            add("こうかがないようだ");
        }
        if (typeMagnification < 1.0) {
            add("こうかいまひとつだ！");
        }
        if (1.0 < typeMagnification) {
            add("こうかばつぐんだ！");
        }
    }

    public static void hp(Pokemon pokemon) {
        add(pokemon.getCurrentHP2());
    }

    public static void hp(Status status) { add(status.getCurrentHP2()); }

    public static void getLogAll() {
        battleLog.forEach(System.out::println);
    }

    public static String getLog(int i) {
        return battleLog.get(i);
    }

    public static void vitals() {
        add("急所に 当たった！");
    }

    public static void statusAilmentError() {
        add("すでに状態異常にかかっています");
    }

    public static void parError() {
        add("効果がないようだ");
    }

    public static void par(String name) {
        add(name+" は麻痺して技が出にくくなった！");
    }

    public static void rankUp(String name, int item, int i) {
        if (item == 1 && i == 12) {
            add(name + " は体力を 削って パワーぜんかいになった");
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
        add(name + "の "+ message2 + message3 + "!");
    }

    public static void psychofieldPriority(String name) {
        add(name + "は サイコフィールドに 守られている！");
    }

    public static void expandPsychoMaker(String name) {
        add(name + "の サイコメイカー");
        add("足下が 不思議な 感じに なった！");
    }

    public static void endField(Field field) {
        if (field.isPsychofield()) {
            add("足下の 不思議感が 消え去った！");
        }
    }

    private static void add(String message) {
        battleLog.add(message);
    }

    public static void changeType(String name, String moveType) {
        add(name + "は " + moveType +"タイプに なった！");
    }

    public static void ability(String name, Ability ability) {
        add(name + "の " + ability);
    }

    public static void moveMiss() {
        add("しかし うまく 決まらなかった!!");
    }

    public static void change(String side, String name, String name1) {
        add(side + "は " + name + "を 引っこめた！");
        add(side + "は " + name1 + " をくりだした！");
    }

    public static void tama(String pokemonName) {
        add(pokemonName + "は 命が 少し削られた！");
    }

    public static void LeftOvers(String pokemonName) {
        add(pokemonName + "は たべのこしで 少し 回復");
    }

    public static void bakekawa(String name) {
        add(name + "の ばけのかわ");
        add("ばけのかわが みがわりに なった！");
        add(name + "の ばけのかわが はがれた！");
    }

    public static void whiteHerb(String name) {
        add(name + "は しろいハーブで ステータスを 元に戻した！");
    }

    public static void combAttack(int count) {
        add(count +"回 当たった！");
    }

    public static int moveCount(String name) {
        return Math.toIntExact(battleLog.stream().filter(x -> x.endsWith(name + " のこうげきだ！")).count());
    }

    public static void recoil(String name) {
        add("攻撃の 反動で " + name+ "は 動けない！");
    }
}
