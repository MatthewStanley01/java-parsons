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
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
@SingleSession
@DisplayName("Tests for feature 4: Two Squared")
public class TwoSquaredAT {

    private static final String TWO_SQUARED_CORRECT = "public class Main {public Integer main(String[] args) {return 2*2;}}";

    private final ExercisePage page;

    public TwoSquaredAT(ChromeDriver driver) {
        this.page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(4, "Two Squared");
    }

    @Test
    @Tag("acceptance-tests")
    void twoSquaredInputted() {

        page.trySolution(TWO_SQUARED_CORRECT);
        assertThat(page.getOutput(),is("4"));
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        assertTrue(page.getDescription().contains("Two Squared"));
    }

}
