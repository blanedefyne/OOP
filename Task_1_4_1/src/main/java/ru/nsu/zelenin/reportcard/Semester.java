package ru.nsu.zelenin.reportcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representation a single semester.
 */
public class Semester {
    private final int number;
    private final List<Subject> subjects = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param number - number of semester
     */
    public Semester(int number) {
        if (number > 0) {
            this.number = number;
        } else {
            this.number = 1;
        }
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public int getNumber() {
        return number;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
