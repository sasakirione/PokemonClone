package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;
import com.sasakirione.main.pokemon.clone.object.value.*;

public class Pokemon {
    private String name;
    private Status status;
    private Type type;
    private boolean goodChoice = false;
    private String choiceMove = null;
    private String statusAilment = "";

    public Pokemon(String name, int[] effort, String good, String nature) {
        setPokemon(name, effort, new Nature(nature), good);
    }

    public Pokemon(String name, int[] effort, int[] base, String good, String nature, String type1, String type2, String ability) {
        this.name = name;
        this.type = new Type(type1, type2);
        this.status = new Status(base, new Effort(effort), good, new Nature(nature));
    }

    public String getName() {
        return this.name;
    }

    public PokemonMove getDamage(String name) {
        if (goodChoice) {
            choiceCheck(name);
        }
        return new PokemonMove(name, this.status, this.type);
    }

    private void choiceCheck(String name) {
        if (choiceMove != null && !name.equals(this.choiceMove)) {
            throw new IllegalArgumentException("こだわっています！");
        }
        if (choiceMove == null) {
            this.choiceMove = name;
        }
    }

    private void setPokemon(String name, int[] effortInt, Nature nature, String good){
        this.name = name;
        int[] base = null;
        Effort effort = null;
        if (name.equals("サンダー")) {
            base = new int[] {90, 90, 85, 125, 90, 100};
            effort = new Effort(effortInt);
            this.type = new Type("でんき","ひこう");
        }
        if (name.equals("ゲッコウガ")) {
            base = new int[] {72, 95, 67, 103, 71, 122};
            effort = new Effort(effortInt);
            this.type = new Type("みず","あく");
        }
        if (name.equals("レジエレキ")) {
            base = new int[] {80, 100, 50, 100, 50, 200};
            effort = new Effort(effortInt);
            this.type = new Type("でんき");
        }
        if (name.equals("ポットデス")) {
            base = new int[] {60, 65, 65, 134, 114, 70};
            effort = new Effort(effortInt);
            this.type = new Type("ゴースト");
        }
        this.status = new Status(base, effort, good, nature);
    }

    public void takeDamage(PokemonMove a) {
        if (a.getMoveClass() == 3) {
            takeChange(a);
            return;
        }
        if (a.getMoveName().equals("ちきゅうなげ")) {
            this.status.constantDamage(50);
            return;
        }
        int damage = a.getMoveDamage();
        int realAttack = a.getRealAttack();
        int defenseChoice;
        String type = a.getMoveType();
        double typeMagnification = this.type.getTypeMagnification(type);
        double magnification = a.getMagnification() * typeMagnification;

        if (a.getMoveClass() == 0) {
            defenseChoice = 2;
        } else {
            defenseChoice = 4;
        }
        this.status.damageCalculation(realAttack, defenseChoice, damage, magnification, type);
        BattleLog.typeMagnification(typeMagnification);
    }

    public void takeChange(PokemonMove a) {
        if (a.getMoveName().equals("からをやぶる")) {
            status.rankUp(1,2);
            status.rankUp(3,2);
            status.rankUp(5,2);
        }
        if (a.getMoveName().equals("かいでんぱ")) {
            status.rankUp(3,-2);
        }
        if (a.getMoveName().equals("でんじは")) {
            getPAR();
        }
    }

    private void getPAR() {
        if (!statusAilment.equals("")) {
            BattleLog.statusAilmentError();
            return;
        }
        if (this.type.isPARCheck()) {
            BattleLog.parError();
            return;
        }
        statusAilment = "まひ";
        status.getPAR();
        BattleLog.par(this.name);
    }

    public int getCurrentHP() {
        return status.getCurrentHP();
    }

    public String getCurrentHP2() {
        return status.getCurrentHP2();
    }

    public Status getStatus() {
        return this.status;
    }
}
