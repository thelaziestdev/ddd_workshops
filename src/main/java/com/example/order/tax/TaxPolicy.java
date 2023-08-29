package com.example.order.tax;

import com.example.Money;
import com.example.order.Order;

public interface TaxPolicy {

    default Order apply(Order order) {
            order.setTaxPolicy(this);

            return order;
    }

    Money taxAmount(Order order);

    boolean isApplicable(Order order);

}


