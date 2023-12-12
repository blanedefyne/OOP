package ru.nsu.zelenin.calculator;

import java.util.ArrayList;
import java.util.List;

public class Atom {
    private final String atom;

    public Atom(String atom) {
        this.atom = atom;
    }

    public static List<Atom> parseAtoms(String string) {
        List<Atom> res = new ArrayList<>();
        String[] spacesSplit = string.split(" ");

        for (String s : spacesSplit) {
            if (!s.isEmpty()) {
                res.add(new Atom(s));
            }
        }
        return res;
    }

    public String getAtom() {
        return atom;
    }
}
