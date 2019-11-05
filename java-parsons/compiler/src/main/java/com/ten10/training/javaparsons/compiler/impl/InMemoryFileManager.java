package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;

// TODO: Use a full implementation of JavaFileManager
class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryFileManager.class);
    private SolutionCompiler.CompilableSolution solution;

    /**
     * Creates a new instance of {@link InMemoryFileManager}.
     *
     * @param fileManager delegate to this file manager
     * @param solution    solution that this memory manager relates to.
     */
    InMemoryFileManager(StandardJavaFileManager fileManager, SolutionCompiler.CompilableSolution solution) {
        super(fileManager);
        this.solution = solution;
    }

    /**
     * @inheritDoc
     * @return a new {@link FileObject} with the parameters provided.
     * @throws IOException
     */
    @Override
    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        // LOGGER.debug("getFileForInput called with ({}, {}, {})", location, packageName, relativeName);
        return super.getFileForInput(location, packageName, relativeName);
    }

    /**
     * @inheritDoc
     * @return a new {@link InMemoryClassFile} with the {@code className} and {@link com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution}.
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        // LOGGER.debug("getJavaFileForOutput called with ({}, {}, {}, {})",
        //     location, className, kind, sibling);
        return new InMemoryClassFile(className, solution);
    }
}
