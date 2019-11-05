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

import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("Tests for feature 6: writing just part of the code.")
@ExtendWith(SeleniumExtension.class)
@SingleSession
class CompleteTheCodeHelloWorldAT {

    private final ExercisePage page;
    private final String COMPLETE_HELLO_WORLD_CORRECT = "System.out.println(\"Hello World!\");";
    private final String COMPLETE_HELLO_WORLD_INCORRECT = "System.out.println(\"Hello World!\")";
    private final String COMPLETE_HELLO_WORLD_INFLOOP = "while(true){}";


    CompleteTheCodeHelloWorldAT(ChromeDriver driver) {
        page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(6, "Complete the code - Hello World!");
    }

    @Test
    @Tag("acceptance-tests")
    void helloWorldInputted() {
        page.trySolution(COMPLETE_HELLO_WORLD_CORRECT);
        assertTrue(page.getOutput().contains("Hello World!"));
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {

        assertTrue(page.getDescription().contains("Complete the Java code"));
    }

    @Test
    @Tag("acceptance-tests")
    void prefixCodeIsDisplayed() {

        assertThat(page.getPrefixCode(), not(isEmptyString()));

    }

    @Test
    @Tag("acceptance-tests")
    void followingCodeIsDisplayed() {

        assertThat(page.getSuffixCode(), not(isEmptyString()));
    }

    @Test
    @Tag("acceptance-tests")
    void lineNumbersAreTranslated() {
        page.trySolution(COMPLETE_HELLO_WORLD_INCORRECT);
        assertEquals(page.getErrorLine(), "Error on line: 1");
    }

    @Test
    @Tag("acceptance-tests")
    void timeoutError() {
        page.trySolution(COMPLETE_HELLO_WORLD_INFLOOP);
        System.out.println(page.getErrors());
        assertEquals(page.getErrors().toString(), "[The runner error description was: timeout error]");
    }

}




