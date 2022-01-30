package ru.bliprog.statistics;

public class TestStatisticConsoleWriter implements TestStatisticWriter {
    @Override
    public void write(TestStatistic testStatistic) {
        var failedTestsWithException = testStatistic.getFailedTestsWithException();
        var passedTests = testStatistic.getPassedTests();
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(String.format("Total tests: %s\n", failedTestsWithException.size() + passedTests.size()));
        outputBuilder.append(String.format("Passed tests: %s\n", passedTests.size()));
        outputBuilder.append(String.format("Failed Tests: %s\n", failedTestsWithException.size()));
        outputBuilder.append("Failed test statistic:\n");
        for (var entry : failedTestsWithException.entrySet()) {
            outputBuilder.append(String.format("Test name: %s  Exception: %s\n", entry.getKey().getName(), entry.getValue()));
        }
        System.out.println(outputBuilder);
    }

}
