package ru.bliprog.demoTests;

import ru.bliprog.annotations.After;
import ru.bliprog.annotations.Before;
import ru.bliprog.annotations.Test;

public class DemoTest1 {
    private int tralala;
    private String ololo;

    @Before
    public void setTralalaBefore() {
        tralala = 1;
    }

    @Before
    public void setOloloBefore() {
        ololo = "Privet";
    }

    @Test
    public void goodTest() {
        if (tralala != 1 || !ololo.equals("Privet")) {
            throw new RuntimeException("Framework don't work :(");
        }
        tralala = 2;
        ololo = "Poka";
    }

    @Test
    public void goodTest2() {
        int i = 1, j = 2;
        int k = j - i;
        tralala = 2;
        ololo = "Poka";
    }

    @Test
    public void badTest() {
        if (tralala == 1 || ololo.equals("Privet")) {
            throw new RuntimeException("Framework work :)");
        }
    }

    @Test
    public void badTest2() {
        int i = 6 / 0;
    }

    @After
    public void setTralalaAfter() {
        tralala = 0;
    }

    @After
    public void setOloloAfter() {
        ololo = null;
    }
}
