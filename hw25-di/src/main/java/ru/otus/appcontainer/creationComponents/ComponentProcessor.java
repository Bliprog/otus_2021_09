package ru.otus.appcontainer.creationComponents;

import java.util.List;
import java.util.Map;

public interface ComponentProcessor {
    Map<String, Object> createComponents(List<Class<?>> config);

}
