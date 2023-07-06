package com.example.order;

import com.example.event.Event;
import com.example.event.EventType;

public class OrderPayedEvent extends Event {

    public String getOrderId() {
        return this.objectId;
    }

    public OrderPayedEvent(String orderId) {
        super(orderId, "order-payed", EventType.OrderPayed);
    }
}
