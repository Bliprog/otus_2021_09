package ru.otus.appcontainer.creationComponents;

import java.util.List;

public interface ConfigProcessor {
    List<Class<?>> orderConfig(Class<?>[] configs);
}
