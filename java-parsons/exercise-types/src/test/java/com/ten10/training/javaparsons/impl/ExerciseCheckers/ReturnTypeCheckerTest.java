package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReturnTypeCheckerTest {
    private int answer=4;
    ReturnTypeChecker returnTypeChecker=new ReturnTypeChecker("this", answer);
    ProgressReporter progressReporter=mock(ProgressReporter.class);

    @Test
    void getGoal() {
        assertEquals("The returned value is " + answer,returnTypeChecker.getGoal());
    }

    @Test
    void validate() {
        assertTrue(returnTypeChecker.validate(answer,progressReporter));
    }
}
