package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.ExercisePathsExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;

public class ExercisePathsExercise implements Exercise {

    private final String exerciseName;
    private SolutionCompiler compiler;
    private final String answer;
    private final int id;
    private final String description;
    private final SolutionRunner runner;
    int dropdownNumber;

    public ExercisePathsExercise(SolutionCompiler compiler, SolutionRunner runner, String answer, String exerciseName, int id, String description, int dropdownNumber) {
        this.compiler = compiler;
        this.runner = runner;
        this.answer =answer;
        this.exerciseName = exerciseName;
        this.id = id;
        this.description = description;
        this.dropdownNumber = dropdownNumber;
    }


    @Override
    public int getIdentifier() {
        return id;
    }

    @Override
    public String getTitle() {
        return "Exercise "+getIdentifier()+": "+exerciseName;
    }

    @Override
    public String getDescription() {
        return  description+ " " + exerciseName;
    }

    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) throws Exception {
        return new ExercisePathsExerciseSolution(compiler, runner, userInput, answer,  progressReporter);
    }

    @Override
    public int getDropdownNumber() { return dropdownNumber; }

    @Override
    public void close() throws Exception {

    }
}
