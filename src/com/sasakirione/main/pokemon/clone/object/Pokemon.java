package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.object.value.Effort;
import com.sasakirione.main.pokemon.clone.object.value.HP;
import com.sasakirione.main.pokemon.clone.object.value.Nature;
import com.sasakirione.main.pokemon.clone.object.value.Type;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Pokemon {
    private String name;
    private int[] real;
    private Effort effort;
    private int[] base;
    private HP currentHP;
    private int enhancement;
    private int weakening;
    private Type type;
    private Nature nature;
    private String good;
    private boolean goodChoice = false;
    private String choiceMove = null;

    public Pokemon(String name, int[] effort, String good, String nature) {
        this.nature = new Nature(nature);
        setPokemon(name, effort);
        setGood(good);
    }

    private void setGood(String good) {
        if (good.equals("なし")) {
            return;
        }
        if (good.equals("こだわりメガネ")) {
            this.good = "こだわりメガネ";
            this.goodChoice = true;
            this.real[3] = (int) Math.round(this.real[3] * 1.5);
        }
    }

    public String getName() {
        return this.name;
    }

    private int realCalculationEtc(int base,int effort){
        return (int) Math.floor((((base * 2.0) + 31 + Math.floor(effort / 4.0)) * (50.0 / 100.0) + 5));
    }

    public PokemonMove getDamage(String name) {
        if (goodChoice) {
            if (choiceMove != null && !name.equals(this.choiceMove)) {
                throw new IllegalArgumentException("こだわっています！");
            }
            if (choiceMove == null) {
                this.choiceMove = name;
            }
        }
        return new PokemonMove(name, this.real);
    }

    public int[] getReal() {
        return real;
    }

    private void setPokemon(String name, int[] effort){
        this.name = name;
        if (name.equals("サンダー")) {
            this.base = new int[] {90, 90, 85, 125, 90, 100};
            this.effort = new Effort(effort);
            this.type = new Type("でんき","ひこう");
            pokemonRealSet();
        }

        if (name.equals("ゲッコウガ")) {
            this.base = new int[] {72, 95, 67, 103, 71, 122};
            this.effort = new Effort(effort);
            this.type = new Type("みず","あく");
            pokemonRealSet();
        }

        if (name.equals("レジエレキ")) {
            this.base = new int[] {80, 100, 50, 100, 50, 200};
            this.effort = new Effort(effort);
            this.type = new Type("でんき");
            pokemonRealSet();
        }

    }

    private void pokemonRealSet() {
        int hp = realCalculationHP(this.base[0],this.effort.getH());
        int a = realCalculationEtc(this.base[1],this.effort.getA());
        int b = realCalculationEtc(this.base[2],this.effort.getB());
        int c = realCalculationEtc(this.base[3],this.effort.getC());
        int d = realCalculationEtc(this.base[4],this.effort.getD());
        int s = realCalculationEtc(this.base[5],this.effort.getS());

        this.real = new int[] {hp, a, b, c, d, s};
        pokemonNutureCalculation();
        this.currentHP = new HP(this.real[0]);
    }

    private void pokemonNutureCalculation() {
        int plus = this.nature.plusNature();
        int minus = this.nature.minusNature();
        real[minus] = (int) Math.floor(real[minus] * 0.9);
        real[plus] = (int) Math.floor(real[plus] * 1.1);
    }

    private int realCalculationHP(int base, int effort) {
        return (int) Math.floor((((base * 2.0) + 31 + Math.floor(effort / 4.0)) * (50.0 / 100.0) + 50 + 10));
    }

    public String takeDamage(PokemonMove a) {
        int damage = a.getMoveDamage();
        int realAttack = a.getRealAttack();
        int realDefense;
        String type = a.getMoveType();
        double typeMagnification = this.type.getTypeMagnification(type);
        double magnification = a.getMagnification() * typeMagnification;

        if (a.getMoveClass() == 0) {
            realDefense = real[2];
        } else {
            realDefense = real[4];
        }

        int damage2 = this.currentHP.pruneHP(damageCalculation(realAttack, realDefense, damage, magnification, type));
        if (typeMagnification == 0.0) {
            return "こうかがないようだ";
        }
        if (typeMagnification < 1.0) {
            return "こうかいまひとつだ！";
        }
        if (1.0 < typeMagnification) {
            return "こうかばつぐんだ！";
        }
        return "";
    }

    private int damageCalculation(double realAttack, double realDefense, int damage, double magnification, String type) {
        double a = Math.floor(50 * 0.4 + 2);
        double b = Math.floor(a * damage * realAttack / realDefense);
        double c = Math.floor((b / 50.0) + 2);
        int d ;
        if (isVitals()) {
            d = fiveOutOverFiveIn(c * (6144.0 / 4096.0));
        } else {
            d = (int) c;
        }
        return (int) Math.floor(d * randomNumber() * magnification);
    }

    public int getCurrentHP() {
        return currentHP.getCurrentHP();
    }

    public String getCurrentHP2() {
        return currentHP.toString();
    }

    private double randomNumber() {
        Random random = new Random();
        int randomNumberRaw = random.nextInt(16);
        return 0.85 + (0.01 * randomNumberRaw);
    }

    private boolean isVitals() {
        Random random = new Random();
        int randomNumber = random.nextInt(16);
        return randomNumber == 0;
    }

    private int fiveOutOverFiveIn(double i) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(i));
        BigDecimal resBD = bigDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return (int) resBD.doubleValue();
    }
}
