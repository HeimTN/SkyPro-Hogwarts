package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudent(long id){
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student){
        return studentRepository.save(student);
    }

    public void delStudent(long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterAgeStudent(int age){
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentByAgeBetween(int minAge, int maxAge){
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    public Faculty getFacultyByStudent(Long id){
        return studentRepository.findById(id).get().getFaculty();
    }
}
