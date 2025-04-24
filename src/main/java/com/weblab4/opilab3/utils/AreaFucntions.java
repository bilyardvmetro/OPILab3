package com.weblab4.opilab3.utils;

/**
 * Area checker
 */
public class AreaFucntions {
    /**
     * check all area
     *
     * @param x x
     * @param y y
     * @param r r
     * @return true if hit
     */
    public static boolean hitCheck(double x, double y, double r) {
        return (checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r));
    }

    /**
     * check circle
     *
     * @param x x
     * @param y y
     * @param r r
     * @return true if hit circle
     */
    private static boolean checkCircle(double x, double y, double r) {
        return (x >= 0) && (x <= r / 2) && (y >= 0) && (y <= r / 2 * (Math.sqrt(1 - 4 * Math.pow(x, 2) / Math.pow(r, 2))));
    }

    /**
     * check rectangle
     *
     * @param x x
     * @param y y
     * @param r r
     * @return true if hit rectangle
     */
    private static boolean checkRectangle(double x, double y, double r) {
        return (x >= 0) && (x <= r) && (y >= -r / 2) && (y <= 0);
    }

    /**
     * check triangle
     *
     * @param x x
     * @param y y
     * @param r r
     * @return true if hiy triangle
     */
    private static boolean checkTriangle(double x, double y, double r) {
        return (x >= -r) && (x <= 0) && (y >= -r) && (y <= 0) && (y >= -x - r);
    }
}
