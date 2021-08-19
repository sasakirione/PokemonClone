package com.sasakirione.main.pokemon.clone.object;

public class PokemonStadium {
    private Pokemon pokemonInBattleA;
    private Pokemon pokemonInBattleB;

    public PokemonStadium(Pokemon pokemonA, Pokemon pokemonB) {
        this.pokemonInBattleA = pokemonA;
        this.pokemonInBattleB = pokemonB;
    }

    public String forwardTurn(PokemonMove a, PokemonMove b) {
        if (this.pokemonInBattleA.getCurrentHP() == 0 || this.pokemonInBattleB.getCurrentHP() == 0) {
            return "おわりだよ";
        }
        if (rapidityDecision() == 1) {
            this.pokemonInBattleA.takeDamage(b);
            if (pokemonInBattleA.getCurrentHP() == 0) {
                return "Aの負け";
            }
            this.pokemonInBattleB.takeDamage(a);
            if (pokemonInBattleB.getCurrentHP() == 0) {
                return "Bの負け";
            }
        } else {
            this.pokemonInBattleB.takeDamage(a);
            if (pokemonInBattleB.getCurrentHP() == 0) {
                return "Bの負け";
            }
            this.pokemonInBattleA.takeDamage(b);
            if (pokemonInBattleA.getCurrentHP() == 0) {
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
}
