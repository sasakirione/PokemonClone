package com.sasakirione.main.pokemon.clone.object;

import java.util.ArrayList;

public class PokemonStadium {
    private Pokemon pokemonInBattleA;
    private Pokemon pokemonInBattleB;
    private ArrayList<String> battleLog = new ArrayList<>();

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
            String bMagnification = this.pokemonInBattleA.takeDamage(b);
            battleLog.add(pokemonInBattleB.getName()+" の " + b.getMoveName()+" のこうげきだ！");
            battleLog.add(bMagnification);
            if (pokemonInBattleA.getCurrentHP() == 0) {
                battleLog.add(pokemonInBattleA.getName() + " はたおれた");
                return "Aの負け";
            }
            String aMagnification = this.pokemonInBattleB.takeDamage(a);
            battleLog.add(pokemonInBattleA.getName()+" の " + a.getMoveName()+" のこうげきだ！");
            battleLog.add(aMagnification);
            if (pokemonInBattleB.getCurrentHP() == 0) {
                battleLog.add(pokemonInBattleA.getName() + " はたおれた");
                return "Bの負け";
            }
        } else {
            String aMagnification = this.pokemonInBattleB.takeDamage(a);
            battleLog.add(pokemonInBattleA.getName()+" の " + a.getMoveName()+" のこうげきだ！");
            battleLog.add(aMagnification);
            if (pokemonInBattleB.getCurrentHP() == 0) {
                battleLog.add(pokemonInBattleB.getName() + " はたおれた");
                return "Bの負け";
            }
            String bMagnification = this.pokemonInBattleA.takeDamage(b);
            battleLog.add(pokemonInBattleB.getName()+" の " + b.getMoveName()+" のこうげきだ！");
            battleLog.add(bMagnification);
            if (pokemonInBattleA.getCurrentHP() == 0) {
                battleLog.add(pokemonInBattleA.getName() + " はたおれた");
                return "Aの負け";
            }
        }
        return "何もなし";
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
        this.battleLog.stream().forEach(System.out::println);
    }
}
