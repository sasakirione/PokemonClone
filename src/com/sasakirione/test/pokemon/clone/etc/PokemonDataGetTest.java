package com.sasakirione.test.pokemon.clone.etc;

import com.sasakirione.main.pokemon.clone.data.PokemonDataGetInterface;
import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;

import java.io.*;
import java.util.Properties;

public class PokemonDataGetTest implements PokemonDataGetInterface {
    private final Properties properties;

    public PokemonDataGetTest() {
        properties = new Properties();
        try (Reader reader = new FileReader("path.properties")) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] pokemonFileGet(int i) {
        String[] res = new String[0];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(properties.getProperty("test")), "windows-31j"))) {
            res = pokemonFileGetSerch(i, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String[] pokemonFileGetSerch(int i, BufferedReader reader) throws IOException {
        String[] res;
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
        return res;
    }

    @Override
    public Pokemon getObjectByID(int dexNo, int form, int[] effort, int i, String good, String nature) {
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

    @Override
    public PokemonMove getMoveByName(String name, Pokemon pokemon) {
        return null;
    }
}
