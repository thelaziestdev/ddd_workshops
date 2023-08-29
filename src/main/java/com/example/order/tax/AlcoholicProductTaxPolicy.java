package com.example.order.tax;

import com.example.Money;
import com.example.order.Order;

import java.math.BigDecimal;

public class AlcoholicProductTaxPolicy implements TaxPolicy {

    private static final int ALCOHOL_TAX_PERCENTAGE = 20;

    @Override
    public Money taxAmount(Order order) {
        var taxValueAmount = order.getTotalAmount().amount().multiply(BigDecimal.valueOf(ALCOHOL_TAX_PERCENTAGE / 100));
        return new Money(taxValueAmount, order.getTotalAmount().currency());
    }

    @Override
    public boolean isApplicable(Order order) {
        return order.isContainsAlcohol();
    }
}
