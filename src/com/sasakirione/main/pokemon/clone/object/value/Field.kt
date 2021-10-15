package com.sasakirione.main.pokemon.clone.object.value;

import java.util.Arrays;

public class Field {
    private static final String[] fieldList = {"エレキフィールド", "グラスフィールド", "サイコフィールド", "ミストフィールド"};
    private final String field;
    private int remainingTurn;

    public Field(String fieldName) {
        fieldCheck(fieldName);
        this.field = fieldName;
        remainingTurn = 5;
    }

    private void fieldCheck(String fieldName) {
        if (!Arrays.asList(fieldList).contains(fieldName)) {
            throw new IllegalArgumentException("存在しないフィールドです");
        }
    }

    public String getField() {
        return field;
    }

    public int getRemainingTurn() {
        return remainingTurn;
    }

    public void forwardTurn() {
        remainingTurn = remainingTurn - 1;
    }

    public boolean isDisablingPriority() {
        return this.field.equals("サイコフィールド");
    }

    public boolean isEndField() {
        return remainingTurn == 0;
    }

    public boolean isPsychofield() {
        return this.field.equals("サイコフィールド");
    }

}
