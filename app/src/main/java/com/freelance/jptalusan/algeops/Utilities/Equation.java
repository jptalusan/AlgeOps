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
        return !(ax == 0 && b == 0 && cx == 0 && d == 0);
    }

    @Override
    public String toString() {
        return "Equation = (" + ax + "x+" + b + ")" + "(" + cx + "x+" + d + ")";
    }

    //TODO: must remove zeroes. Check algetiles
    public String getPart(int part) {
        return (part == 1 ? ax + "x+" + b : cx + "x+" + d)
                .replace("0x", "")
                .replace("+0", "")
                .replace("+-", " - ")
                .replace("+", " + ");
    }
}
