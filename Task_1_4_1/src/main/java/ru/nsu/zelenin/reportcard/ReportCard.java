package ru.nsu.zelenin.reportcard;

import java.util.*;

/**
 * Class for student's report card representation.
 */
public class ReportCard {
    /**
     * totalSum - sum of all meaning grades (not including PASSes and FAILs)
     * totalGrades - amount of these grades
     * lessThan4 - number of grades less than 4
     * grades - "main" HashMap we're mostly working with
     * finalGrades - HashMap of not-repeating subjects from all the semesters
     * (more accurately - their grades; if something continues for a few semesters
     * the last "put" will overwrite it anyway, so we get the final grade)
     */

    private double totalSum = 0;
    private int totalGrades = 0;
    private int lessThan4 = 0;
    private final Map<Semester, GradeTable> grades = new HashMap<>();
    private final Map<Subject, Grade> finalGrades = new HashMap<>();

    /**
     * Constructor.
     * @param semester - semester we're working with.
     */
    public ReportCard(Semester semester) {
        grades.put(semester, new GradeTable());
    }

    /**
     * Method that adds grades to report card.
     * firstly we check if the semester has been added
     * if not - we add it ourselves
     * then put in in grades HashMap and finalGrades HashMap
     *
     * @param semester - semester we're working with
     * @param subject - subject
     * @param grade - grade
     */
    public void addGrade(Semester semester, Subject subject, Grade grade) {
        if (!grades.containsKey(semester)) {
            grades.put(semester, new GradeTable());
        }
        grades.get(semester).putInTable(subject, grade);
        finalGrades.put(subject, grade);
        boolean isGradePassFail = grade == Grade.FAIL || grade == Grade.PASS;
        if (!isGradePassFail) {
            totalSum += grade.getValue();
            totalGrades++;
        }
        if (grade.getValue() < 4) {
            lessThan4++;
        }
    }

    /**
     * Method changes already existing grade.
     * for example student repass the exam with better mark
     * or the teacher got mad and decreased the grade.
     *
     * @param semester - semester we're working with
     * @param subject - subject
     * @param newGrade - new grade
     * @return boolean value - did we change the mark
     */
    public boolean changeGrade(Semester semester, Subject subject, Grade newGrade) {
        if (!grades.containsKey(semester)) {
            return false;
        }
        Grade prevGrade = grades
                .get(semester)
                .getGradeValue(subject);
        boolean isPrevGradePassFail = (prevGrade == Grade.PASS || prevGrade == Grade.FAIL);
        boolean isNewGradePassFail = (newGrade == Grade.PASS || newGrade == Grade.FAIL);
        if (isPrevGradePassFail != isNewGradePassFail) {
            return false;
        }
        grades.get(semester).putInTable(subject, newGrade);
        finalGrades.put(subject, newGrade);
        if (prevGrade.getValue() < 4 && newGrade.getValue() >= 4) {
            lessThan4--;
        }
        if (!isPrevGradePassFail) {
            totalSum += newGrade.getValue() - prevGrade.getValue();
        }
        return true;
    }

    /**
     * Getting the grade by semester and subject.
     *
     * @param semester - semester we're working with
     * @param mySubject - subject
     * @return Grade instance
     */
    public Grade getGrade(Semester semester, Subject mySubject) {
        return grades.get(semester).getKeysSet().stream()
                .filter(subject -> subject.equals(mySubject))
                .findFirst()
                .map(subject -> grades.get(semester).getGradeValue(subject))
                .orElse(null);
    }

    /**
     * Method calculates the GPA of a semester.
     *
     * @param semester - semester
     * @return double value
     */
    public double getAverageOfSemester(Semester semester) {
        return grades.get(semester).getValuesSet().stream()
                .filter(grade -> grade != Grade.PASS && grade != Grade.FAIL)
                .mapToInt(Grade::getValue)
                .summaryStatistics().getAverage();
    }

    /**
     * Method calculates total GPA for all existing for now semesters.
     *
     * @return double value
     */
    public double getTotalAverage() {
        return totalSum / totalGrades;
    }

    /**
     * Method calculates the possibility of getting red diploma.
     * if we got even one mark less than 4
     * then we cannot get red diploma for sure
     * also we should consider the subjects we haven't got a grade for yet
     * so we need to find all subjects except for ones with ExamType.PASS
     * (named them significantSubjects)
     * also we look through all the subjects for "Qualifying work"
     * it student got less than 5 for it, he also cannot get a red diploma
     * in other ways we just check that number of fours is not larger than 25% of all grades
     *
     * @return boolean value - is it possible or not.
     */
    public boolean possibilityOfRedDiploma() {
        if (lessThan4 > 0) {
            return false;
        }
        int significantSubjects = (int) grades.keySet().stream()
                .flatMap(sem -> sem.getSubjects().stream())
                .filter(subject -> subject.type() != ExamType.PASS)
                .count();
        for (var sem : grades.keySet()) {
            for (var subject : sem.getSubjects()) {
                if (subject.name().equals("Qualifying work")) {
                    if (grades.get(sem).getGradeValue(subject) != Grade.EXCELLENT) {
                        return false;
                    }
                }
            }
        }
        int fours = (int) finalGrades.values().stream()
                .filter(x -> x == Grade.GOOD)
                .count();
        return (fours / significantSubjects) <= 1/4;
    }

    /**
     * Method calculates the possibility of an extra scholarship.
     * in my implementation - we can get it if we only have 4 and 5 as "meaning" marks
     *
     * @return boolean value - can we get it or not
     */
    public boolean possibilityOfBonusScholarship() {
        return lessThan4 == 0;
    }

    /**
     * toString() method override.
     *
     * @return String representation of a report card
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var sem : grades.keySet()) {
            result.append("--------------\n");
            result.append("  SEMESTER ").append(sem.getNumber()).append("\n");
            result.append("--------------\n");
            for (var subject : sem.getSubjects()) {
                result.append("\nSUBJECT: ").append(subject.name()).append("\n");
                result.append("PROFESSOR: ").append(subject.professor()).append("\n");
                result.append("GRADE: ").append(grades.get(sem).getGradeValue(subject))
                        .append("\n");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
