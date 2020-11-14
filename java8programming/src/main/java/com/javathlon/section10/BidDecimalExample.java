package com.javathlon.section10;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BidDecimalExample {

  public static void main(String[] args) {

    double a = 0.0003;
    double b = 0.0002;
    System.out.println(a-b); //9.999999999999999E-5 it isn't expected.
    // expected result 0.0001

    BigDecimal c = new BigDecimal("0.0003");
    BigDecimal d = new BigDecimal("0.0002");
    System.out.println(c.subtract(d)); // give me the correct result 0.0001

    BigDecimal e = new BigDecimal("0.0003");
    BigDecimal f = new BigDecimal("0.0002");
    System.out.println(e.multiply(f)); //Give me the correct result in E notation.

    BigDecimal g = new BigDecimal("0.0003");
    BigDecimal h = new BigDecimal("0.0002");
    System.out.println(g.multiply(h).toPlainString()); //Give me the correct 0.00000006
    // toPlainString() convert into a human format.

    BigDecimal capital = new BigDecimal("5000");
    double interestRate = 1.73;
    BigDecimal  result = capital.multiply((new BigDecimal(interestRate).divide(new BigDecimal(100))));
    System.out.println("************* " + result.setScale(2, RoundingMode.CEILING).toPlainString());











  }


}
