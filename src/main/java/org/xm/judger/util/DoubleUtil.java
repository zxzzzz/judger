package org.xm.judger.util;

import java.math.BigDecimal;

import static java.lang.Math.round;

public class DoubleUtil {

    /**
     * 浮点数保留两位小数
     * @param value
     * @return
     */
    public static double stayTwoDec(double value){
        BigDecimal bigDecimal = new BigDecimal(value);
        double result = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    public static void main(String[] args) {
        double t=0.00987;
        t=stayTwoDec(t);
        System.out.println(t);
    }
}
