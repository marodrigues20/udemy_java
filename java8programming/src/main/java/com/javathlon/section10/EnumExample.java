package com.javathlon.section10;

import java.math.BigDecimal;

public class EnumExample {

  public static void main(String[] args) {

    Loan loan = new Loan(new BigDecimal("3000"), "talha ocakci");
    loan.status = LoanStatus.APPLIED;
    System.out.println(loan.status);

    LoanStatus[] allStatus = LoanStatus.values();

    for(int i = 0; i < allStatus.length; i++){
      System.out.println(allStatus[i] + " " + allStatus[i].dbKey);
    }

    // I get the index
    int ordinal = LoanStatus.APPROVED.ordinal();
    System.out.println(ordinal);

    //You queried a loan.
    LoanStatus status = LoanStatus.valueOf("NOT_STARTED");
    System.out.println("Db key of NOT_STARTED: " + status.dbKey);

    status = LoanStatus.findByDbKey("U");
    System.out.println(status);




  }

}
