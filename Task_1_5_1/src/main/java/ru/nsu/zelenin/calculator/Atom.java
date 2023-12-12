package ru.nsu.zelenin.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Atom (token) class for passing to solver.
 */
public class Atom {
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
                if (isDouble(s)) {
                    res.add(new Number(Double.parseDouble(s)));
                } else {
                    res.add(new Operation(s));
                }
            }
        }
        return res;
    }

    /**
     * Method check if given atom is a number.
     *
     * @param str - given atom
     * @return boolean value - is it or it's not
     */
    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
