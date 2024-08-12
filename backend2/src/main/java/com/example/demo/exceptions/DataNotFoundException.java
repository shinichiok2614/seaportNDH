package com.example.demo.exceptions;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(String messenger) {
        super(messenger);
    }
}
