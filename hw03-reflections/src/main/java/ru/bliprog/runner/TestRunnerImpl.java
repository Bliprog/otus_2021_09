package ru.bliprog.runner;

import lombok.SneakyThrows;
import ru.bliprog.annotations.After;
import ru.bliprog.annotations.Before;
import ru.bliprog.annotations.Test;
import ru.bliprog.annotationsProcessors.AnnotationProcessor;
import ru.bliprog.annotationsProcessors.AnnotationProcessorImpl;
import ru.bliprog.statistics.TestStatistic;
import ru.bliprog.statistics.TestStatisticImpl;

import java.lang.reflect.InvocationTargetException;

public class TestRunnerImpl implements TestRunner {
    AnnotationProcessor annotationProcessor = new AnnotationProcessorImpl();
    TestStatisticImpl testStatistic = new TestStatisticImpl();

    @Override
    @SneakyThrows
    public void run(Class<?> testClass) {
        var beforeMethods = annotationProcessor.getMethods(testClass, Before.class);
        var testMethods = annotationProcessor.getMethods(testClass, Test.class);
        var afterMethods = annotationProcessor.getMethods(testClass, After.class);
        var testInstance = testClass.getDeclaredConstructor().newInstance();
        for (var testMethod :
                testMethods) {
            //Не получилось запустить invoke в стриме, т.к. checked Exceptions
            for (var beforeMethod : beforeMethods) beforeMethod.invoke(testInstance);
            try {

                testMethod.invoke(testInstance);

            } catch (InvocationTargetException e) {
                testStatistic.addFailedTestsWithException(testMethod, (Exception) e.getTargetException());
                continue;
            }
            testStatistic.addPassedTest(testMethod);
            for (var afterMethod : afterMethods) afterMethod.invoke(testInstance);
        }
    }

    @Override
    public TestStatistic getTestStatistic() {
        return testStatistic;
    }
}
