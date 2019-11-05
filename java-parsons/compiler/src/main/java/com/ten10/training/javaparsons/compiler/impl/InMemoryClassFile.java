package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

class InMemoryClassFile extends SimpleJavaFileObject {

    private final SolutionCompiler.CompilableSolution solution;

    private static URI nameToUri(String name) {
        return URI.create("string:///" + name.replace('.', '/') + Kind.CLASS.extension);
    }

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     * @inheritDoc
     */
    InMemoryClassFile(String name, SolutionCompiler.CompilableSolution solution) {
        super(nameToUri(name), Kind.CLASS);
        this.solution = solution;
    }

    /**
     * @inheritDoc
     */
    @Override
    public OutputStream openOutputStream() {
        return new ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                super.close();
                solution.recordCompiledClass(toByteArray());
            }
        };
    }
}
