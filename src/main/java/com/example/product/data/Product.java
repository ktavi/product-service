package com.example.product.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    /**
     * Null maxAmount value represents unbounded amount.
     */
    private BigDecimal maxAmount;

    @NotNull
    private BigDecimal rate;

    /**
     * Null termInMonths value represents unbounded term.
     */
    private Integer termInMonths;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rule> rules = new HashSet<>();

    /**
     * Default non-private constructor is needed for JPA and Jackson.
     */
    Product() {}

    public Product(String name, BigDecimal maxAmount, BigDecimal rate, Integer termInMonths) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.rate = rate;
        this.termInMonths = termInMonths;
    }

    public boolean isApplicable(ClientInfo info) {
        if (maxAmount != null && maxAmount.compareTo(info.getClaim()) < 0) {
            return false;
        }
        return rules.stream().allMatch(rule -> rule.apply(info));
    }

    public Optional<Rule> findEqualRule(Rule rule) {
        for (Rule current : getRules()) {
            if (current.equals(rule)) {
                return Optional.of(current);
            }
        }
        return Optional.empty();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Integer getTermInMonths() {
        return termInMonths;
    }

    public Set<Rule> getRules() {
        return rules;
    }
}
