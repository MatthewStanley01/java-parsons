package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.CapturedOutputChecker;

public class PrintOutChecker implements CapturedOutputChecker {

    private final String answer;

    public PrintOutChecker(String answer) {
        this.answer = answer;
    }

    @Override
    public String getGoal() {
        return "The program prints out "+answer + "\n";
    }

    @Override
    public Boolean validate(String output, ProgressReporter progressReporter) {
        progressReporter.storeCapturedOutput(output);
        return output.trim().equals(answer);
    }
}
