package ru.bliprog.statistics;

public class TestStatisticConsoleWriter implements TestStatisticWriter {
    @Override
    public void write(TestStatistic testStatistic) {
        var failedTestsWithException = testStatistic.getFailedTestsWithException();
        var passedTests = testStatistic.getPassedTests();
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(String.format("Всего тестов: %s\n", failedTestsWithException.size() + passedTests.size()));
        outputBuilder.append(String.format("Пройдено тестов: %s\n", passedTests.size()));
        outputBuilder.append(String.format("Не пройдено тестов: %s\n", failedTestsWithException.size()));
        outputBuilder.append("Далее приведена статистика по не пройденным тестам\n");
        for (var entry : failedTestsWithException.entrySet()) {
            outputBuilder.append(String.format("Тест: %s  Исключение: %s\n", entry.getKey().getName(), entry.getValue()));
        }
        System.out.println(outputBuilder);
    }

}
