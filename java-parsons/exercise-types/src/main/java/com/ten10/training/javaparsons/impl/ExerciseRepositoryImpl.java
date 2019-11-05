package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.ReturnTypeChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
import com.ten10.training.javaparsons.impl.ExerciseList.WholeClassExercise;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    public final List<? extends Exercise> exercises;

    /**
     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
     * @param compiler Prepares an user input to be run.
     * @param runner
     */
    public ExerciseRepositoryImpl(SolutionCompiler compiler, SolutionRunner runner) {
        WholeClassExercise helloWorld = new WholeClassExercise(compiler, runner, new ArrayList<>(Arrays.asList(new PrintOutChecker("Hello World!"))), new ArrayList<>(), new ArrayList<>(), "Whole Class \"Hello world\"", 1,null,null);
        WholeClassExercise cruelWorld = new WholeClassExercise(compiler, runner, new ArrayList<>(Arrays.asList(new PrintOutChecker("Goodbye Cruel World!"))), new ArrayList<>(), new ArrayList<>(), "Goodbye Cruel World!", 2,null,null);
        WholeClassExercise staticField = new WholeClassExercise(compiler, runner, new ArrayList<>(), new ArrayList<>(Arrays.asList(new StaticFieldValueChecker("Has a static int field with a value of 3 \n", 3))), new ArrayList<>(), "Static Field", 3,null,null);
        WholeClassExercise returnSquareNumber = new WholeClassExercise(compiler, runner, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(new ReturnTypeChecker("Returns an int with the value of 2 squared", 4))), "Two Squared", 4,null,null);
        WholeClassExercise returnChar = new WholeClassExercise(compiler, runner, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(new ReturnTypeChecker("Returns a Char with value 'A'", 'A'))), "Return Char A", 5,null,null);
        WholeClassExercise completeTheCodeHelloWorld = new WholeClassExercise(compiler, runner, new ArrayList<>(Arrays.asList(new PrintOutChecker("Hello World!"))), new ArrayList<>(), new ArrayList<>(), "Complete the code - Hello World!", 6,"public class Main { \npublic static void main (String[] args) {","}\n}");
        exercises = Arrays.asList(helloWorld, cruelWorld, staticField, returnSquareNumber, returnChar, completeTheCodeHelloWorld);

    }


    /**
     * @param identifier The unique identifier for an exercise
     * @return The exercise with a given id.
     */
    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        for (Exercise exercise : exercises) {
            if (exercise.getIdentifier() == identifier) {
                return exercise;
            }
        }
        return null;
    }

    /**
     * @return The number of exercises available.
     */
    @Override
    public int getExerciseArraySize() {
        return exercises.size();
    }
}
