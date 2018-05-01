package org.xm.judger.util;

import java.math.BigDecimal;

import static java.lang.Math.round;

/**
 * @author zhouxuan
 */

public class DoubleUtil {
    /**
     * 最高分
     */
    private static final double MAX_SCORE=100.0;
    /**
     * 最低分
     */
    private static final double MIN_SCORE= 20;

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

    /**
     * 确保分数不越界
     */
    public static double  processScore(double score){
        if (score > MAX_SCORE)
            return MAX_SCORE;
        if (score < MIN_SCORE)
            return MIN_SCORE;
        return score;
    }
}
