package com.ten10.training.javaparsons.webapp.views;

import com.ten10.training.javaparsons.ProgressReporter;

import java.util.ArrayList;
import java.util.List;

public class Results implements ProgressReporter {

    /**
     * Nested class that sets the structure for information captured in results
     */
    public static class Information {

        private long lineNumber;
        private final String message;

        Information(long lineNumber, String message) {
            this.lineNumber = lineNumber;
            this.message = message;
        }

        Information(String message){
            this.message = message;
        }

        public long getLineNumber() {
            return lineNumber;
        }

        public String getMessage() {
            return message;
        }
    }

    private String output = "";

    private boolean succesfulSolution = false;

    private final List<Information> compilerErrors = new ArrayList<>();
    private final List<Information> compilerInfo = new ArrayList<>();
    private final List<Information> runnerErrors = new ArrayList<>();

    public String getOutput() {
        return output;
    }

    public List<Information> getCompilerErrors() {
        return compilerErrors;
    }

    public List<Information> getCompilerInfo() {
        return compilerInfo;
    }

    public List<Information> getRunnerErrors() {
        return runnerErrors;
    }

    @Override
    public void storeCapturedOutput(String output) {
        this.output = output;
    }

    @Override
    public void reportCompilerError(long lineNumber, String message) {
        compilerErrors.add(new Information(lineNumber, message));
    }

    @Override
    public void reportCompilerInfo(long lineNumber, String message) {
        compilerInfo.add(new Information(lineNumber, message));
    }

    /**
     * Set whether the provided solution was correct or not.
     * @param answer {@code True} if successful, {@code False} if unsuccessful.
     */
    public void setSuccessfulSolution(boolean answer) {
        succesfulSolution = answer;
    }

    @Override
    public void reportRunnerError(String message) {
        runnerErrors.add(new Information(message));
    }

    public boolean isSuccesfulSolution() {
        return succesfulSolution;
    }
}

//Future Dev Academy - all Javadoc is in the ProgressReporter Interface
