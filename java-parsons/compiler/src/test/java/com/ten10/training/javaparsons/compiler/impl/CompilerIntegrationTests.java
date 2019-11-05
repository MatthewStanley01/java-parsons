package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


class CompilerIntegrationTests {

    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    ProgressReporter progressReporter = mock(ProgressReporter.class);
    @ParameterizedTest
    @EnumSource(TestData.class)
    void compilerFun(TestData data) {
        // Arrange
        JavaSolutionCompiler javaSolutionCompiler = new JavaSolutionCompiler(compiler);
        TestData.TestDataCompilableSolution solution = data.toSolution();

        // Act
        boolean result = javaSolutionCompiler.compile(solution, progressReporter);

        // Assert
        if (data.shouldPass()) {
            assertTrue(result);
            assertThat(solution.byteCode, hasSize(1));
            assertThat(solution.byteCode.get(0), is(javaByteCode()));
        } else {
            assertFalse(result);
        }
    }

    private Matcher<byte[]> javaByteCode() {
        return new TypeSafeMatcher<byte[]>() {
            @Override
            protected boolean matchesSafely(byte[] byteCode) {
                return (byteCode[0] == (byte) 0xCA
                    && byteCode[1] == (byte) 0xFE
                    && byteCode[2] == (byte) 0xBA
                    && byteCode[3] == (byte) 0xBE);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a byte[] starting with 0xCAFEBABE\n");
            }
        };
    }

    public enum TestData {

        @SuppressWarnings("unused") WORKING_CLASS("HelloWorld", true, "public class HelloWorld { " +
            "public static void main(String[] args) {" +
            "System.out.println(\"Hello World\");" +
            "}" +
            "}"),
        @SuppressWarnings("unused") ERROR_CLASS("HelloWorld", false, "public class HelloWorld { " +
            "public static void main(String[] args) {" +
            "System.out.println(\"Hello World\")" + // Missing Semicolon
            "}" +
            "}");

        private final String className;
        private final boolean shouldPass;
        private final String code;

        TestData(String className, boolean shouldPass, String code) {
            this.className = className;
            this.shouldPass = shouldPass;
            this.code = code;
        }


        public TestDataCompilableSolution toSolution() {
            return new TestDataCompilableSolution();
        }

        public boolean shouldPass() {
            return shouldPass;
        }

        private class TestDataCompilableSolution implements CompilableSolution {

            List<byte[]> byteCode = new ArrayList<>();

            @Override
            public CharSequence getFullClassText() {
                return code;
            }

            @Override
            public String getClassName() {
                return className;
            }

            @Override
            public void recordCompiledClass(byte[] byteCode) {
                this.byteCode.add(byteCode);
            }
        }
    }
}
