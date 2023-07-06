package com.example.payment;

import com.example.order.Money;
import com.example.order.Order;
import com.example.order.OrderLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PaymentServiceTest {

    @Test
    void whenPayingForOrderThenAmountDueIsReducedAndTotalPayedIncreased() {
        // given
        var paymentService = new PaymentService();
        var order = new Order("1", "USD");
        order.addLine(new OrderLine("1", "2", "Beer", 1, new Money(BigDecimal.TEN, "USD")));
        var payment = new Payment("1", order.getId(), "USD");
        var paymentRow = new PaymentRow("1", new Money(BigDecimal.TEN, "USD"), PaymentMethod.CASH);

        // when
        paymentService.addPayment(payment, order, paymentRow);

        // then
        Assertions.assertEquals(order.getAmountDue(), new Money(BigDecimal.ZERO, "USD"));
        Assertions.assertEquals(payment.getTotalPayed(), new Money(BigDecimal.TEN, "USD"));
    }

    @Test
    void whenPayingAmountGreaterThanDueThenExceptionIsThrown() {
        // given
        var paymentService = new PaymentService();
        var order = new Order("1", "USD");
        order.addLine(new OrderLine("1", "2", "Beer", 1, new Money(BigDecimal.TEN, "USD")));
        var payment = new Payment("1", order.getId(), "USD");
        var paymentRow = new PaymentRow("1", new Money(BigDecimal.valueOf(11), "USD"), PaymentMethod.CASH);

        // when
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentService.addPayment(payment, order, paymentRow));

        // then
        Assertions.assertEquals("Amount is greater than amount due", exception.getMessage());
    }

    @Test
    void whenPayingWithDifferentCurrencyThanOrderedExceptionIsThrown() {
        // given
        var paymentService = new PaymentService();
        var order = new Order("1", "USD");
        order.addLine(new OrderLine("1", "2", "Beer", 1, new Money(BigDecimal.TEN, "USD")));
        var payment = new Payment("1", order.getId(), "USD");
        var paymentRow = new PaymentRow("1", new Money(BigDecimal.TEN, "EUR"), PaymentMethod.CASH);

        // when
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentService.addPayment(payment, order, paymentRow));

        // then
        Assertions.assertEquals("Cannot add different currencies", exception.getMessage());
    }



}
