package model;

public class Student {
    private String studentName;
    private int classNum;

    public Student(String studentName, int classNum){
        this.studentName = studentName;
        this.classNum = classNum;
    }

    public String getStudentName(){
        return this.studentName;
    }

    public int getClassNum(){
        return this.classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
