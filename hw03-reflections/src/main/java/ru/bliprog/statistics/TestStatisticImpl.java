package ru.bliprog.statistics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestStatisticImpl implements TestStatistic {
    List<Method> passedTests = new ArrayList<>();
    Map<Method, Exception> failedTestsWithException = new HashMap<>();

    @Override
    public List<Method> getPassedTests() {
        return passedTests;
    }

    @Override
    public Map<Method, Exception> getFailedTestsWithException() {
        return failedTestsWithException;
    }

    public void addPassedTest(Method method) {
        passedTests.add(method);
    }

    public void addFailedTestsWithException(Method method, Exception exception) {
        failedTestsWithException.put(method, exception);
    }
}
