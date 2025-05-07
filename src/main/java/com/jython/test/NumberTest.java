package com.jython.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @ClassName:NumberTest
 * @author: qm
 * @Description:
 * @date:2025-04-10
 */
public class NumberTest {
    public static void main(String[] args) {
        BigDecimal number = new BigDecimal("0.9");
        BigDecimal fullNumber = BigDecimal.ONE;
        System.out.println(number.divide(fullNumber, RoundingMode.DOWN));
    }
}
