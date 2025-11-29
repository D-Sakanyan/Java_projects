package ru.skillbox;

import model.Student;
import service.StudentService;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        studentService.addGrade(new Student("Anna", 4), 58);
        studentService.addGrade(new Student("Katya", 6), 27);
        studentService.addGrade(new Student("Sergey", 8), 98);
        studentService.addGrade(new Student("Nikita", 2), 11);
        studentService.addGrade(new Student("Ivan", 9), 34);

        Student best = studentService.getBestStudent();
        Student worst = studentService.getWorstStudent();
        System.out.println("Best student is " + best.getStudentName() + " from " + best.getClassNum() );
        System.out.println("Worst student is " + worst.getStudentName() + " from " + worst.getClassNum());
        System.out.println("Average grade is " + studentService.getAverageGrade());
    }
}
