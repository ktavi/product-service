package com.example.product.data;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {

    private Product product = new Product("Test product", new BigDecimal("100000"), BigDecimal.ZERO, 0);
    private ClientInfo infoWithAppropriateClaim = new ClientInfo(BigDecimal.ZERO, new BigDecimal("60000"), Boolean.FALSE);
    private ClientInfo infoWithInappropriateClaim = new ClientInfo(BigDecimal.ZERO, new BigDecimal("120000"), Boolean.FALSE);
    private Rule ruleStubPositive = new Rule("1 == 1");
    private Rule ruleStubNegative = new Rule("1 != 1");

    @Test
    public void testApplicable() {
        product.getRules().add(ruleStubPositive);
        boolean result = product.isApplicable(infoWithAppropriateClaim);
        Assert.assertTrue(result);
    }

    @Test
    public void testNotApplicableBecauseOfClaim() {
        product.getRules().add(ruleStubPositive);
        boolean result = product.isApplicable(infoWithInappropriateClaim);
        Assert.assertFalse(result);
    }

    @Test
    public void testNotApplicableBecauseOfRules() {
        product.getRules().add(ruleStubPositive);
        product.getRules().add(ruleStubNegative);
        boolean result = product.isApplicable(infoWithAppropriateClaim);
        Assert.assertFalse(result);
    }

    @Test
    public void testFindEqualRule() {
        Rule rule = ruleStubPositive;
        Rule copy = new Rule(rule);
        product.getRules().add(rule);
        Rule equal = product.findEqualRule(copy).get();
        boolean result = rule == equal;
        Assert.assertTrue(result);
    }

}
