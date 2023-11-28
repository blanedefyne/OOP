package ru.nsu.zelenin.reportcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;


class ReportCardTest {

    @Test
    public void simpleAverageTest() {
        Semester first = new Semester(1);
        List<String> subjectNames = Arrays.asList("Calculus", "Discrete math", "English",
                "PE", "C", "Haskell", "Digital platforms");
        List<String> professorNames = Arrays.asList("Vaskevich", "Stukachev", "Savilova",
                "Timofeev", "San Sanich", "Vlasov", "Nazarov");
        List<ExamType> examTypes = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.PASS,
                ExamType.PASS, ExamType.DIFF_PASS, ExamType.DIFF_PASS, ExamType.PASS);
        List<Subject> subjects = IntStream.range(0, subjectNames.size())
                .mapToObj(i -> new Subject(subjectNames.get(i), professorNames.get(i),
                        examTypes.get(i)))
                .toList();
        ReportCard zelenin = new ReportCard(first);
        subjects.forEach(first::addSubject);

        zelenin.addGrade(first, subjects.get(0), Grade.EXCELLENT);
        zelenin.addGrade(first, subjects.get(1), Grade.GOOD);
        zelenin.addGrade(first, subjects.get(2), Grade.PASS);
        zelenin.addGrade(first, subjects.get(3), Grade.PASS);
        zelenin.addGrade(first, subjects.get(4), Grade.GOOD);
        zelenin.addGrade(first, subjects.get(5), Grade.GOOD);
        zelenin.addGrade(first, subjects.get(6), Grade.PASS);

