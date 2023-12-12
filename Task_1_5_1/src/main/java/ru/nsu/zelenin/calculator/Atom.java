package ru.nsu.zelenin.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Atom (token) class for passing to solver.
 */
public class Atom {
    private final String atom;

    public Atom(String atom) {
        this.atom = atom;
    }

    /**
     * Method parses the string.
     * it doesn't count empty strings
     * that are left after splitting by spaces
     *
     *
     * @param string - given string
     * @return arrayList of atoms
     */
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
