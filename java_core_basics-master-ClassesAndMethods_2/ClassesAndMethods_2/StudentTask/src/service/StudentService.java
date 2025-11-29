package service;

import model.Student;

public class StudentService {
    int studentCount = 0;
    float rateSum = 0;
    Student studentMax;
    Student studentMin;
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;

    public void addGrade(Student student, int grade){
        studentCount++;
        rateSum += grade;

        if(max < grade){
            max = grade;
            studentMax = student;
        }else if (min > grade){
            min = grade;
            studentMin = student;
        }
    }

    public float getAverageGrade(){
        return rateSum/studentCount;
    }

    public Student getBestStudent(){
        return studentMax;
    }

    public Student getWorstStudent(){
        return studentMin;
    }


}
