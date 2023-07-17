package com.example.amigos.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void createStudent(Student student) {
        if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);
    }
    
    @Transactional
    public void updateStudent(Long id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }
        Student studentToUpdate = studentRepository.findById(id).get();

        if (student.getName() != null && student.getName().length() > 0 && !student.getName().equals(studentToUpdate.getName())) {
            studentToUpdate.setName(student.getName());
        }

        if (student.getEmail() != null && student.getEmail().length() > 0 && !studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            studentToUpdate.setEmail(student.getEmail());
        }
    }
    
}
