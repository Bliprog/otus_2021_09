import demo.Example;
import demo.ExampleInterface;
import proxy.ProxyProcessor;

public class Demo {
    public static void main(String[] args) {
        ExampleInterface e = ProxyProcessor.getProxy(ExampleInterface.class, Example.class);
        e.nonTestMethod("Hello");
        e.testMethod("Hello");
        Integer r = e.testMethod2(3);
    }
}
