package com.sasakirione.main.pokemon.clone.object.value;

import com.sasakirione.main.pokemon.clone.constant.CalculationConst;
import com.sasakirione.main.pokemon.clone.loggin.BattleLog;

public class HP {
    private final int maxHP;
    private int currentHP;

    public HP (int hp) {
        this.maxHP = hp;
        this.currentHP = hp;
    }

    public void pruneHP(int damage) {
        int hp = currentHP - damage;
        this.currentHP = Math.max(hp, 0);
    }

    public boolean isDeath() {
        return this.currentHP == 0;
    }

    public String toString() {
        int half = maxHP / 2;
        int quarter = maxHP / 4;
        String now = "緑";
        if (currentHP <= half) {
            now = "黄色";
            if (currentHP <= quarter) {
                now = "赤色";
            }
        }
        return this.currentHP+"/"+this.maxHP + " " + now;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public boolean isOneThird() {
        if(currentHP == 0) {
            return false;
        }
        return (3 < (maxHP / currentHP));
    }

    public void damageOneEighth() {
        int oneEighth = (int) Math.floor(maxHP * CalculationConst.ONE_EIGHTH);
        pruneHP(oneEighth);
    }

    public void recoveryOnePointSixteen() {
        int calculationHP = (int) Math.floor(maxHP * CalculationConst.ONE_POINT_SIXTEEN + currentHP);
        this.currentHP = Math.min(calculationHP , maxHP);
    }
}
