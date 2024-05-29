package com.students.data.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.students.data.model.Student;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvHelper {

    public static List<Student> csvToStudents(InputStream is) {
        List<Student> students = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is)).withSkipLines(1).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                Student student = new Student(
                        line[0], // rollNumber
                        line[1], // name
                        Integer.parseInt(line[2]), // science
                        Integer.parseInt(line[3]), // maths
                        Integer.parseInt(line[4]), // english
                        Integer.parseInt(line[5]), // computer
                        "ToBeChecked".equalsIgnoreCase(line[6]) ? "YES" : "NO" // eligible
                );
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
