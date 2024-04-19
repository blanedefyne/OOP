package ru.nsu.zelenin.checker;

import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Checker {

    public static void main(String[] args) {
        try {
            GroovyShell sh = new GroovyShell();
            GroovyCodeSource src = new GroovyCodeSource(new File("src/main/java/" +
                    "ru/nsu/zelenin/checker/clone.groovy"));
            sh.run(src, new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
