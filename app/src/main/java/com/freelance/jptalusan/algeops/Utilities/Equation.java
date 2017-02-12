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

    public boolean isValid() {
        return !(ax == 0 && b == 0 && cx == 0 && d == 0);
    }

    public boolean isAnswerCorrect(int ax, int b, int cx, int d) {
        if ((this.ax == ax) &&
                (this.b == b) &&
                (this.cx == cx) &&
                (this.d == d))
        {
            return true;
        }
        return false;
    }

    public boolean isFinalAnswerCorrect(int x, int one) {
        if ((x == (ax + cx) &&
                one == (b + d))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Equation = (" + ax + "x+" + b + ")" + "(" + cx + "x+" + d + ")";
    }

    //TODO: must remove zeroes. Check algetiles, check formatting
    public String getPart(int part) {
        String output = "";
        int x = 0;
        int one = 0;
        //vars = (ax + c)
        if (part == 1) {
            x = ax;
            one = b;
        } else {
            x = cx;
            one = d;
        }

        if (x != 0)
            output += x + "x+";
        if (one != 0)
            output += one;

        output = output.replace(" ", "");
        output = output.replace("+-", "-");
        output = output.replace("+", " + ");
        output = output.replace("-", " - ");
        return output;
//        return (part == 1 ? ax + "x+" + b : cx + "x+" + d)
//                .replace("0x", "")
//                .replace("+0", "")
//                .replace("+-", " - ")
//                .replace("+", " + ");
    }
}
