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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 1: Hello World")
@ExtendWith(SeleniumExtension.class)
@SingleSession
public class HelloWorldAT {

    private static final String CORRECT_PROGRAM_THAT_PRINTS_HELLO_WORLD = "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
    private static final String INCORRECT_PROGRAM = "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\")}}";
    private static final String CORRECT_PROGRAM_THAT_DOESNT_PRINT_HELLO_WORLD = "public class Main {public static void main (String [] args) {System.out.println(\"Telly Tubby!\");}}";
    private static final String CORRECT_PROGRAM_WHERE_CLASS_NAME_DOESNT_MATCH_EXPECTATION = "public class ain {public static void main (String [] args) {System.out.println(\"Hello World!\");}}";
    private static final String NOT_MAIN_METHOD = "public class Main {public static void ain (String [] args) {System.out.println(\"Hello World!\");}}";
    private static final String CORRECT_PROGRAM_THAT_PRODUCES_WARNINGS =
        "import java.util.List;\n" +
            "import java.util.ArrayList;\n" +
            "\n" +
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        List<?> l = new ArrayList<>();\n" +
            "        List<String> l2 = (List<String>) l;\n" +
            "        System.out.println(\"Hello World!\");\n" +
            "    }\n" +
            "}";


    private final ExercisePage page;

    HelloWorldAT(ChromeDriver driver) {
        page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(1, "Hello World!");
    }

    @Test
    @Tag("acceptance-tests")
    void helloWorldInputted() {
        page.trySolution(CORRECT_PROGRAM_THAT_PRINTS_HELLO_WORLD);
        assertThat(page.getOutput(), is("Hello World!"));
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void semiColonIsMissed() {
        page.trySolution(INCORRECT_PROGRAM);
        assertFalse(page.isSuccessful());
        assertThat(page.getErrors(),
            contains("The compiler error description was: ';' expected"));
    }

    @Test
    @Tag("acceptance-tests")
    void notHelloWorld() {
        page.trySolution(CORRECT_PROGRAM_THAT_DOESNT_PRINT_HELLO_WORLD);
        assertThat(page.getOutput(), is("Telly Tubby!"));
        assertFalse(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void notCalledClassMain() {
        page.trySolution(CORRECT_PROGRAM_WHERE_CLASS_NAME_DOESNT_MATCH_EXPECTATION);
        assertThat(page.getErrors(),
            hasItem(containsString("class ain is public, should be declared in a file named ain.java")));
        assertFalse(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void notCalledMethodMain() {
        page.trySolution(NOT_MAIN_METHOD);
        assertThat(page.getErrors(), hasItem(containsString("No such method main")));
    }

    @Test
    @Tag("acceptance-tests")
    void informationBoxDisplayed() {
        page.trySolution(CORRECT_PROGRAM_THAT_PRODUCES_WARNINGS);
        assertThat(page.getInfo(), not(isEmptyString()));
        assertTrue(page.isSuccessful());
    }
}


