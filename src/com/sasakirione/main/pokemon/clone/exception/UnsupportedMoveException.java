package com.sasakirione.main.pokemon.clone.exception;

public class UnsupportedMoveException extends RuntimeException {
    public UnsupportedMoveException() {}
    public UnsupportedMoveException(String message) {
        super(message);
    }
}
