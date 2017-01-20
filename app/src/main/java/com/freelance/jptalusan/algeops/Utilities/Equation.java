package com.freelance.jptalusan.algeops.Utilities;

/**
 * Created by JPTalusan on 20/01/2017.
 */

public class Equation {
    int ax;
    int b;
    int cx;
    int d;

    public Equation(int ax, int b, int cx, int d) {
        this.ax = ax;
        this.b = b;
        this.cx = cx;
        this.d = d;
    }

    boolean isValid() {
        if (ax == 0 && b == 0 && cx == 0 && d == 0) {
            return false;
        } else if (ax == 0 && b == 0) {
            return false;
        } else if (cx == 0 && d == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Equation = (" + ax + "x+" + b + ")" + "(" + cx + "x+" + d + ")";
    }

    public String getPart(int part) {
        return (part == 1 ? ax + "x+" + b : cx + "x+" + d).replace("+-", " - ").replace("+", " + ");
    }
}
