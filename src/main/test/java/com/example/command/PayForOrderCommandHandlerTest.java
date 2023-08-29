package com.example.command;

import com.example.Money;
import com.example.order.Order;
import com.example.order.OrderLine;
import com.example.order.OrdersRepository;
import com.example.payment.Payment;
import com.example.payment.PaymentMethod;
import com.example.payment.PaymentRow;
import com.example.payment.PaymentsRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

@QuarkusTest
class PayForOrderCommandHandlerTest {

    @Inject
    Handler createPaymentCommandHandler;

    @Inject
    OrdersRepository ordersRepository;

    @Inject
    PaymentsRepository paymentsRepository;

    @BeforeEach
    @Transactional
    void cleanUp() {
        ordersRepository.deleteAll();
        paymentsRepository.deleteAll();
    }

    @Test
    @Transactional
    void canHandleCreatePaymentCommand() {
        // given
        var orderId = "1";
        var orderToAdd = new Order(orderId, "USD");
        orderToAdd.addLine(new OrderLine(UUID.randomUUID().toString(), "2", "Beer", 1, new Money(BigDecimal.TEN, "USD")));

        ordersRepository.persist(orderToAdd);
        var command = new PayForOrderCommand("1", new Money(BigDecimal.TEN, "USD"), PaymentMethod.CASH);

        // when
        createPaymentCommandHandler.handle(command);

        // then
        var order = ordersRepository.findById(orderId);
        Assertions.assertEquals(new Money(BigDecimal.TEN, "USD"), order.getTotalAmount());
        Assertions.assertEquals(new Money(BigDecimal.ZERO, "USD"), order.getAmountDue());
    }

    @Test
    @Transactional
    void canHandleCreatePaymentCommandWhenPaymentExists() {
        // given
        var orderId = "1";
        var orderToAdd = new Order(orderId, "USD");
        orderToAdd.addLine(new OrderLine(UUID.randomUUID().toString(), "2", "Beer", 1, new Money(BigDecimal.TEN, "USD")));

        var payment = new Payment("1", orderId, "USD");
        payment.addPayment(new PaymentRow(UUID.randomUUID().toString(), new Money(BigDecimal.valueOf(5), "USD"), PaymentMethod.CASH));
        paymentsRepository.persist(payment);
        orderToAdd.pay(payment.getPayments().get(0).getAmount());
        ordersRepository.persist(orderToAdd);

        var command = new PayForOrderCommand("1", new Money(BigDecimal.valueOf(2), "USD"), PaymentMethod.CASH);

        // when
        createPaymentCommandHandler.handle(command);

        // then
        var order = ordersRepository.findById(orderId);
        Assertions.assertEquals(new Money(BigDecimal.TEN, "USD"), order.getTotalAmount());
        Assertions.assertEquals(new Money(BigDecimal.valueOf(3), "USD"), order.getAmountDue());
    }

    @Test
    @Transactional
    void whenHandlingPaymentOrderEventPayedIsSendEndReceivedByListener() {
        // given
        var orderId = "1";
        var orderToAdd = new Order(orderId, "USD");
        orderToAdd.addLine(new OrderLine(UUID.randomUUID().toString(), "2", "Beer", 1, new Money(BigDecimal.TEN, "USD")));

        var payment = new Payment("1", orderId, "USD");
        payment.addPayment(new PaymentRow(UUID.randomUUID().toString(), new Money(BigDecimal.valueOf(5), "USD"), PaymentMethod.CASH));
        paymentsRepository.persist(payment);
        orderToAdd.pay(payment.getPayments().get(0).getAmount());
        ordersRepository.persist(orderToAdd);

        var command = new PayForOrderCommand("1", new Money(BigDecimal.valueOf(2), "USD"), PaymentMethod.CASH);

        // when
        createPaymentCommandHandler.handle(command);

        // then
        var order = ordersRepository.findById(orderId);
        Assertions.assertEquals(new Money(BigDecimal.TEN, "USD"), order.getTotalAmount());
        Assertions.assertEquals(new Money(BigDecimal.ZERO, "USD"), order.getAmountDue());
    }
}
