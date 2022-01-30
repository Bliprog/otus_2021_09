package ru.otus.appcontainer.creationComponents;

import java.util.List;

public interface ComponentDefinitionProcessor {
    List<ComponentDefinition> createComponentDefinition(Class<?> configClass);
}
