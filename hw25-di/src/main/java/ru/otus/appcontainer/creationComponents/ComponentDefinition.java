package ru.otus.appcontainer.creationComponents;

import java.lang.reflect.Method;

public interface ComponentDefinition {
    int getComponentOrder();

    String getComponentName();

    Class<?>[] getComponentParametersType();

    Method getMethod();
}
