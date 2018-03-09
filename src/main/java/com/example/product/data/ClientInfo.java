package com.example.product.data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class ClientInfo {

    @NotNull
    private BigDecimal salary;

    @NotNull
    private BigDecimal claim;

    @NotNull
    private Boolean debtor;

    /**
     * Default non-private constructor is needed for Jackson.
     */
    ClientInfo() {}

    public ClientInfo(BigDecimal salary, BigDecimal claim, Boolean debtor) {
        this.salary = salary;
        this.claim = claim;
        this.debtor = debtor;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public BigDecimal getClaim() {
        return claim;
    }

    public Boolean isDebtor() {
        return debtor;
    }
}
