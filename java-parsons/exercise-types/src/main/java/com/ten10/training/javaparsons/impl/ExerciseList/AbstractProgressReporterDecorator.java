package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.ProgressReporter;

public class AbstractProgressReporterDecorator implements ProgressReporter {

    private final ProgressReporter wrapped;

    protected AbstractProgressReporterDecorator(ProgressReporter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void storeCapturedOutput(String output) {
        this.wrapped.storeCapturedOutput(output);
    }

    @Override
    public void reportCompilerError(long lineNumber, String message) {
        this.wrapped.reportCompilerError(lineNumber, message);
    }

    @Override
    public void reportCompilerInfo(long lineNumber, String message) {
        this.wrapped.reportCompilerInfo(lineNumber, message);
    }

    @Override
    public void reportRunnerError(String message) {
        this.wrapped.reportRunnerError(message);
    }
}
