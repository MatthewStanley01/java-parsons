package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StaticFieldValueCheckerTest {
    private int answer=4;
    StaticFieldValueChecker staticFieldValueChecker=new StaticFieldValueChecker("this", answer);
    ProgressReporter progressReporter=mock(ProgressReporter.class);
    @Test
    void getGoal() {

        assertEquals("this",staticFieldValueChecker.getGoal());
    }

    @Test
    void validate() {

    }
}
