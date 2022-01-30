package ru.otus.appcontainer.creationComponents;

import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigProcessorImpl implements ConfigProcessor {
    @Override
    public List<Class<?>> orderConfig(Class<?>[] configs) {
        List<Class<?>> result = new ArrayList<>();
        Map<Class<?>, Integer> temp = new HashMap<>();
        for (var config : configs) {
            var annotation = config.getAnnotation(AppComponentsContainerConfig.class);
            var order = annotation.order();
            temp.put(config, order);
        }
        List<Map.Entry<Class<?>, Integer>> modifiedEntry = new ArrayList<>(temp.entrySet().stream().toList());
        modifiedEntry.sort(Map.Entry.comparingByValue());
        modifiedEntry.forEach(entry -> result.add(entry.getKey()));
        return result;
    }
}
