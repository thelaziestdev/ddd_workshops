package com.example.order.tax;

import com.example.order.Order;

import java.util.Arrays;
import java.util.List;

public class TaxPolicyFactory {

    public static StandardProductTaxPolicy STANDARD = new StandardProductTaxPolicy();
    public static AlcoholicProductTaxPolicy ALCOHOLIC = new AlcoholicProductTaxPolicy();
    private static List<TaxPolicy> policies = Arrays.asList(
            STANDARD,
            ALCOHOLIC
    );

    public static TaxPolicy getTaxPolicyForOrder(Order order) {
       return policies.stream().filter(policy -> policy.isApplicable(order)).findFirst().orElseThrow(() -> new RuntimeException("No tax policy found for order"));
    }

}
