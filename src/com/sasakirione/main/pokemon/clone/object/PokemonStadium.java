package com.sasakirione.main.pokemon.clone.object;

import com.sasakirione.main.pokemon.clone.loggin.BattleLog;

public class PokemonStadium {
    private final Pokemon pokemonInBattleA;
    private final Pokemon pokemonInBattleB;
    private boolean matchEndFlag = false;

    public PokemonStadium(Pokemon pokemonA, Pokemon pokemonB) {
        this.pokemonInBattleA = pokemonA;
        this.pokemonInBattleB = pokemonB;
        BattleLog.start();
        BattleLog.startBattle("A",pokemonA);
        BattleLog.startBattle("B",pokemonB);
    }

    public String forwardTurn(PokemonMove a, PokemonMove b) {
        if (this.pokemonInBattleA.getCurrentHP() == 0 || this.pokemonInBattleB.getCurrentHP() == 0) {
            return "おわりだよ";
        }
        if (rapidityDecision() == 1) {
            attackSideB(b);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideA(a);
        } else {
            attackSideA(a);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideB(b);
        }
        return "何もなし";
    }
    private void attackSideA (PokemonMove a) {
        BattleLog.attack(pokemonInBattleA, a);
        this.pokemonInBattleB.takeDamage(a);
        BattleLog.hp(pokemonInBattleB);
        if (pokemonInBattleB.getCurrentHP() == 0) {
            BattleLog.death(pokemonInBattleB);
            this.matchEndFlag = true;
        }
    }
    private void attackSideB(PokemonMove b) {
        BattleLog.attack(pokemonInBattleB, b);
        this.pokemonInBattleA.takeDamage(b);
        BattleLog.hp(pokemonInBattleA);
        if (pokemonInBattleA.getCurrentHP() == 0) {
            BattleLog.death(pokemonInBattleA);
            this.matchEndFlag = true;
        }
    }

    private int rapidityDecision() {
        if (pokemonInBattleA.getReal()[5] < pokemonInBattleB.getReal()[5]) {
            return 1;
        } else {
            return 0;
        }
    }

    private int priorityDecision() {
        return 0;
    }
}
