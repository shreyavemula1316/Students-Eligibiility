package com.students.data.controller;

import com.students.data.model.Student;
import com.students.data.service.CsvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Api(tags = "Student Management API")
public class StudentController {

    private final CsvService csvService;

    @Autowired
    public StudentController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new student")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            csvService.insertStudent(student);
            return ResponseEntity.status(HttpStatus.OK).body("Student added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student: " + e.getMessage());
        }
    }

    @PostMapping("/addMultiple")
    @ApiOperation(value = "Add multiple students")
    public ResponseEntity<String> addStudents(@RequestBody List<Student> students) {
        try {
            csvService.insertStudents(students);
            return ResponseEntity.status(HttpStatus.OK).body("Students added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add students: " + e.getMessage());
        }
    }

    @GetMapping("/{rollNumber}")
    @ApiOperation(value = "Get student details by roll number")
    public ResponseEntity<?> getStudentDetails(@ApiParam(value = "Roll number of the student") @PathVariable String rollNumber) {
        try {
            Student student = csvService.getStudentDetails(rollNumber);
            if (student == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found for roll number: " + rollNumber);
            }
            return ResponseEntity.status(HttpStatus.OK).body(student);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve student details: " + e.getMessage());
        }
    }

    @PutMapping("/criteria")
    @ApiOperation(value = "Update eligibility criteria")
    public ResponseEntity<String> updateCriteria(
            @ApiParam(value = "Science criteria") @RequestParam int science,
            @ApiParam(value = "Maths criteria") @RequestParam int maths,
            @ApiParam(value = "English criteria") @RequestParam int english,
            @ApiParam(value = "Computer criteria") @RequestParam int computer) {
        try {
            csvService.updateCriteria(science, maths, english, computer);
            return ResponseEntity.status(HttpStatus.OK).body("Eligibility criteria updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update eligibility criteria: " + e.getMessage());
        }
    }

    @GetMapping("/criteria")
    @ApiOperation(value = "Get current eligibility criteria")
    public ResponseEntity<String> getCriteria() {
        try {
            String criteria = String.format("Science: %d, Maths: %d, English: %d, Computer: %d",
                    csvService.getScienceCriteria(), csvService.getMathsCriteria(),
                    csvService.getEnglishCriteria(), csvService.getComputerCriteria());
            return ResponseEntity.status(HttpStatus.OK).body(criteria);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve eligibility criteria: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    @ApiOperation(value = "Upload a single CSV file", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@ApiParam(value = "CSV file to upload") @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file.");
        }

        try {
            csvService.processCsvFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded and processed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadMultiple")
    @ApiOperation(value = "Upload multiple CSV files", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadMultipleFiles(@ApiParam(value = "CSV files to upload") @RequestParam("files") MultipartFile[] files) {
        if (files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload one or more CSV files.");
        }

        try {
            csvService.processCsvFiles(List.of(files));
            return ResponseEntity.status(HttpStatus.OK).body("Files uploaded and processed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files: " + e.getMessage());
        }
    }
}
