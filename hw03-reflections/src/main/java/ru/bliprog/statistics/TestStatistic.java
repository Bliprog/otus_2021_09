package ru.bliprog.statistics;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public interface TestStatistic {
    List<Method> getPassedTests();

    Map<Method, Exception> getFailedTestsWithException();
}
