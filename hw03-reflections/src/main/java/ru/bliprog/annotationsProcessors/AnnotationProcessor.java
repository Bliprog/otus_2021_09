package ru.bliprog.annotationsProcessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface AnnotationProcessor {
    List<Method> getMethods(Class<?> searchClass, Class<? extends Annotation> classOfAnnotation);
}
