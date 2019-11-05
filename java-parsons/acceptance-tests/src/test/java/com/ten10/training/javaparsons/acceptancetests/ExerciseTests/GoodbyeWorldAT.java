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
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 2: Goodbye Cruel World!")
@ExtendWith(SeleniumExtension.class)
@SingleSession
public class GoodbyeWorldAT {
    private static final String GOODBYE_WORLD_CORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Goodbye Cruel World!\");}}";


    private final ExercisePage page;

    public GoodbyeWorldAT(ChromeDriver driver) {
        this.page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(2, "Goodbye Cruel World!");
    }

    @Test
    @Tag("acceptance-tests")
    void goodbyeCruelWorldInputted() {
        page.trySolution(GOODBYE_WORLD_CORRECT);
        assertThat(page.getOutput(), is("Goodbye Cruel World!"));
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        assertThat(page.getDescription(), containsString("Goodbye Cruel World!"));
    }
}
