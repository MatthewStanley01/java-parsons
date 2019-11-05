package com.ten10.training.javaparsons.acceptancetests.ExerciseTests;

import com.ten10.training.javaparsons.acceptancetests.ExercisePageObjects.ExercisePage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumExtension.class)
@SingleSession
@DisplayName("Tests for Feature 3: 42 (Static Field Exercise)")
public class StaticFieldExerciseAT {

    private static final String CORRECT_STATIC_FIELD_ANSWER = "public class Main{ \n public static int i = 42; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_STATIC_FIELD_ANSWER = "public class Main{ \n public static int i = 40; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_TYPE_STATIC_FIELD_ANSWER = "public class Main{ \n public static String i = \"42\"; \npublic static void main(String[] args){\n}\n}";


    private ExercisePage page;

    public StaticFieldExerciseAT(ChromeDriver driver) {
        this.page = new ExercisePage(driver);
    }


    @BeforeEach
    void goToHomepage() {
        page.goToHomepage();
        page.chooseExercise(3,"42");
    }

    @Test
    @Tag("acceptance-tests")
    void containsCorrectAnswer() {
        page.trySolution(CORRECT_STATIC_FIELD_ANSWER);
        assertThat(page.getOutput(),is("42"));
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void doesNotContainCorrectAnswer() {
        page.trySolution(INCORRECT_STATIC_FIELD_ANSWER);
        assertFalse(page.isSuccessful());
        assertThat(page.getErrors(),
            contains("The runner error description was: Expected: 42. Received: 40"));
    }

    @Test
    @Tag("acceptance-tests")
    void incorrectTypeAnswer() {
        page.trySolution(INCORRECT_TYPE_STATIC_FIELD_ANSWER);
        assertFalse(page.isSuccessful());
        assertThat(page.getErrors(),hasItem(containsString("Expected: 42. Received: 42")));
    }
}
