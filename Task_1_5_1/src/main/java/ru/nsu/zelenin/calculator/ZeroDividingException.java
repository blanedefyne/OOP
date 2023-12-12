package ru.nsu.zelenin.calculator;

public class ZeroDividingException extends Exception{
    public ZeroDividingException(String message) {
        super(message);
    }
}
