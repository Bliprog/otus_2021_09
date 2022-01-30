package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        TreeMap<String, Double> result = new TreeMap<>();
        for (Measurement measurement : data) {
            if (result.containsKey(measurement.getName())) {
                double oldValue = result.get(measurement.getName());
                double newValue = measurement.getValue() + oldValue;
                result.put(measurement.getName(), newValue);
            } else {
                result.put(measurement.getName(), measurement.getValue());
            }
        }
        //группирует выходящий список по name, при этом суммирует поля value
        return result;
    }
}
