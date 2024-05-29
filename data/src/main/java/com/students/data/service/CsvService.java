package com.students.data.service;

import com.opencsv.CSVWriter;
import com.students.data.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvService {

    private static final String CSV_FILE_PATH = "students.csv";
    private static final String[] CSV_HEADER = {"rollNumber", "name", "science", "maths", "english", "computer", "eligible"};

    private int scienceCriteria = 85;
    private int mathsCriteria = 89;
    private int englishCriteria = 87;
    private int computerCriteria = 75;

    public void insertStudent(Student student) throws IOException {
        student.setEligible(checkEligibility(student));
        writeToCsv(new Student[]{student});
    }

    public void insertStudents(List<Student> students) throws IOException {
        students.forEach(student -> student.setEligible(checkEligibility(student)));
        writeToCsv(students.toArray(new Student[0]));
    }

    public List<Student> getAllStudents() throws IOException {
        return new CsvHelper().csvToStudents(new FileInputStream(CSV_FILE_PATH));
    }

    public Student getStudentDetails(String rollNumber) throws IOException {
        List<Student> students = new CsvHelper().csvToStudents(new FileInputStream(CSV_FILE_PATH));
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                student.setEligible(checkEligibility(student));
                return student;
            }
        }
        return null; // Student not found
    }

    public void updateCriteria(int science, int maths, int english, int computer) {
        this.scienceCriteria = science;
        this.mathsCriteria = maths;
        this.englishCriteria = english;
        this.computerCriteria = computer;

        // Recalculate eligibility based on new criteria
        //recalculateEligibility();
    }

    public int getScienceCriteria() {
        return scienceCriteria;
    }

    public int getMathsCriteria() {
        return mathsCriteria;
    }

    public int getEnglishCriteria() {
        return englishCriteria;
    }

    public int getComputerCriteria() {
        return computerCriteria;
    }

    public void processCsvFile(MultipartFile file) throws IOException {
        List<Student> students = new CsvHelper().csvToStudents(file.getInputStream());
        insertStudents(students);
    }

    public void processCsvFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            processCsvFile(file);
        }
    }

    private String checkEligibility(Student student) {
        return (student.getScience() >= scienceCriteria &&
                student.getMaths() >= mathsCriteria &&
                student.getEnglish() >= englishCriteria &&
                student.getComputer() >= computerCriteria) ? "YES" : "NO";
    }

    private void writeToCsv(Student[] students) throws IOException {
        boolean fileExists = Files.exists(Paths.get(CSV_FILE_PATH));
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH, true))) {
            if (!fileExists) {
                writer.writeNext(CSV_HEADER);
            }
            for (Student student : students) {
                String[] data = {
                        student.getRollNumber(),
                        student.getName(),
                        String.valueOf(student.getScience()),
                        String.valueOf(student.getMaths()),
                        String.valueOf(student.getEnglish()),
                        String.valueOf(student.getComputer()),
                        student.getEligible()
                };
                writer.writeNext(data);
            }
        }
    }
}
