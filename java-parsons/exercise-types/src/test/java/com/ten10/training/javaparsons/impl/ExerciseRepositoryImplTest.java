package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.impl.ExerciseList.WholeClassExercise;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ExerciseRepositoryImplTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final SolutionRunner runner = mock(SolutionRunner.class);
    private final ExerciseRepositoryImpl exerciseRepository = new ExerciseRepositoryImpl(compiler, runner);


    @Test
    void exercise1IsAWholeClassExercise() {
        ExerciseRepositoryImpl exerciseRepository = new ExerciseRepositoryImpl(compiler, runner);
        assertThat(exerciseRepository.getExerciseByIdentifier(1), is(instanceOf(WholeClassExercise.class)));
    }

    @Test
    void getExerciseArraySize(){
        assertEquals(exerciseRepository.getExerciseArraySize(),(exerciseRepository.exercises.size()));
    }
}
