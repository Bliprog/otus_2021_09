package ru.otus.appcontainer.creationComponents;

import ru.otus.appcontainer.api.AppComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentDefinitionProcessorImpl implements ComponentDefinitionProcessor {
    @Override
    public List<ComponentDefinition> createComponentDefinition(Class<?> configClass) {
        List<ComponentDefinition> result = new ArrayList<>();
        var methods = configClass.getDeclaredMethods();
        for (var method :
                methods) {
            var annotations = method.getAnnotations();
            var annotationAppComponent = Arrays.stream(annotations).
                    filter(annotation -> annotation instanceof AppComponent).
                    findFirst().orElse(null);
            if (annotationAppComponent != null) {
                var aac = (AppComponent) annotationAppComponent;
                int componentOrder = aac.order();
                String componentName = aac.name();
                var componentParameters = method.getParameterTypes();
                var componentDefinition = new ComponentDefinitionImpl(componentOrder, componentName, componentParameters, method);
                result.add(componentDefinition);
            }
        }
        return result;
    }
}
