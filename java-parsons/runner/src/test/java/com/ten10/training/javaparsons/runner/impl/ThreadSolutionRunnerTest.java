package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ThreadSolutionRunnerTest {

    private static final AtomicBoolean exampleMethodCalled = new AtomicBoolean(false);
    private static final AtomicBoolean takesArgsCalled = new AtomicBoolean(false);
    private static final AtomicBoolean instanceMethodCalled = new AtomicBoolean(false);
    private ProgressReporter progressReporter = mock(ProgressReporter.class);

    @SuppressWarnings("unused")
    static class Example {
        public static void exampleMethod() {
            exampleMethodCalled.set(true);
        }

        @SuppressWarnings("InfiniteLoopStatement")
        public static void blockForever() {
            //noinspection StatementWithEmptyBody
            while (true) ;
        }

        public static void takesArgs(int a, int b) {
            takesArgsCalled.set(true);
        }


        public void instanceMethod() {
            instanceMethodCalled.set(true);
        }
    }


    @Test
    void runShouldBeAbleToCallStaticMethodOnClass() throws ReflectiveOperationException, ExecutionException, InterruptedException {
        // Arrange
        exampleMethodCalled.set(false);
        ThreadSolutionRunner runner = new ThreadSolutionRunner();
        EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "exampleMethod";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        // Act
        /*boolean result = */runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        //Assert
        //assertTrue(result, "run() should have completed successfully");
        assertTrue(exampleMethodCalled.get(), "Our method should have been called");
    }

    @Test
    void runThrowsExceptionWhenParameterListArentEqual() {
        //Arrange
        ClassLoader classLoader = mock(ClassLoader.class);
        ThreadSolutionRunner threadSolutionRunner = new ThreadSolutionRunner();
        EntryPoint entryPoint = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "exampleMethod";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[2];
            }

            @Override
            public Object[] getParameters() {
                return new Object[1];
            }
        };
        //Assert
        assertThrows(IllegalArgumentException.class, () -> threadSolutionRunner.run(classLoader, entryPoint, progressReporter));
    }

    @Test
    @Tag("slow")
    void methodsShouldTimeOut() {
        // Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
        final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "blockForever";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        runner.setTimeout(500, TimeUnit.MILLISECONDS);
        // Act
        //Assert
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter));
    }

    @Test
    void methodsShouldAcceptParameters() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
        final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "takesArgs";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[]{int.class, int.class};
            }

            @Override
            public Object[] getParameters() {
                return new Object[]{1, 3};
            }
        };
        runner.setTimeout(500, TimeUnit.MILLISECONDS);
        // Act
        /*boolean result = */runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        //Assert
        //assertTrue(result, "run() should have completed successfully");
        assertTrue(takesArgsCalled.get(), "run() should have completed successfully");
    }


    @Test
    void handleInstanceMethods() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
        final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "instanceMethod";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        runner.setTimeout(500, TimeUnit.MILLISECONDS);
        // Act
        /*boolean result = */runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        //Assert
        //assertTrue(result, "run() should have completed successfully");
        assertTrue(instanceMethodCalled.get(), "run() should have completed successfully");
    }
}
