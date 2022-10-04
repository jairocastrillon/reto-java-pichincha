package co.com.bancopichincha.retojava.domain.enums;

import co.com.bancopichincha.retojava.exception.NotFoundException;

import java.util.Arrays;

public enum AccountType {
    AHORROS, CORRIENTE;

    public static AccountType findAccount(String type) {
        return Arrays.stream(values()).filter(v -> v.toString().equals(type)).findFirst()
                .orElseThrow(() -> new NotFoundException("Account's type not valid"));
    }
}
