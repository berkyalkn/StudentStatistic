package dev.lpa;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Main2 {
    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python MasterClass", 50);
        Course jmc = new Course("JMC", "Java MasterClass", 100);
        Course jgames = new Course("JGame", "Creating Games in Java");

        List<Student> students = IntStream
                .rangeClosed(1, 5000)
                .mapToObj(s -> Student.getRandomStudent(jmc, pymc))
                .toList();


        //Average percentage completed for all students for just the Java MasterClass

        double totalPercent = students.stream()
                .mapToDouble(s -> s.getPercentComplete("JMC"))
                .reduce(0, Double::sum);

        double averagePercent = totalPercent / students.size();
        System.out.printf("Average Percentage Completed = %.2f%% %n", averagePercent);



        //Adding to a new course to
        // ten students who are still active and over the best percentage completed
        // then sort them according to longest enrolled

        int topPercent = (int) (1.25 * averagePercent);
        System.out.printf("Best Percentage Completed = %d%% %n", topPercent);

        Comparator<Student> longTermStudent = Comparator.comparing(Student::getYearEnrolled);

        List<Student> hardWorkers = students.stream()
                .filter(s -> s.getMonthsSinceActive("JMC") == 0)
                .filter(s -> s.getPercentComplete("JMC") >= topPercent)
                .sorted(longTermStudent)
                .limit(10)
                .toList();
        hardWorkers.forEach(s -> {
            s.addCourse(jgames);
            System.out.println(s);
        });
    }
}