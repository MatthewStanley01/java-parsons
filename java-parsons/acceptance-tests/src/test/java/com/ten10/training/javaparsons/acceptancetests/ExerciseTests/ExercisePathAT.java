package com.ten10.training.javaparsons.acceptancetests.ExerciseTests;

import com.ten10.training.javaparsons.acceptancetests.ExercisePageObjects.ExercisePage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
@SingleSession
@DisplayName("Tests for feature 7: select statements from dropdown menus")
public class ExercisePathAT {

    int[] correctInput = {1,2,3,4,4};
    int[] missingBracket = {1,2,3,4,0};
    int[] noClassDeclaration = {0,2,3,4,0};
    int[] noMethodDeclaration = {1,0,3,4,0};

    private final ExercisePage page;

    ExercisePathAT(ChromeDriver driver){ this.page = new ExercisePage(driver); }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(7, "Exercise Paths!");
    }

    @Test
    void correctAnswer() {
        page.trySolutionEPE(correctInput);
        assertThat(page.getOutput(), is("Exercise Paths!"));
        assertTrue(page.isSuccessful());
    }

    @Test
    void missingBracket() {
        page.trySolutionEPE(missingBracket);
        assertFalse(page.isSuccessful());
        assertTrue(page.getErrorLine().contains("Error on line: 4"));
        assertTrue(page.getErrors().contains("The compiler error description was: reached end of file while parsing"));
    }

    @Test
    void noClassDeclaration() {
        page.trySolutionEPE(noClassDeclaration);
        assertFalse(page.isSuccessful());
        assertTrue(page.getErrorLine().contains("Error on line: 2"));
        assertTrue(page.getErrors().contains("The compiler error description was: class, interface, or enum expected"));
    }

    @Test
    void noMethodDeclaration() {
        page.trySolutionEPE(noMethodDeclaration);
        assertFalse(page.isSuccessful());
        assertTrue(page.getErrorLine().contains("Error on line: 3"));
        assertTrue(page.getErrors().contains("The compiler error description was: illegal start of type"));
    }
}
