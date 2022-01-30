package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.creationComponents.ComponentProcessor;
import ru.otus.appcontainer.creationComponents.ComponentProcessorImpl;
import ru.otus.appcontainer.creationComponents.ConfigProcessor;
import ru.otus.appcontainer.creationComponents.ConfigProcessorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final ComponentProcessor componentProcessor = new ComponentProcessorImpl();
    private final ConfigProcessor configProcessor = new ConfigProcessorImpl();


    private AppComponentsContainerImpl() {
    }

    public AppComponentsContainerImpl(Class<?>... configs) {
        this();
        var orderedConfigs = configProcessor.orderConfig(configs);
        processConfig(orderedConfigs);

    }

    public AppComponentsContainerImpl(String packageName) {
        this();
        Reflections reflections = new Reflections(packageName);
        var configs = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class).toArray(new Class<?>[0]);
        var orderedConfigs = configProcessor.orderConfig(configs);
        processConfig(orderedConfigs);

    }

    private void processConfig(List<Class<?>> configClasses) {
        checkConfigClass(configClasses);
        var components = componentProcessor.createComponents(configClasses);
        this.appComponents.addAll(components.values());
        this.appComponentsByName.putAll(components);
    }

    private void checkConfigClass(List<Class<?>> configClasses) {
        for (var configClass :
                configClasses) {
            if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
                throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
            }
        }

    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var appComponentList = appComponents.stream()
                .filter(c -> c.getClass().equals(componentClass) || componentClass.isAssignableFrom(c.getClass()))
                .collect(Collectors.toList());
        if (appComponentList.size() != 1) {
            throw new RuntimeException("Component not found or found more than 1");
        }
        return (C) appComponentList.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
