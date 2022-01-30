package ru.bliprog.runner;

import ru.bliprog.statistics.TestStatistic;

public interface TestRunner {
    void run(Class<?> testClass);

    TestStatistic getTestStatistic();
}
