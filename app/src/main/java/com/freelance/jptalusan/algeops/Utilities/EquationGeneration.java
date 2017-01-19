package com.freelance.jptalusan.algeops.Utilities;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by JPTalusan on 16/01/2017.
 */

public class EquationGeneration {

    public static Equation generateEquation(String equationType) {
        int maxPossible = 6;
        int minPossible = -6;
        int ax = pickRandom(minPossible, maxPossible);
        int b = 0;
        int cx = 0;
        int d = pickRandom(minPossible, maxPossible);

        if (d <= -5) {
            b = pickRandom((-10 - d), maxPossible);
        } else if (d >= -4 && d <= 4) {
            b = pickRandom(minPossible, maxPossible);
        } else {
            b = pickRandom(minPossible, (10 - d));
        }

        if (d <= -5) {
            cx = pickRandom((-10 - ax), maxPossible);
        } else if (d >= -4 && d <= 4) {
            cx = pickRandom(minPossible, maxPossible);
        } else {
            cx = pickRandom(minPossible, (10 - ax));
        }

        Equation generatedEquation = new Equation(ax, b, cx, d);
        if (!generatedEquation.isValid()) {
            generatedEquation = generateEquation("test");
        }

        System.out.println(generatedEquation.toString());
        System.out.println(generatedEquation.getPart(1));
        System.out.println(generatedEquation.getPart(2));
        return generatedEquation;
    }

    private static int pickRandom(int min, int max) {
        long seed = Calendar.getInstance().getTimeInMillis();
        Random rnd = new Random((int)seed);
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rnd.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
