package com.freelance.jptalusan.algeops.Utilities;

import android.util.Log;

import java.util.Calendar;
import java.util.Random;


/**
 * Created by JPTalusan on 16/01/2017.
 */

public class EquationGeneration {
    public static String TAG = "EquationGeneration";
    public static Equation generateEquation(String equationType, int subtractionRestriction) {
        int maxPossible = 6;
        int minPossible = -6;

        long seed = Calendar.getInstance().getTimeInMillis();
        Random rnd = new Random((int)seed);

        int ax = pickRandom(rnd, minPossible, maxPossible);
        int b = 0;
        int cx = 0;
        int d = pickRandom(rnd, minPossible, maxPossible);

        if (d <= -5) {
            b = pickRandom(rnd, (-10 - d), maxPossible);
        } else if (d >= -4 && d <= 4) {
            b = pickRandom(rnd, minPossible, maxPossible);
        } else {
            b = pickRandom(rnd, minPossible, (10 - d));
        }

        if (d <= -5) {
            cx = pickRandom(rnd, (-10 - ax), maxPossible);
        } else if (d >= -4 && d <= 4) {
            cx = pickRandom(rnd, minPossible, maxPossible);
        } else {
            cx = pickRandom(rnd, minPossible, (10 - ax));
        }

        Equation generatedEquation = new Equation(ax, b, cx, d);
        if (equationType == Constants.ADD) {
            if (!generatedEquation.isValid(equationType)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                generatedEquation = generateEquation(equationType, subtractionRestriction);
            }
        } else if (equationType == Constants.SUB) {
            if (subtractionRestriction <= Constants.LEVEL_2) {
                Log.d(TAG, "Underrestrictions");
                if (!generatedEquation.isValidWithRestrictions()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    generatedEquation = generateEquation(equationType, subtractionRestriction);
                }
            } else {
                if (!generatedEquation.isValid(equationType)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    generatedEquation = generateEquation(equationType, subtractionRestriction);
                }
            }
        }

        System.out.println(generatedEquation.toString());
        System.out.println(generatedEquation.getPart(1));
        System.out.println(generatedEquation.getPart(2));
        return generatedEquation;
    }

    private static int pickRandom(Random rnd, int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rnd.nextInt((max - min) + 1) + min;
    }
}
