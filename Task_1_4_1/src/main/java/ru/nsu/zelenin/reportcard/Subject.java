package ru.nsu.zelenin.reportcard;

/**
 * Subject record.
 *
 * @param name - name of subject.
 * @param professor - name of professor (teacher)
 * @param type - ExamType of exam
 */
public record Subject(String name, String professor, ExamType type) {}
