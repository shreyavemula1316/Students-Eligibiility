package com.students.data.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details about a Student")
public class Student {

    @ApiModelProperty(notes = "Roll number of the student", example = "S001", required = true)
    private String rollNumber;

    @ApiModelProperty(notes = "Name of the student", example = "John Doe", required = true)
    private String name;

    @ApiModelProperty(notes = "Science marks of the student", example = "85", required = true)
    private int science;

    @ApiModelProperty(notes = "Maths marks of the student", example = "89", required = true)
    private int maths;

    @ApiModelProperty(notes = "English marks of the student", example = "87", required = true)
    private int english;

    @ApiModelProperty(notes = "Computer marks of the student", example = "75", required = true)
    private int computer;

    @ApiModelProperty(notes = "Eligibility status of the student", example = "YES", allowableValues = "YES, NO")
    private String eligible;

    public Student() {
    }

    public Student(String rollNumber, String name, int science, int maths, int english, int computer, String eligible) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.science = science;
        this.maths = maths;
        this.english = english;
        this.computer = computer;
        this.eligible = eligible;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getMaths() {
        return maths;
    }

    public void setMaths(int maths) {
        this.maths = maths;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }

    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNumber='" + rollNumber + '\'' +
                ", name='" + name + '\'' +
                ", science=" + science +
                ", maths=" + maths +
                ", english=" + english +
                ", computer=" + computer +
                ", eligible='" + eligible + '\'' +
                '}';
    }
}
