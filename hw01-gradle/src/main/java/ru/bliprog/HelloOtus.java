package ru.bliprog;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

public class HelloOtus {
    public static void main(String[] args) {
        Multiset<String> helloMessageMultiset = LinkedHashMultiset.create();
        helloMessageMultiset.add("Hello");
        helloMessageMultiset.add(" ");
        helloMessageMultiset.add("OTUS");
        helloMessageMultiset.add("!");
        StringBuilder helloOtusBuilder = new StringBuilder();
        for (String s : helloMessageMultiset) {
            helloOtusBuilder.append(s);
        }
        System.out.println(helloOtusBuilder);
    }
}
