package com.example.order;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {

    public static final Money ZERO(String currency) {
        return new Money(BigDecimal.ZERO, currency);
    }


    public Money add(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract different currencies");
        }
        return new Money(amount.subtract(other.amount), currency);
    }

    public boolean isGreaterThan(Money amountDue) {
        if (!currency.equals(amountDue.currency)) {
            throw new IllegalArgumentException("Cannot subtract different currencies");
        }
        return amount.compareTo(amountDue.amount) > 0;
    }
}
