package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class SolutionJavaFile extends SimpleJavaFileObject {

    private final CompilableSolution solution;

    private static URI uriFromSolution(CompilableSolution solution) {
        return URI.create(
                "string:///"
                        + solution.getClassName().replace('.', '/')
                        + Kind.SOURCE.extension);
    }

    /**
     * @inheritDoc
     */
    SolutionJavaFile(CompilableSolution solution) {
        super(uriFromSolution(solution), Kind.SOURCE);
        this.solution = solution;
    }

    /**
     * Get the content of the {@link CompilableSolution} as a {@code CharSequence}.
     * @param ignoreEncodingErrors Unused by this {@code @Override} - exists because of {@code super}.
     * @return {@code CharSequence} of the user input.
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return solution.getFullClassText();
    }
}
