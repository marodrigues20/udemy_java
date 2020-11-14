package com.javathlon.section10.ds;

import java.math.BigDecimal;

public class Loan {

    BigDecimal amount;
    String customerName;
    LoanStatus status;

    public Loan(BigDecimal amount, String customerName) {
        super();
        this.amount = amount;
        this.customerName = customerName;
        status = LoanStatus.NOT_STARTED;
    }

    

    
}