        assertEquals(4.25, zelenin.getAverageOfSemester(first));
    }

    @Test
    public void noScholarshipTest() {
        Semester first = new Semester(1);
        List<String> subjectNames = Arrays.asList("Calculus", "Prolog", "English",
                "PE", "Models of computation", "OOP", "Operating systems");
        List<String> professorNames = Arrays.asList("Vaskevich", "Zagorulko", "Savilova",
                "Timofeev", "Puzir", "Vlasov", "Irtegov");
        List<ExamType> examTypes = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.DIFF_PASS,
                ExamType.PASS, ExamType.DIFF_PASS, ExamType.DIFF_PASS, ExamType.EXAM);
        List<Subject> subjects = IntStream.range(0, subjectNames.size())
                .mapToObj(i -> new Subject(subjectNames.get(i), professorNames.get(i),
                        examTypes.get(i)))
                .toList();
        ReportCard bukin = new ReportCard(first);
        subjects.forEach(first::addSubject);

        bukin.addGrade(first, subjects.get(0), Grade.SATISFACTORY);
        bukin.addGrade(first, subjects.get(1), Grade.GOOD);
        bukin.addGrade(first, subjects.get(2), Grade.EXCELLENT);
        bukin.addGrade(first, subjects.get(3), Grade.PASS);
        bukin.addGrade(first, subjects.get(4), Grade.GOOD);
        bukin.addGrade(first, subjects.get(5), Grade.GOOD);
        bukin.addGrade(first, subjects.get(6), Grade.SATISFACTORY);

        assertFalse(bukin.possibilityOfBonusScholarship());
    }

    @Test
    public void totalAverageTest() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Calculus", "Prolog", "English",
                "PE");
        List<String> professorNames1 = Arrays.asList("Vaskevich", "Zagorulko", "Savilova",
                "Timofeev");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.EXAM, ExamType.EXAM,
                ExamType.DIFF_PASS, ExamType.PASS);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard bukin = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        bukin.addGrade(first, subjects1.get(0), Grade.SATISFACTORY);
        bukin.addGrade(first, subjects1.get(1), Grade.GOOD);
        bukin.addGrade(first, subjects1.get(2), Grade.EXCELLENT);
        bukin.addGrade(first, subjects1.get(3), Grade.PASS);

        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("Calculus", "Networks", "English",
                "PE", "OOP", "Data storage");
        List<String> professorNames2 = Arrays.asList("Vaskevich", "Setevih", "Savilova",
                "Timofeev", "Vlasov", "Miginskiy");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.EXAM, ExamType.EXAM,
                ExamType.DIFF_PASS, ExamType.PASS, ExamType.DIFF_PASS, ExamType.DIFF_PASS);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();
        subjects2.forEach(second::addSubject);

        bukin.addGrade(second, subjects2.get(0), Grade.GOOD);
        bukin.addGrade(second, subjects2.get(1), Grade.GOOD);
        bukin.addGrade(second, subjects2.get(2), Grade.GOOD);
        bukin.addGrade(second, subjects2.get(3), Grade.PASS);
        bukin.addGrade(second, subjects2.get(4), Grade.SATISFACTORY);
        bukin.addGrade(second, subjects2.get(5), Grade.UNSATISFACTORY);

        assertEquals(3.625, bukin.getTotalAverage());
    }

    @Test
    public void toStringTest() {
        Semester third = new Semester(3);
        List<String> subjectNames3 = Arrays.asList("Differential equation",
                "Models of computation", "English");
        List<String> professorNames3 = Arrays.asList("Vaskevich", "Puzir", "Savilova");
        List<ExamType> examTypes3 = Arrays.asList(ExamType.EXAM, ExamType.EXAM,
                ExamType.DIFF_PASS);
        List<Subject> subjects3 = IntStream.range(0, subjectNames3.size())
                .mapToObj(i -> new Subject(subjectNames3.get(i), professorNames3.get(i),
                        examTypes3.get(i)))
                .toList();
        subjects3.forEach(third::addSubject);

        ReportCard roiland = new ReportCard(third);
        roiland.addGrade(third, subjects3.get(0), Grade.GOOD);
        roiland.addGrade(third, subjects3.get(1), Grade.GOOD);
        roiland.addGrade(third, subjects3.get(2), Grade.GOOD);

        assertEquals(
                "--------------\n"
                        +
                "  SEMESTER 3\n"
                        +
                "--------------\n"
                        +
                "\n"
                        +
                "SUBJECT: Differential equation\n"
                        +
                "PROFESSOR: Vaskevich\n"
                        +
                "GRADE: GOOD\n"
                        +
                "\n"
                        +
                "SUBJECT: Models of computation\n"
                        +
                "PROFESSOR: Puzir\n"
                        +
                "GRADE: GOOD\n"
                        +
                "\n"
                        +
                "SUBJECT: English\n"
                        +
                "PROFESSOR: Savilova\n"
                        +
                "GRADE: GOOD\n"
                        +
                "\n", roiland.toString());
    }

    @Test
    public void noRedDiplomaTest() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Calculus", "Prolog", "English",
                "PE");
        List<String> professorNames1 = Arrays.asList("Vaskevich", "Zagorulko", "Savilova",
                "Timofeev");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.DIFF_PASS,
                ExamType.PASS);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard petrov = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        petrov.addGrade(first, subjects1.get(0), Grade.SATISFACTORY);
        petrov.addGrade(first, subjects1.get(1), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(2), Grade.EXCELLENT);
        petrov.addGrade(first, subjects1.get(3), Grade.PASS);

        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("Calculus", "Networks", "English",
                "PE", "OOP", "Data storage", "Qualifying work");
        List<String> professorNames2 = Arrays.asList("Vaskevich", "Setevih", "Savilova",
                "Timofeev", "Vlasov", "Miginskiy", "Somebody");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.EXAM, ExamType.EXAM,
                ExamType.DIFF_PASS, ExamType.PASS, ExamType.DIFF_PASS, ExamType.DIFF_PASS,
                ExamType.EXAM);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();
        subjects2.forEach(second::addSubject);

        petrov.addGrade(second, subjects2.get(0), Grade.GOOD);
        petrov.addGrade(second, subjects2.get(4), Grade.SATISFACTORY);
        petrov.addGrade(second, subjects2.get(5), Grade.EXCELLENT);

        assertFalse(petrov.possibilityOfRedDiploma());
    }

    @Test
    public void thereCanBeRedDiplomaTest() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Probability theory", "Operating systems",
                "English", "PE", "C++");
        List<String> professorNames1 = Arrays.asList("Rogazinskiy", "Irtegov", "Savilova",
                "Timofeev", "San Sanich");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.DIFF_PASS, ExamType.EXAM,
                ExamType.PASS, ExamType.PASS, ExamType.DIFF_PASS);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard petrov = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        petrov.addGrade(first, subjects1.get(0), Grade.EXCELLENT);
        petrov.addGrade(first, subjects1.get(1), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(2), Grade.PASS);
        petrov.addGrade(first, subjects1.get(3), Grade.PASS);
        petrov.addGrade(first, subjects1.get(4), Grade.EXCELLENT);

        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("Calculus", "Networks", "English",
                "OOP", "PE", "Data storage", "Schemotechnics");
        List<String> professorNames2 = Arrays.asList("Vaskevich", "Setevih", "Savilova",
                "Vlasov", "Timofeev", "Miginskiy", "Schemotechnic");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.PASS,
                ExamType.DIFF_PASS, ExamType.PASS, ExamType.DIFF_PASS, ExamType.EXAM);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();
        subjects2.forEach(second::addSubject);

        petrov.addGrade(second, subjects2.get(0), Grade.EXCELLENT);
        petrov.addGrade(second, subjects2.get(5), Grade.SATISFACTORY);
        petrov.changeGrade(second, subjects2.get(5), Grade.GOOD);
        petrov.addGrade(second, subjects2.get(6), Grade.EXCELLENT);

        assertTrue(petrov.possibilityOfRedDiploma());
    }

    @Test
    public void tryToChangeWrongGrade() {
        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("Discrete math", "Operating systems",
                "English", "OOP", "C");
        List<String> professorNames2 = Arrays.asList("Apanovich", "Irtegov", "Savilova",
                "Vlasov", "San Sanich");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.PASS,
                ExamType.DIFF_PASS, ExamType.DIFF_PASS);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();

        ReportCard smith = new ReportCard(second);
        subjects2.forEach(second::addSubject);

        smith.addGrade(second, subjects2.get(0), Grade.SATISFACTORY);
        smith.changeGrade(second, subjects2.get(0), Grade.PASS);
        assertEquals(Grade.SATISFACTORY, smith.getGrade(second, subjects2.get(0)));


    }

    @Test
    public void badSemesterInput() {
        Semester third = new Semester(-3);
        assertEquals(1, third.getNumber());
    }

    @Test
    public void goddamitQualifyingWork() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Maths", "Informatics", "English",
                "Linear algebra", "Algorithms");
        List<String> professorNames1 = Arrays.asList("Vaskevich", "Somebody", "Savilova",
                "Shwab", "San Sanich");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.PASS,
                ExamType.EXAM, ExamType.DIFF_PASS);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard petrov = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        petrov.addGrade(first, subjects1.get(0), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(1), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(2), Grade.PASS);
        petrov.addGrade(first, subjects1.get(3), Grade.EXCELLENT);
        petrov.addGrade(first, subjects1.get(4), Grade.EXCELLENT);

        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("PE", "Digital platforms", "English",
                "OOP", "PE", "SQL", "Haskell");
        List<String> professorNames2 = Arrays.asList("Timofeev", "Nazarov", "Savilova",
                "Vlasov", "Timofeev", "Miginskiy", "Vlasov");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.PASS, ExamType.PASS,
                ExamType.DIFF_PASS, ExamType.DIFF_PASS, ExamType.PASS, ExamType.DIFF_PASS,
                ExamType.DIFF_PASS);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();
        subjects2.forEach(second::addSubject);

        petrov.addGrade(second, subjects2.get(0), Grade.FAIL);
        petrov.changeGrade(second, subjects2.get(0), Grade.PASS);
        petrov.addGrade(second, subjects2.get(1), Grade.PASS);
        petrov.addGrade(second, subjects2.get(2), Grade.PASS);
        petrov.addGrade(second, subjects2.get(3), Grade.EXCELLENT);
        petrov.addGrade(second, subjects2.get(4), Grade.PASS);
        petrov.addGrade(second, subjects2.get(5), Grade.UNSATISFACTORY);
        petrov.changeGrade(second, subjects2.get(5), Grade.EXCELLENT);
        petrov.addGrade(second, subjects2.get(6), Grade.EXCELLENT);

        Semester eighth = new Semester(8);
        List<String> subjectNames8 = Arrays.asList("Practice", "Team Project", "English",
                "Qualifying work");
        List<String> professorNames8 = Arrays.asList("Somebody", "Vlasov", "Savilova",
                "Somebody");
        List<ExamType> examTypes8 = Arrays.asList(ExamType.DIFF_PASS, ExamType.DIFF_PASS,
                ExamType.DIFF_PASS, ExamType.EXAM);
        List<Subject> subjects8 = IntStream.range(0, subjectNames8.size())
                .mapToObj(i -> new Subject(subjectNames8.get(i), professorNames8.get(i),
                        examTypes8.get(i)))
                .toList();
        subjects8.forEach(second::addSubject);

        petrov.addGrade(eighth, subjects8.get(0), Grade.GOOD);
        petrov.addGrade(eighth, subjects8.get(1), Grade.EXCELLENT);
        petrov.addGrade(eighth, subjects8.get(2), Grade.PASS);
        petrov.addGrade(eighth, subjects8.get(3), Grade.GOOD);

        assertFalse(petrov.possibilityOfRedDiploma());
    }

    @Test
    public void tryToChangeNotExistingSemesterGrade() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Discrete math", "Haskell", "English",
                "PE", "C", "Calculus");
        List<String> professorNames1 = Arrays.asList("Stukachev", "Vlasov", "Savilova",
                "Timofeev", "San Sanich", "Vaskevich");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.EXAM, ExamType.DIFF_PASS, ExamType.PASS,
                ExamType.PASS, ExamType.DIFF_PASS, ExamType.EXAM);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard smith = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("Discrete math", "SQL", "English",
                "PE", "C", "Calculus");
        List<String> professorNames2 = Arrays.asList("Apanovich", "Miginskiy", "Savilova",
                "Timofeev", "San Sanich", "Vaskevich");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.EXAM, ExamType.DIFF_PASS,
                ExamType.DIFF_PASS, ExamType.PASS, ExamType.DIFF_PASS, ExamType.EXAM);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();
        subjects2.forEach(second::addSubject);

        assertFalse(smith.changeGrade(second, subjects2.get(4), Grade.GOOD));
    }

    @Test
    public void anotherTotalAverageTest() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Maths", "Informatics", "English",
                "Linear algebra", "Algorithms");
        List<String> professorNames1 = Arrays.asList("Vaskevich", "Somebody", "Savilova",
                "Shwab", "San Sanich");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.PASS,
                ExamType.EXAM, ExamType.DIFF_PASS);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard petrov = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        petrov.addGrade(first, subjects1.get(0), Grade.SATISFACTORY);
        petrov.addGrade(first, subjects1.get(1), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(2), Grade.PASS);
        petrov.addGrade(first, subjects1.get(3), Grade.EXCELLENT);
        petrov.addGrade(first, subjects1.get(4), Grade.EXCELLENT);

        Semester second = new Semester(2);
        List<String> subjectNames2 = Arrays.asList("PE", "Digital platforms", "English",
                "OOP", "SQL", "Haskell");
        List<String> professorNames2 = Arrays.asList("Timofeev", "Nazarov", "Savilova",
                "Vlasov", "Miginskiy", "Vlasov");
        List<ExamType> examTypes2 = Arrays.asList(ExamType.PASS, ExamType.PASS, ExamType.PASS,
                ExamType.DIFF_PASS, ExamType.DIFF_PASS, ExamType.DIFF_PASS);
        List<Subject> subjects2 = IntStream.range(0, subjectNames2.size())
                .mapToObj(i -> new Subject(subjectNames2.get(i), professorNames2.get(i),
                        examTypes2.get(i)))
                .toList();
        subjects2.forEach(second::addSubject);

        petrov.addGrade(second, subjects2.get(0), Grade.FAIL);
        petrov.changeGrade(second, subjects2.get(0), Grade.PASS);
        petrov.addGrade(second, subjects2.get(1), Grade.PASS);
        petrov.addGrade(second, subjects2.get(2), Grade.PASS);
        petrov.addGrade(second, subjects2.get(3), Grade.EXCELLENT);
        petrov.addGrade(second, subjects2.get(4), Grade.PASS);
        petrov.addGrade(second, subjects2.get(5), Grade.UNSATISFACTORY);
        petrov.changeGrade(second, subjects2.get(5), Grade.GOOD);


        Semester eighth = new Semester(8);
        List<String> subjectNames8 = Arrays.asList("Practice", "Team Project", "English",
                "Qualifying work");
        List<String> professorNames8 = Arrays.asList("Somebody", "Vlasov", "Savilova",
                "Somebody");
        List<ExamType> examTypes8 = Arrays.asList(ExamType.DIFF_PASS, ExamType.DIFF_PASS,
                ExamType.DIFF_PASS, ExamType.EXAM);
        List<Subject> subjects8 = IntStream.range(0, subjectNames8.size())
                .mapToObj(i -> new Subject(subjectNames8.get(i), professorNames8.get(i),
                        examTypes8.get(i)))
                .toList();
        subjects8.forEach(second::addSubject);

        petrov.addGrade(eighth, subjects8.get(0), Grade.GOOD);
        petrov.addGrade(eighth, subjects8.get(1), Grade.EXCELLENT);
        petrov.addGrade(eighth, subjects8.get(2), Grade.PASS);
        petrov.addGrade(eighth, subjects8.get(3), Grade.GOOD);

        assertEquals(4.333333333333333, petrov.getTotalAverage());
    }
    
    @Test
    public void anotherSemesterAverageTest() {
        Semester first = new Semester(1);
        List<String> subjectNames1 = Arrays.asList("Maths", "Informatics", "English",
                "Linear algebra", "Algorithms", "PE", "History", "Russian");
        List<String> professorNames1 = Arrays.asList("Vaskevich", "Somebody", "Savilova",
                "Shwab", "San Sanich", "Timofeev", "Oplakanskaya", "Zavorina");
        List<ExamType> examTypes1 = Arrays.asList(ExamType.EXAM, ExamType.EXAM, ExamType.PASS,
                ExamType.DIFF_PASS, ExamType.DIFF_PASS, ExamType.PASS, ExamType.DIFF_PASS,
                ExamType.DIFF_PASS);
        List<Subject> subjects1 = IntStream.range(0, subjectNames1.size())
                .mapToObj(i -> new Subject(subjectNames1.get(i), professorNames1.get(i),
                        examTypes1.get(i)))
                .toList();

        ReportCard petrov = new ReportCard(first);
        subjects1.forEach(first::addSubject);

        petrov.addGrade(first, subjects1.get(0), Grade.SATISFACTORY);
        petrov.addGrade(first, subjects1.get(1), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(2), Grade.EXCELLENT);
        petrov.addGrade(first, subjects1.get(3), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(4), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(5), Grade.PASS);
        petrov.addGrade(first, subjects1.get(6), Grade.GOOD);
        petrov.addGrade(first, subjects1.get(7), Grade.EXCELLENT);

        assertEquals(4.142857142857143, petrov.getAverageOfSemester(first));
    }
}