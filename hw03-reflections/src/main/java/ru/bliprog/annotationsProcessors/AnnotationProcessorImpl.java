package ru.bliprog.annotationsProcessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AnnotationProcessorImpl implements AnnotationProcessor {

    private List<Method> searchAnnotatedMethods(Class<?> searchClass, Class<? extends Annotation> classOfAnnotation) {
        return Arrays.stream(searchClass.getMethods())
                .filter(m -> m.isAnnotationPresent(classOfAnnotation))
                .collect(Collectors.toList());

    }

    @Override
    public List<Method> getMethods(Class<?> searchClass, Class<? extends Annotation> classOfAnnotation) {
        return searchAnnotatedMethods(searchClass, classOfAnnotation);
    }

}
