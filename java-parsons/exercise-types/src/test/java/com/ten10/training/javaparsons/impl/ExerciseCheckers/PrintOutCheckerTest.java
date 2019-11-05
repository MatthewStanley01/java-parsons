package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PrintOutCheckerTest {
    private String answer="Answer";
 PrintOutChecker printOutChecker=new PrintOutChecker(answer);
 ProgressReporter progressReporter=mock(ProgressReporter.class);
    @Test
    void getGoal() {
     assertEquals(printOutChecker.getGoal(),"The program prints out "+answer + "\n");

    }

    @Test
    void validate() {
        assertTrue(printOutChecker.validate(answer,progressReporter));
    }


}
