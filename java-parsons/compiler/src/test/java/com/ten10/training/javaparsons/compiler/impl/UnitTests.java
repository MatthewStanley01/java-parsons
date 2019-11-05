package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import static com.ten10.training.javaparsons.compiler.impl.CompilerIntegrationTests.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UnitTests {
    @Test
    void saveByteArrayOutputStream() throws IOException {
        //Arrange
        SolutionCompiler.CompilableSolution solution = mock(SolutionCompiler.CompilableSolution.class);
        InMemoryClassFile file = new InMemoryClassFile("HelloWorld", solution);
        OutputStream stream = file.openOutputStream();
        //Act
        stream.write(1);
        stream.close();
        //Assert
        verify(solution).recordCompiledClass(new byte[]{1});
    }

}
