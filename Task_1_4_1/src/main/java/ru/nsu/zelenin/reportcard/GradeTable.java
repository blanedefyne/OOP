package ru.nsu.zelenin.reportcard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class helper - helps not to put HashMap into another HashMap.
 */
public class GradeTable {
    private final Map<Subject, Grade> gradeTable;

    public GradeTable() {
        gradeTable = new HashMap<>();
    }

    public void putInTable(Subject subject, Grade grade) {
        gradeTable.put(subject, grade);
    }

    public Grade getGradeValue(Subject subject) {
        return gradeTable.get(subject);
    }

    public Set<Subject> getKeysSet() {
        return gradeTable.keySet();
    }

    public Collection<Grade> getValuesSet() {
        return gradeTable.values();
    }
}
