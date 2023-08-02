package ru.skypro.homework.exception;

public class FindNoEntityException extends RuntimeException{
    public FindNoEntityException(String message) {
        super(message);
    }
}
