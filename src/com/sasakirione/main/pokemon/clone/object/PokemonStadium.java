package com.sasakirione.main.pokemon.clone.object;

import java.util.ArrayList;

public class PokemonStadium {
    private final Pokemon pokemonInBattleA;
    private final Pokemon pokemonInBattleB;
    private ArrayList<String> battleLog = new ArrayList<>();
    private boolean matchEndFlag = false;

    public PokemonStadium(Pokemon pokemonA, Pokemon pokemonB) {
        this.pokemonInBattleA = pokemonA;
        this.pokemonInBattleB = pokemonB;
        battleLog.add("Aは " + pokemonA.getName() + " をくりだした！");
        battleLog.add("Bは " + pokemonB.getName() + " をくりだした！");
    }

    public String forwardTurn(PokemonMove a, PokemonMove b) {
        if (this.pokemonInBattleA.getCurrentHP() == 0 || this.pokemonInBattleB.getCurrentHP() == 0) {
            return "おわりだよ";
        }
        if (rapidityDecision() == 1) {
            attckSideB(b);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attackSideA(a);
        } else {
            attackSideA(a);
            if (matchEndFlag) {
                return "おわりだよ";
            }
            attckSideB(b);
        }
        return "何もなし";
    }
    private void attackSideA (PokemonMove a) {
        String aMagnification = this.pokemonInBattleB.takeDamage(a);
        battleLog.add(pokemonInBattleA.getName()+" の " + a.getMoveName()+" のこうげきだ！");
        battleLog.add(pokemonInBattleB.getCurrentHP2());
        battleLog.add(aMagnification);
        if (pokemonInBattleB.getCurrentHP() == 0) {
            battleLog.add(pokemonInBattleB.getName() + " はたおれた");
            this.matchEndFlag = true;
        }
    }

    private void attckSideB (PokemonMove b) {
        String bMagnification = this.pokemonInBattleA.takeDamage(b);
        battleLog.add(pokemonInBattleB.getName()+" の " + b.getMoveName()+" のこうげきだ！");
        battleLog.add(pokemonInBattleA.getCurrentHP2());
        battleLog.add(bMagnification);
        if (pokemonInBattleA.getCurrentHP() == 0) {
            battleLog.add(pokemonInBattleA.getName() + " はたおれた");
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

    public String getLog(int i) {
        return battleLog.get(i);
    }

    public void getLogAll() {
        this.battleLog.forEach(System.out::println);
    }
}
