package com.sasakirione.main.pokemon.clone.data;

import com.sasakirione.main.pokemon.clone.exception.UnsupportedMoveException;
import com.sasakirione.main.pokemon.clone.object.Pokemon;
import com.sasakirione.main.pokemon.clone.object.PokemonMove;
import com.sasakirione.main.pokemon.clone.object.code.MoveClass;

import java.io.*;
import java.util.Properties;

public class PokemonDataGet implements PokemonDataGetInterface {
    private final Properties properties;

    public PokemonDataGet() {
        properties = new Properties();
        try (Reader reader = new FileReader("path.properties")) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] pokemonFileGet(int i, int form) {
        String[] res = new String[0];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(properties.getProperty("pokemon")), "windows-31j"))) {
            res = pokemonFileGetSerch(i, form,  reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String[] pokemonFileGetSerch(int i, int form, BufferedReader reader) throws IOException {
        String[] res;
        String name;
        if (form == 0) {
            name = String.valueOf(i);
        } else {
            name = i + "-" + form;
        }
        while(true) {
            String row = reader.readLine();
            if (row == null) {
                throw new AssertionError("ポケモンが見つかりません！");
            }
            String[] rowList = row.split(",");
            if (rowList[0].equals(name)) {
                res = rowList;
                break;
            }
        }
        return res;
    }

    @Override
    public Pokemon getObjectByID(int dexNo, int form, int[] effort, int i, String good, String nature) {
        String[] pokemon = pokemonFileGet(dexNo, form);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(properties.getProperty("move")))) {
            res = pokemonMoveFileGetSerch(name, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String[] pokemonMoveFileGetSerch(String name2, BufferedReader reader) throws IOException {
        String[] res;
        while(true) {
            String row = reader.readLine();
            if (row == null) {
                throw new UnsupportedMoveException("技が見つかりません！");
            }
            String[] rowList = row.split(",");
            if (rowList[1].equals(name2)) {
                res = rowList;
                break;
            }
        }
        return res;
    }

    @Override
    public PokemonMove getMoveByName(String name, Pokemon pokemon) {
        String[] move = pokemonMoveFileGet(name);
        int damage = Integer.parseInt(move[3]);
        MoveClass moveClass = getMoveClass(move[7],move[8]);
        int priority = Integer.parseInt(move[6]);
        String moveType = getMoveType(move[2]);
        int accuracy = Integer.parseInt(move[5]);
        int vitalRank;
        int comb;
        try {
            vitalRank = Integer.parseInt(move[9]);
        } catch (ArrayIndexOutOfBoundsException e) {
            vitalRank = 0;
        }
        try {
            comb = Integer.parseInt(move[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            comb = 0;
        }
        return new PokemonMove(name, pokemon, moveClass, damage, moveType, priority, accuracy, vitalRank, comb);
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
            default -> throw new UnsupportedMoveException("技データベースの不正値です");
        };
    }

    private MoveClass getMoveClass(String s, String s1) {
        if (s1.equals("2")) {
            return MoveClass.PHYSICS;
        }
        if (s1.equals("3")) {
            return MoveClass.SPECIAL;
        }
        throw new UnsupportedMoveException("現在対応してない技です");
    }
}
