package dev.lpa;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python MasterClass");
        Course jmc = new Course("JMC", "Java MasterClass");

        Student[] students = new Student[1000];
        Arrays.setAll(students, i -> Student.getRandomStudent(pymc, jmc));


        //How many male students are in the group ?

        var maleStudents = Arrays.stream(students)
                .filter(s -> s.getGender().equals("M"));

        System.out.println("# of Male Students = " + maleStudents.count());


        //How many male and female students are in the group ?

        for (String gender : List.of("M", "F", "U")){
            var myStudents = Arrays.stream(students)
                    .filter(s -> s.getGender().equals(gender));
            System.out.println("# of " + gender + " students " + myStudents.count());
        }



        //How many students fall into the three age ranges
        // - Less than age 30
        // - Between 30 and 60
        // - Over 60

        List<Predicate<Student>> list = List.of(
                (s) -> s.getAge() < 30,
                (Student s) -> s.getAge() >= 30 && s.getAge() < 60
        );

        long total = 0;
        for (int i = 0; i < list.size(); i++){
            var myStudents = Arrays.stream(students).filter(list.get(i));
            long count = myStudents.count();
            total += count;
            System.out.printf("# of students (%s) = %d%n",
                    i == 0 ? " < 30" : " >= 30 & < 60", count);
        }
        System.out.println("# of students >= 60 = " + (students.length - total));



        //Statistics of students enrollment age

        var ageStream = Arrays.stream(students)
                .mapToInt(Student::getAgeEnrolled);
        System.out.println("Stats for Enrollment Age = " + ageStream.summaryStatistics());



        //Statistics of students current age

        var currentAgeStream = Arrays.stream(students)
                .mapToInt(Student::getAge);
        System.out.println("Stats for Current Age = " + currentAgeStream.summaryStatistics());



        //What countries are the students from ?
        Arrays.stream(students)
                .map(Student::getCountryCode)
                .distinct()
                .sorted()
                .forEach(s -> System.out.print(s + " "));

        System.out.println();



        //Are there any active students that have been enrolled for more than 7 years ?

        boolean longTerm = Arrays.stream(students)
                .anyMatch(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12));
        System.out.println("LongTerm Students ? " + longTerm);



        //How many long term students that have been enrolled for more than 7 years ?

        long longTermCount = Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .count();
        System.out.println("How Many LongTerm Students ? " + longTermCount);



        //First five of long term students' information with programming experience ?

        Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> s.hasProgrammingExperience())
                .limit(5)
                .forEach(System.out::println);

        var longTimeLearners = Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> s.hasProgrammingExperience())
                .limit(5)
                .toArray(Student[]::new);

        var learners = Arrays.stream(students)
                .filter(s -> (s.getAge() - s.getAgeEnrolled() >= 7) &&
                        (s.getMonthsSinceActive() < 12))
                .filter(s -> s.hasProgrammingExperience())
                .limit(5)
                .collect(Collectors.toList());

        Collections.shuffle(learners);
    }
}