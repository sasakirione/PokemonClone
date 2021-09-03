package com.sasakirione.main.pokemon.clone.data;

import java.io.*;
import java.util.List;

public class PokemonDataGet {
    private PokemonDataGet() {
        throw new AssertionError("これはインスタンス化しないで！");
    }

    public static String getNameByID(int i) throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File("C:\\Users\\Yuki Yamada\\IdeaProjects\\PokemonClone\\data\\pokemon_status.csv");
        String res = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-31j"))) {
            while(true) {
                String row = reader.readLine();
                if (row == null) {
                    throw new AssertionError("ポケモンが見つかりません！");
                }
                String[] rowList = row.split(",");
                if (rowList[0].equals(String.valueOf(i))) {
                    res = rowList[1];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
