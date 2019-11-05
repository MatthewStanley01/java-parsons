package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InMemoryClassFileTest {

    @Test
    void dataWrittenToOutputStreamShouldEndUpInSolution() throws IOException {
        // Arrange
        CompilableSolution solution = mock(CompilableSolution.class);
        InMemoryClassFile file = new InMemoryClassFile("HelloWorld", solution);
        OutputStream stream = file.openOutputStream();
        byte[] bytes = {1};
        // Act
        stream.write(bytes);
        stream.close();
        // Assert
        verify(solution).recordCompiledClass(bytes);
    }

}
