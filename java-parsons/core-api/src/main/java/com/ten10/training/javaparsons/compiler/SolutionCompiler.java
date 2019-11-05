package com.ten10.training.javaparsons.compiler;

import com.ten10.training.javaparsons.ProgressReporter;

/**
 * Encapsulates the machinery for dealing with a compiler, and provides a simple mechanism for compiling an exercise.
 */
public interface SolutionCompiler {

    interface CompilableSolution {

        CharSequence getFullClassText();

        String getClassName();

        void recordCompiledClass(byte[] byteCode);
    }

    /**
     * Compile the solution, reporting any errors or warnings to the progressReporter.
     * Returns true upon success.
     *
     * If compilation was successful, {@link CompilableSolution#recordCompiledClass(byte[])} will have been called.
     *
     * @param solution       The submitted solution to be compiled. This will not be modified.
     * @param progressReporter An object which will collect errors. This is modified.
     * @return a CompiledSolution upon success.
     * @throws NullPointerException if either parameter is null.
     */
    boolean compile(CompilableSolution solution, ProgressReporter progressReporter);


}
