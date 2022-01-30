package ru.bliprog;

import ru.bliprog.demoTests.DemoTest1;
import ru.bliprog.runner.TestRunner;
import ru.bliprog.runner.TestRunnerImpl;
import ru.bliprog.statistics.TestStatisticConsoleWriter;
import ru.bliprog.statistics.TestStatisticWriter;

public class TestStarter {

    private static void doTest(Class<?> testClass) {
        TestRunner testRunner = new TestRunnerImpl();
        TestStatisticWriter statisticWriter = new TestStatisticConsoleWriter();
        testRunner.run(testClass);
        statisticWriter.write(testRunner.getTestStatistic());
    }

    public static void main(String[] args) {
        doTest(DemoTest1.class);
    }


}
