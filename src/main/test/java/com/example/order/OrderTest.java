package com.example.order;

import com.example.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderTest {

    @Test
    public void canAddLineToOrder() {
        // given
        var order = new Order();

        // when
        var orderLine = new OrderLine(UUID.randomUUID().toString(), "2", "Beer", 1, new Money(BigDecimal.TEN, "USD"));
        order.addLine(orderLine);

        // then
        Assertions.assertEquals(order.getOrderLines().get(0), orderLine);
        Assertions.assertEquals(order.getTotalAmount(), orderLine.getPrice());
    }

}
