package com.weblab4.opilab3.utils;

public class AreaFucntions {
    public static boolean hitCheck(double x, double y, double r){
        return (checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r));
    }

    private static boolean checkCircle(double x, double y, double r){
        return (x >= 0) && (x <= r/2) && (y >= 0) && (y <= r/2*(Math.sqrt(1-4*  Math.pow(x, 2)/Math.pow(r, 2))));
    }

    private static boolean checkRectangle(double x, double y, double r){
        return (x >= 0) && (x <= r) && (y >= -r/2) && (y <= 0);
    }

    private static boolean checkTriangle(double x, double y, double r){
        return (x >= -r) && (x <= 0) && (y >= -r) && (y <= 0) && (y >= -x - r);
    }
}
