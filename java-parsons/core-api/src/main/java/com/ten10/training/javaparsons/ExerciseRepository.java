package com.ten10.training.javaparsons;

/**
 * An object implementing this interface is used to fetch {@link Exercise} objects.
 *
 */
public interface ExerciseRepository {
    /**
     * @param identifier The unique identifier for an exercise
     * @return The {@link Exercise} corresponding to the identifier
     * @throws IllegalArgumentException if the identifier is not a valid Exercise.
     */
    Exercise getExerciseByIdentifier(int identifier);

    int getExerciseArraySize();

}
