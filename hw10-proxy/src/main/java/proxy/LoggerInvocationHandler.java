package proxy;

import annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LoggerInvocationHandler implements InvocationHandler {
    private final Object defaultObject;
    private final List<Method> methodsForLogging;

    LoggerInvocationHandler(Object o) {
        this.defaultObject = o;
        this.methodsForLogging = Arrays.stream(defaultObject.getClass().getDeclaredMethods())
                .filter(method -> method.getAnnotation(Log.class) != null)
                .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (methodsForLogging.stream()
                .filter(m -> m.getName().equals(method.getName()))
                .anyMatch(m -> Arrays.equals(m.getParameterTypes(), method.getParameterTypes()))
        ) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Class:%s, executed method:%s, params:", defaultObject.getClass().getName(), method.getName()));
            for (var arg : args) {
                sb.append(arg.toString()).append(", ");
            }
            System.out.println(sb);
        }
        return method.invoke(defaultObject, args);
    }
}
