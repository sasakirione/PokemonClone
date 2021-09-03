package com.sasakirione.main.pokemon.clone.data;

import com.sasakirione.main.pokemon.clone.object.Pokemon;

import java.io.*;
import java.util.List;

public class PokemonDataGet {
    private static final File filePokemon = new File("C:\\Users\\Yuki Yamada\\IdeaProjects\\PokemonClone\\data\\pokemon_status.csv");
    
    private PokemonDataGet() {
        throw new AssertionError("これはインスタンス化しないで！");
    }

    public static String getNameByID(int i) throws FileNotFoundException, UnsupportedEncodingException {
        String[] rowList = pokemonFileGet(i);
        return rowList[1];
    }

    private static String[] pokemonFileGet(int i) {
        String[] res = new String[0];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePokemon), "windows-31j"))) {
            while(true) {
                String row = reader.readLine();
                if (row == null) {
                    throw new AssertionError("ポケモンが見つかりません！");
                }
                String[] rowList = row.split(",");
                if (rowList[0].equals(String.valueOf(i))) {
                    res = rowList;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Pokemon getObjectByID(int dexNo, int[] effort, int i, String good, String nature) {
        String[] pokemon = pokemonFileGet(dexNo);
        int[] base = new int[] {
                Integer.parseInt(pokemon[7]),
                Integer.parseInt(pokemon[8]),
                Integer.parseInt(pokemon[9]),
                Integer.parseInt(pokemon[10]),
                Integer.parseInt(pokemon[11]),
                Integer.parseInt(pokemon[12]),
        };
        String type1 = pokemon[2];
        String type2 = pokemon[3];
        String ability = pokemon[i+3];
        return new Pokemon(pokemon[1], effort, base, good, nature, type1, type2, ability);
    }
}
