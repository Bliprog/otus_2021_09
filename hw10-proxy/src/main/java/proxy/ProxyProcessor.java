package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyProcessor {

    public static <T> T getProxy(Class<T> targetInterface, Class<? extends T> targetObjectClass) {
        T targetObject;
        try {
            targetObject = targetObjectClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't find no param constructor");
        }
        InvocationHandler invocationHandler = new LoggerInvocationHandler(targetObject);
        var interfaces = new Class<?>[]{targetInterface};
        Object proxy = Proxy.newProxyInstance(targetInterface.getClassLoader(), interfaces, invocationHandler);
        return targetInterface.cast(proxy);
    }
}
