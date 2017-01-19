package com.freelance.jptalusan.algeops;

import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
        EquationGeneration.generateEquation("TEST");
    }
}