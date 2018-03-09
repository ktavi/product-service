package com.example.product.data;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class RuleTest {

    private ClientInfo clientInfo = new ClientInfo(new BigDecimal("30000"), new BigDecimal("15000"), true);

    @Test
    public void testEquals() {
        Rule rule = new Rule("1 == 1");
        Rule copy = new Rule(rule);
        boolean equal = rule.equals(copy);
        Assert.assertTrue(equal);
    }

    @Test
    public void testCorrect() {
        new Rule("salary > claim and not debtor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectSyntax() {
        new Rule("salary >> claim and not debtor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectVariable() {
        new Rule("salaryy > claim and not debtor");
    }

    @Test
    public void testApplicable() {
        Rule rule = new Rule("salary > claim");
        boolean result = rule.apply(clientInfo);
        Assert.assertTrue(result);
    }

    @Test
    public void testNotApplicable() {
        Rule rule = new Rule("salary > claim and not debtor");
        boolean result = rule.apply(clientInfo);
        Assert.assertFalse(result);
    }
}
