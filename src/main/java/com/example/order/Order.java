package com.example.order;

import com.example.Money;
import com.example.event.Event;
import com.example.order.tax.TaxPolicy;
import com.example.order.tax.TaxPolicyFactory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@NoArgsConstructor
public class Order {

    public Order(String id, String currency) {
        this.id = id;
        this.totalAmount = Money.ZERO(currency);
        this.amountDue = Money.ZERO(currency);
        this.taxValue = Money.ZERO(currency);
    }

    @Transient
    @Getter
    private List<Event> events = new ArrayList<>();

    @Getter
    @Id
    private String id;

    @Getter
    private boolean containsAlcohol = false;

    @Embedded
    @Getter
    @AttributeOverrides({
            @AttributeOverride(name="amount",
                    column=@Column(name="total_due_amount")),
            @AttributeOverride(name="currency",
                    column=@Column(name="total_due_currency"))
    })
    private Money totalAmount;

    @Embedded
    @Getter
    @AttributeOverrides({
            @AttributeOverride(name="amount",
                    column=@Column(name="amount_due_amount")),
            @AttributeOverride(name="currency",
                    column=@Column(name="amount_due_currency"))
    })
    private Money amountDue;

    @Embedded
    @Setter
    @Getter
    @AttributeOverrides({
            @AttributeOverride(name="amount",
                    column=@Column(name="tax_due_amount")),
            @AttributeOverride(name="currency",
                    column=@Column(name="tax_due_currency"))
    })
    private Money taxValue;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    private List<OrderLine> orderLines = new ArrayList<>();

    public void addLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        totalAmount = totalAmount.add(orderLine.getPrice());
        amountDue = amountDue.add(orderLine.getPrice());
    }

    public void markAsAlcoholic() {
        this.containsAlcohol = true;
        setTaxPolicy(TaxPolicyFactory.ALCOHOLIC);
    }

    @Embedded
    private ClientDetails clientDetails;

    @Transient
    private TaxPolicy taxPolicy = TaxPolicyFactory.STANDARD;

    public void setTaxPolicy(TaxPolicy taxPolicy) {
        this.taxPolicy = taxPolicy;
    }

    public void calculateTax() {
        taxValue = taxPolicy.taxAmount(this);
    }

    private Money getTotalAmountWithTax() {
        return totalAmount.add(taxValue);
    }

    public void pay(Money amount) {
        if (amount.isGreaterThan(amountDue)) {
            throw new IllegalArgumentException("Amount is greater than amount due");
        }
        amountDue = amountDue.subtract(amount);
        events.add(new OrderPayedEvent(id));
    }
}
