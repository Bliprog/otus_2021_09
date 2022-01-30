package ru.otus.appcontainer.creationComponents;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class ComponentProcessorImpl implements ComponentProcessor {
    private final ComponentDefinitionProcessor componentDefinitionProcessor = new ComponentDefinitionProcessorImpl();
    private final Map<String, Object> namedComponent = new HashMap<>();

    @Override
    @SneakyThrows
    public Map<String, Object> createComponents(List<Class<?>> configs) {
        for (var config :
                configs) {
            List<ComponentDefinition> componentDefinitions = componentDefinitionProcessor.createComponentDefinition(config);
            Object configInstance = config.getDeclaredConstructor().newInstance();
            componentDefinitions.sort(Comparator.comparingInt(ComponentDefinition::getComponentOrder));
            for (var componentDef :
                    componentDefinitions) {
                if (componentDef.getComponentParametersType().length == 0) {
                    namedComponent.put(componentDef.getComponentName(), componentDef.getMethod().invoke(configInstance));
                } else {
                    List<Object> listOfParametrs = new ArrayList<>();
                    for (var parametrType :
                            componentDef.getComponentParametersType()) {
                        listOfParametrs.addAll(namedComponent.
                                values()
                                .stream()
                                .filter(value -> value.getClass().equals(parametrType)
                                        || parametrType.isAssignableFrom(value.getClass()))
                                .collect(Collectors.toList()));
                    }
                    Object obj = componentDef.getMethod().invoke(configInstance, listOfParametrs.toArray(new Object[0]));
                    namedComponent.put(componentDef.getComponentName(), obj);
                }
            }
        }
        return namedComponent;
    }


}
