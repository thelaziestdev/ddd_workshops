package com.example;

import com.example.order.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MoneyTest {

    @Test
    void canAddToMoney() {
        // given
        var money = new Money(BigDecimal.TEN, "USD");

        // when
        var result = money.add(new Money(BigDecimal.valueOf(20), "USD"));

        // then
        Assertions.assertEquals(new Money(BigDecimal.valueOf(30), "USD"), result);
    }

    @Test
    void canSubtractFromMoney() {
        // given
        var money = new Money(BigDecimal.valueOf(20), "USD");

        // when
        var result = money.subtract(new Money(BigDecimal.valueOf(10), "USD"));

        // then
        Assertions.assertEquals(new Money(BigDecimal.valueOf(10), "USD"), result);
    }

}
