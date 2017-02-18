package com.freelance.jptalusan.algeops.Utilities;

/**
 * Created by JPTalusan on 20/01/2017.
 */

public class Equation {
    private int ax;

    public int getAx() {
        return ax;
    }

    public int getB() {
        return b;
    }

    private int b;
    private int cx;

    public int getD() {
        return d;
    }

    public int getCx() {
        return cx;
    }

    private int d;

    Equation(int ax, int b, int cx, int d) {
        this.ax = ax;
        this.b = b;
        this.cx = cx;
        this.d = d;
    }

    boolean isValid() {
        return !((ax == 0 && b == 0) || (cx == 0 && d == 0));
    }

    public boolean isAdditionAnswerCorrect(int ax, int b, int cx, int d) {
        return ((this.ax == ax) &&
                (this.b == b) &&
                (this.cx == cx) &&
                (this.d == d));
    }

    public boolean isSubtractAnswerCorrect(int ax, int b) {
        return ((this.ax - this.cx) == ax &&
                (this.b - this.d) == b);
    }

    public boolean isFinalAnswerCorrect(int x, int one) {
        return ((x == (ax + cx) &&
                one == (b + d)));
    }

    @Override
    public String toString() {
        return "Equation = (" + ax + "x+" + b + ")" + "(" + cx + "x+" + d + ")".replace("+-", "-");
    }

    public String getPart(int part) {
        String output = "";
        int x;
        int one;
        //vars = (ax + c)
        if (part == 1) {
            x = ax;
            one = b;
        } else {
            x = cx;
            one = d;
        }

        if (x != 0)
            output += x + "x";
        if (x != 0 && one != 0)
            output += "+";
        if (one != 0)
            output += one;


        output = output.replace(" ", "");
        output = output.replace("+-", "-");
        output = output.replace("+", " + ");
        output = output.replace("-", " - ");
        return output;
    }

    public String computeSubtractAnswer() {
        String out = (this.ax - this.cx) + " + ";
        out += (this.b - this.d);
        return out;
    }
}
