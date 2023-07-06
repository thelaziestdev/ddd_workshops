package com.example.order.tax;

import com.example.order.Money;
import com.example.order.Order;

import java.math.BigDecimal;

public class StandardProductTaxPolicy implements TaxPolicy {

    private static final int STANDARD_TAX_PERCENTAGE = 10;

    @Override
    public Money taxAmount(Order order) {
        var taxValueAmount = order.getTotalAmount().amount().multiply(BigDecimal.valueOf(STANDARD_TAX_PERCENTAGE / 100));
        return new Money(taxValueAmount, order.getTotalAmount().currency());
    }

    @Override
    public boolean isApplicable(Order order) {
        return !order.isContainsAlcohol();
    }
}
