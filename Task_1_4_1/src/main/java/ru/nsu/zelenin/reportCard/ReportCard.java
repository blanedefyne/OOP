package ru.nsu.zelenin.reportCard;

import java.util.*;


public class ReportCard {
    private double totalSum = 0;
    private int totalGrades = 0;
    private int lessThan4 = 0;
    private final Map<Semester, GradeTable> grades = new HashMap<>();
    private final Map<Subject, Grade> finalGrades = new HashMap<>();

    public ReportCard(Semester semester) {
        grades.put(semester, new GradeTable());
    }

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

    public Grade getGrade(Semester semester, Subject mySubject) {
        return grades.get(semester).getKeysSet().stream()
                .filter(subject -> subject.equals(mySubject))
                .findFirst()
                .map(subject -> grades.get(semester).getGradeValue(subject))
                .orElse(null);
    }

    public double getAverageOfSemester(Semester semester) {
        return grades.get(semester).getValuesSet().stream()
                .filter(grade -> grade != Grade.PASS && grade != Grade.FAIL)
                .mapToInt(Grade::getValue)
                .summaryStatistics().getAverage();
    }

    public double getTotalAverage() {
        return totalSum / totalGrades;
    }

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

    public boolean possibilityOfBonusScholarship() {
        return lessThan4 == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var sem : grades.keySet()) {
            result.append("--------------\n");
            result.append("  SEMESTER " + sem.getNumber() + "\n");
            result.append("--------------\n");
            for (var subject : sem.getSubjects()) {
                String subj = "\nSUBJECT: " + subject.name() + "\n";
                result.append(subj);
                String prof = "PROFESSOR: " + subject.professor() + "\n";
                result.append(prof);
                String grd = "GRADE: " + grades.get(sem).getGradeValue(subject) + "\n";
                result.append(grd);
            }
            result.append("\n");
        }

        return result.toString();
    }
}
