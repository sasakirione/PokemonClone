package com.sasakirione.main.pokemon.clone.data;

import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;
import com.sasakirione.main.pokemon.clone.object.value.Ability;
import com.sasakirione.main.pokemon.clone.object.value.Status;
import com.sasakirione.main.pokemon.clone.object.value.Type;

public interface PokemonDataGetInterface {
    Pokemon getObjectByID(int dexNo, int[] effort, int i, String good, String nature);
    PokemonMove getMoveByName(String name, Type type, Status status, Ability ability);
}
