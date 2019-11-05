package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>Facade over the classes in javax.tools, particularly {@link javax.tools.JavaCompiler}</p>
 *
 * <p>Input files and class files are stored in memory</p>
 */
public class JavaSolutionCompiler implements SolutionCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSolutionCompiler.class);
    private final JavaCompiler compiler;


    /**
     * Prepares a {@link javax.tools.JavaCompiler} to compile {@link com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution}.
     * @param compiler {@link javax.tools.JavaCompiler} used to compile user solutions.
     */
    public JavaSolutionCompiler(JavaCompiler compiler) {
        this.compiler = Objects.requireNonNull(compiler, "compiler");
    }


    /**
     * Uses the {@link javax.tools.JavaCompiler} provided in the constructor to compile the {@link com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution}.
     * Errors and progress are stored in the {@link ProgressReporter} through a {@link Logger}.
     * @param solution The submitted solution to be compiled. This will not be modified.
     * @param progressReporter An object which will collect errors. This is modified.
     * @return {@code True} if the {@link com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution} compiles successfully. {@code False} if the
     * {@code CompilableSolution} fails to compile.
     */
    @Override
    public boolean compile(final CompilableSolution solution, final ProgressReporter progressReporter) {
        Objects.requireNonNull(solution, "solution");
        Objects.requireNonNull(progressReporter, "progressReporter");

        LOGGER.info("Compiling {}", solution);

        DiagnosticListener<JavaFileObject> diagnostics = new ProgressReporterAdapter(progressReporter);
        List<SimpleJavaFileObject> compilationUnits = Collections.singletonList(new SolutionJavaFile(solution));

        try (JavaFileManager fileManager = new InMemoryFileManager(compiler.getStandardFileManager(diagnostics, null, null), solution)) {
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            return task.call();
        } catch (IOException e) {
            LOGGER.error("Error closing fileManager", e);
            throw new RuntimeException(e);
        }
    }
}
