package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CaptureConsoleOutput;
import com.ten10.training.javaparsons.impl.CapturedOutputChecker;
import com.ten10.training.javaparsons.impl.ClassChecker;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BaseSolution implements Solution, SolutionCompiler.CompilableSolution {

    private static SolutionRunner.EntryPoint entryPoint = new SolutionRunner.EntryPoint() {

        @Override
        public String getEntryPointClass() {
            return "Main";
        }

        @Override
        public String getEntryPointMethod() {
            return "main";
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return new Class<?>[]{String[].class};
        }

        @Override
        public Object[] getParameters() {
            return new Object[]{new String[]{}};
        }
    };

    private final SolutionCompiler compiler;
    private final SolutionRunner runner;
    private final String userInput;
    private final ProgressReporter progressReporter;
    private CaptureConsoleOutput captureConsoleOutput = new CaptureConsoleOutput();
    private byte[] byteCode;
    private final List<CapturedOutputChecker> capturedOutputCheckers;
    private final List<ClassChecker> classCheckers;
    private final List<MethodReturnValueChecker> methodReturnValueCheckers;
    private Object result;
    private Field[] klassFields;

    /**
     * Creates a new PrintOutExerciseSolution. This constructor sets the local fields.
     *
     * @param compiler         SolutionCompiler to compile the user input.
     * @param runner           ThreadSolutionRunner to run the compiled code.
     * @param userInput        The user input as a String.
     * @param progressReporter ProgressReporter for storing the result of compiling and running the user input.
     */
    public BaseSolution(SolutionCompiler compiler,
                        SolutionRunner runner,
                        String userInput,
                        List<CapturedOutputChecker> capturedOutputCheckers,
                        List<ClassChecker> classCheckers,
                        List<MethodReturnValueChecker> methodReturnValueCheckers,
                        ProgressReporter progressReporter) {

        this.compiler = compiler;
        this.runner = runner;
        this.userInput = userInput;
        this.capturedOutputCheckers = capturedOutputCheckers;
        this.classCheckers = classCheckers;
        this.methodReturnValueCheckers = methodReturnValueCheckers;
        this.progressReporter = progressReporter;

    }

    /**
     * Compile and Run the stored user input then compares the output to the expected output.
     *
     * @return True if the output matches, else return False.
     * @throws Exception Exceptions when
     */
    @Override
    public boolean evaluate() throws Exception {
        if (!compile()) {
            return false;
        }
        boolean result;
        result = run();
        if (!result) {
            return false;
        }

        ArrayList<Boolean> results = new ArrayList<>();
        for (CapturedOutputChecker checker : capturedOutputCheckers) {
            results.add(checker.validate(output, progressReporter));
        }
        for (ClassChecker checker : classCheckers) {
            if (getClassFields(getClassLoader())) {
                results.add(checker.validate(klassFields, progressReporter));
            }
        }
        for (MethodReturnValueChecker checker : methodReturnValueCheckers) {
            results.add(checker.validate(result, progressReporter));
        }
        return !results.contains(false);


    }

    private boolean compile() {
        boolean result = compiler.compile(this, progressReporter);
        assert !result || (byteCode != null);
        return result;
    }

    /**
     * @return The User Input as a CharSequence.
     */
    @Override
    public CharSequence getFullClassText() {
        return userInput;
    }

    /**
     * @return The name of the Class for the solution.
     */
    @Override
    public String getClassName() {
        return "Main";
    }

    /**
     * Store the byteCode of a solution. Used by the Runner to run the code.
     *
     * @param byteCode compiled java code.
     */
    @Override
    public void recordCompiledClass(byte[] byteCode) {
        this.byteCode = byteCode;
    }

    private ClassLoader getClassLoader() {

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        return new ClassLoader(contextClassLoader) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (name.equals(getClassName())) {
                    Class<?> clazz = defineClass(name, byteCode, 0, byteCode.length);
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return clazz;
                }
                return super.loadClass(name, resolve);
            }
        };
    }

    private String output = "";

    private boolean run() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        captureConsoleOutput.start();
        try {
            return runner.run(getClassLoader(), entryPoint, progressReporter).isSuccess();
        } finally {
            this.output = captureConsoleOutput.stop();
            progressReporter.storeCapturedOutput(output);
        }
    }

    private boolean getClassFields(ClassLoader classLoader) {
        try {
            Class<?> klass = classLoader.loadClass(entryPoint.getEntryPointClass());
            klassFields = klass.getFields();
            if (klassFields.length != 0) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            progressReporter.reportRunnerError("No such class " + entryPoint.getEntryPointClass());
        }

        return false;
    }
}
