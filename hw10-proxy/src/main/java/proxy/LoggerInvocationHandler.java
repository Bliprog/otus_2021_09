package proxy;

import annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggerInvocationHandler implements InvocationHandler {
    private final Object defaultObject;

    LoggerInvocationHandler(Object o) {
        this.defaultObject = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        var logAnnotation = defaultObject.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(Log.class);
        if (logAnnotation != null) {
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
