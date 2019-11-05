package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;

public class ReturnTypeChecker implements MethodReturnValueChecker {

    private final String goal;
    private final Object answer;

    public ReturnTypeChecker(String goal, Object answer) {
        this.answer = answer;
        this.goal = goal;
    }


    @Override
    public String getGoal() {
        return "The returned value is " + answer.toString();
    }

    @Override
    public Boolean validate(Object result, ProgressReporter progressReporter) {
        progressReporter.storeCapturedOutput(result.toString());
        return result.equals(answer);
    }
}
