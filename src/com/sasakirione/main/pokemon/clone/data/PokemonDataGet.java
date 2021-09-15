package com.sasakirione.main.pokemon.clone.data;

import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;
import com.sasakirione.main.pokemon.clone.object.value.*;

import java.io.*;

public class PokemonDataGet implements PokemonDataGetInterface {
    private static final File filePokemon = new File("C:\\Users\\Yuki Yamada\\IdeaProjects\\PokemonClone\\data\\pokemon_status.csv");
    private static final File fileMove = new File("C:\\Users\\Yuki Yamada\\IdeaProjects\\PokemonClone\\data\\moves.csv");

    private String[] pokemonFileGet(int i) {
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

    @Override
    public Pokemon getObjectByID(int dexNo, int[] effort, int i, String good, String nature) {
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

    private String[] pokemonMoveFileGet(String name) {
        String[] res = new String[0];
        String name2 = "\"" + name + "\"";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileMove))) {
            while(true) {
                String row = reader.readLine();
                if (row == null) {
                    throw new AssertionError("技が見つかりません！");
                }
                String[] rowList = row.split(",");
                if (rowList[3].equals(name2)) {
                    res = rowList;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public PokemonMove getMoveByName(String name, Type type, Status status, Ability ability, Good good) {
        String[] move = pokemonMoveFileGet(name);
        int damage = Integer.parseInt(move[7]);
        MoveClass moveClass = getMoveClass(move[11],move[12]);
        int priority = Integer.parseInt(move[10]);
        String moveType = getMoveType(move[6]);
        int accuracy = Integer.parseInt(move[9]);
        return new PokemonMove(name, status, type, good, moveClass, damage, moveType, priority, ability, accuracy);
    }

    private String getMoveType(String type) {
        int typeID = Integer.parseInt(type);
        return switch (typeID) {
            case 1 -> "ノーマル";
            case 2 -> "かくとう";
            case 3 -> "ひこう";
            case 4 -> "どく";
            case 5 -> "じめん";
            case 6 -> "いわ";
            case 7 -> "むし";
            case 8 -> "ゴースト";
            case 9 -> "はがね";
            case 10 -> "ほのお";
            case 11 -> "みず";
            case 12 -> "くさ";
            case 13 -> "でんき";
            case 14 -> "エスパー";
            case 15 -> "こおり";
            case 16 -> "ドラゴン";
            case 17 -> "あく";
            default -> throw new IllegalArgumentException("技データベースの不正値です");
        };
    }

    private MoveClass getMoveClass(String s, String s1) {
        if (s1.equals("2")) {
            return MoveClass.PHYSICS;
        }
        if (s1.equals("3")) {
            return MoveClass.SPECIAL;
        }
        throw new IllegalArgumentException("現在対応してない技です");
    }
}
