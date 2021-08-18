package com.sasakirione.main.pokemon.clone.object.value;

public class HP {
    private int maxHP;
    private int currentHP;

    public HP (int hp) {
        this.maxHP = hp;
        this.currentHP = hp;
    }

    public int pruneHP(int damage) {
        int hp = currentHP - damage;
        if (hp < 0) {
            this.currentHP = 0;
            return 0;
        } else {
            this.currentHP = hp;
            return hp;
        }
    }

    public boolean isDeath() {
        if (this.currentHP == 0) {
            return true;
        } else {
            return false;
        }
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
}
