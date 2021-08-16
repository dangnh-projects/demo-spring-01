package com.example.demo.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {

        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Email has been taken!");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {

        Boolean studentExists = studentRepository.existsById(studentId);

        if(!studentExists) {
            throw new IllegalStateException("The student with id " + studentId + " does not exist!");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, String dob) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("The student with id " + studentId + " does not exist!"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if(studentOptional.isPresent()) {
                throw new IllegalStateException("Email has been taken!");
            }

            student.setEmail(email);
        }

        if(dob != null && dob.length() > 0) {
            LocalDate dobValue;
            try {
                dobValue = LocalDate.parse(dob);
            } catch (Exception e) {
                throw new IllegalStateException("Invalid date " + dob + "!");
            }
            if(dobValue.compareTo(student.getDob()) != 0) {
                student.setDob(dobValue);
            }
        }
    }
}
