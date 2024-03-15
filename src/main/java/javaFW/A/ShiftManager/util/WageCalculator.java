package javaFW.A.ShiftManager.util;

public class WageCalculator {
    public static int calculateNightHourlyWage(int hourlyWage) {
        // 夜間時給は時給の1.3倍として計算
        return (int) (hourlyWage * 1.3);
    }
}
