package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CapturedOutputChecker;
import com.ten10.training.javaparsons.impl.ClassChecker;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.BaseSolution;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.List;

public class WholeClassExercise implements Exercise {
    private final String exerciseName;
    private final String prefixCode;
    private final String suffixCode;
    private final SolutionCompiler compiler;
    private final SolutionRunner runner;
    private final int id;
    private final List<CapturedOutputChecker> capturedOutputCheckers;
    private final List<ClassChecker> classCheckers;
    private final List<MethodReturnValueChecker> methodReturnValueCheckers;
//    private final List<CompleteTheCodeChecker> completetheCodeCheckers;


    /**
     * Creates a new WholeClassExercise.
     *
     * @param compiler     Prepares an user input to be run.
     * @param runner
     * @param exerciseName Description of the exercise.
     * @param id           The unique identifier of an exercise.
     */
    public WholeClassExercise(SolutionCompiler compiler,
                              SolutionRunner runner,
                              List<CapturedOutputChecker> capturedOutputCheckers,
                              List<ClassChecker> classCheckers,
                              List<MethodReturnValueChecker> methodReturnValueCheckers,
                              String exerciseName,
                              int id,
                              String prefixCode,
                              String suffixCode) {
        this.compiler = compiler;
        this.runner = runner;
        this.exerciseName = exerciseName;
        this.id = id;
        this.capturedOutputCheckers = capturedOutputCheckers;
        this.classCheckers = classCheckers;
        this.methodReturnValueCheckers = methodReturnValueCheckers;
        this.prefixCode = prefixCode;
        this.suffixCode = suffixCode;
//        this.completetheCodeCheckers = completetheCodeCheckers;

    }


    /**
     * @return The unique identifier of an exercise.
     */
    @Override
    public int getIdentifier() {
        return id;
    }

    /**
     * @return A string containing the name of the exercise.
     */
    @Override
    public String getTitle() {
        return "Exercise " + getIdentifier() + ": " + exerciseName;
    }

    /**
     * @return A string containing the description and instruction of an exercise.
     */

    @Override
    public String getDescription() {
        StringBuilder goals = new StringBuilder();
        goals.append("Create a Java class that: \n");
        for (MethodReturnValueChecker checker : methodReturnValueCheckers) {
            goals.append(checker.getGoal());
        }
        for (ClassChecker checker : classCheckers) {
            goals.append(checker.getGoal());
        }
        for (CapturedOutputChecker checker : capturedOutputCheckers) {
            goals.append(checker.getGoal());
        }
        return goals.toString();
    }

    @Override
    public String getPrecedingCode() {

        return prefixCode;
    }

    @Override
    public String getFollowingCode() {

        return suffixCode;
    }

    public String returnAppendedUserInput(String userInput) {
        if (prefixCode != null && suffixCode != null) {
            return prefixCode + userInput + suffixCode;
        } else {
            return userInput;
        }

    }

    /**
     * @param userInput        The input provided by the user.
     * @param progressReporter The callback object to use when reporting compilation and test results.
     * @return A new PrintOutExerciseSolution from user input.
     */
    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) {
        return new BaseSolution(compiler, runner, returnAppendedUserInput(userInput), capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
    }

    @Override
    public void close() throws Exception {
    }
}
