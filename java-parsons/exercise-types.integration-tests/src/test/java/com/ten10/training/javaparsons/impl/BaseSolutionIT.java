package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.BaseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseSolutionIT {

    private static final String USER_INPUT = "Answer ";
    private SolutionCompiler mockCompiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ThreadSolutionRunner mockRunner = mock(ThreadSolutionRunner.class);
    private PrintOutChecker printOutChecker = mock(PrintOutChecker.class);
    private List<CapturedOutputChecker> capturedOutputCheckers = singletonList(printOutChecker);
    private List<ClassChecker> classCheckers = new ArrayList<>();
    private List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final BaseSolution baseSolution = new BaseSolution(mockCompiler, mockRunner, USER_INPUT, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
    private ClassLoader classLoader = mock(ClassLoader.class);
    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);
    private final SolutionRunner.RunResult runResult = mock(SolutionRunner.RunResult.class);

    @BeforeEach
    void setUpMocks() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // By default, compiling and running methods solutions should be successful

        // The contract of SolutionCompiler.compile() is that the callback method recordCompiledClass will be called.
        // We therefore need to do this more advanced stubbing to get hold of the callback object, and invoke the
        // method before returning true.
        //
        // When we call compile on the solution compiler...
        when(mockCompiler.compile(any(SolutionCompiler.CompilableSolution.class), any(ProgressReporter.class)))
            // ... then ...
            .thenAnswer((Answer<Boolean>) invocationOnMock -> {
                // ... Get the callback object
                SolutionCompiler.CompilableSolution callback = invocationOnMock.getArgument(0);
                // .. and invoke the method
                callback.recordCompiledClass(new byte[0]);
                // ... before returning true.
                return true;
            });

        when(runResult.isSuccess()).thenReturn(true);
        when(mockRunner.run(any(ClassLoader.class), any(SolutionRunner.EntryPoint.class), any(ProgressReporter.class))).thenReturn(runResult);
    }


    @Test
    @DisplayName("Calling evaluate should call SolutionCompiler.compile()")
    void evaluateCallsCompiler() throws Exception {
        // Act
        baseSolution.evaluate();
        // Assert
        verify(mockCompiler).compile(baseSolution, progressReporter);
    }

    @Test
    @DisplayName("Calling getFullClassText() should return the provided text")
        // TODO: Should build the text from template!
    void getFullClassText() {
        assertEquals(USER_INPUT, baseSolution.getFullClassText());
    }

    @Test
    void getClassName() {  // TODO: Should probably get this from the entrypoint!
        assertEquals("Main", baseSolution.getClassName());
    }


    @Test
    void correctCheckerCalledPrintOut() throws Exception {
        // Act
        baseSolution.evaluate();
        // Assert
        verify(printOutChecker).validate("", progressReporter);
    }

    @Test
    void correctCheckerCalledPrintOutReturnsTrue() throws Exception {
        when(printOutChecker.validate("", progressReporter)).thenReturn(true);
        assertTrue(baseSolution.evaluate());
    }

    @Test
    void earlyReturnWhenCompileFails() throws Exception {
        when(mockCompiler.compile(any(SolutionCompiler.CompilableSolution.class), any(ProgressReporter.class))).thenReturn(false);
        baseSolution.evaluate();
        verify(mockRunner, never()).run(classLoader, entryPoint, progressReporter);
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
        BaseSolution baseSolution = new BaseSolution(compiler, runner, userInput, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
        baseSolution.evaluate();
        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
    }

    //is an IT
    @Test
    void runTimeFailure() throws Exception {
        //ARRANGE
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput =
            "public class Main{\npublic String i = \"42\";\npublic static void main(String[] args){while(true){}}}";
        BaseSolution baseSolution =
            new BaseSolution(compiler, runner, userInput, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
        //ACT
        boolean evaluateResult = baseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }
}
