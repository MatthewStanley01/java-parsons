package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.ExerciseList.AbstractProgressReporterDecorator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AbstractProgressReporterDecoratorTest {
    private static final List<Consumer<ProgressReporter>> METHOD_CALLS = Arrays.asList(
        progressReporter -> progressReporter.reportCompilerInfo(1, "message"),
        progressReporter -> progressReporter.reportRunnerError("message"),
        progressReporter -> progressReporter.reportCompilerError(1, "Message"),
        progressReporter -> progressReporter.storeCapturedOutput("output")
    );

    static class MethodCall implements Consumer<ProgressReporter> {

        private final Consumer<ProgressReporter> methodCall;
        private String description;

        MethodCall(Consumer<ProgressReporter> methodCall) {
            this.methodCall = methodCall;
            makeDescription(methodCall);
        }

        private void makeDescription(Consumer<ProgressReporter> methodCall) {
            ProgressReporter proxy = (ProgressReporter) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{ProgressReporter.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Iterable<String> paramStrs = Arrays.stream(args).map(Object::toString).collect(Collectors.toList());
                    String paramStr = String.join(", ", paramStrs);
                    description = method.getName() + "(" + paramStr + ")";
                    return null;
                }
            });
            methodCall.accept(proxy);
        }

        @Override
        public void accept(ProgressReporter progressReporter) {
            methodCall.accept(progressReporter);
        }

        @Override
        public String toString() {
            return description;
        }
    }

    static Stream<Consumer<ProgressReporter>> getMethods() {
        return METHOD_CALLS.stream().map(
            MethodCall::new
        );
    }

    private static class NullProgressReporterDecorator extends AbstractProgressReporterDecorator {
        NullProgressReporterDecorator(ProgressReporter wrapped) {
            super(wrapped);
        }
    }

    @ParameterizedTest(name = "wrapper.{arguments} should call wrapped.{arguments}")
    @MethodSource("getMethods")
    void TestMethod(Consumer<ProgressReporter> methodCall) {
        ProgressReporter wrapped = mock(ProgressReporter.class);
        ProgressReporter wrapper = new NullProgressReporterDecorator(wrapped);

        methodCall.accept(wrapper);

        methodCall.accept(verify(wrapped));
    }
}
