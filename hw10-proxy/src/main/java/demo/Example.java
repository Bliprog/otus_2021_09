package demo;

import annotation.Log;

public class Example implements ExampleInterface {
    public void nonTestMethod(String s) {
        System.out.println(s);
    }

    @Log
    public void testMethod(String s) {
        System.out.println(s);
    }

    @Log
    public Integer testMethod2(int s) {
        return s;
    }
}
