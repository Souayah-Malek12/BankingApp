package com.BankingAppSpringBoot.BankingApp.exception;


// 403 Forbidden

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

}
