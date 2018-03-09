package com.example.product.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


/**
 * Rule instance represents restrictions on ClientInfo instances.
 * Restriction must be defined as SpEL expression. Rules with equal expressions are equal.
 * Immutable.
 */
@Entity
public class Rule {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String expression;

    /**
     * Default non-private constructor is needed for JPA and Jackson.
     */
    Rule() {}

    public Rule(String expression) {
        validate(expression);
        this.expression = expression;
    }

    /**
     * Copy constructor.
     * @param rule original Rule instance
     */
    public Rule(Rule rule) {
        this(rule.getExpression());
    }

    public boolean apply(ClientInfo info) {
        return new SpELClientInfoPredicate(expression).apply(info);
    }

    private void validate(String expression) {
        new SpELClientInfoPredicate(expression);
    }

    public Long getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(expression, rule.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
