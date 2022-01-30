package ru.otus.appcontainer.creationComponents;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class ComponentDefinitionImpl implements ComponentDefinition {
    private final int order;
    private final String name;
    private final Class<?>[] parametersType;
    private final Method componentClass;

    @Override
    public int getComponentOrder() {
        return order;
    }

    @Override
    public String getComponentName() {
        return name;
    }

    @Override
    public Class<?>[] getComponentParametersType() {
        return parametersType;
    }

    @Override
    public Method getMethod() {
        return componentClass;
    }

}
