package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private long idCheck = 0;

    public Student addStudent(Student student){
        student.setId(++idCheck);
        students.put(idCheck, student);
        return student;
    }

    public Student getStudent(long id){
        return students.get(id);
    }

    public Student editStudent(Student student){
        if(students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student delStudent(long id){
        return students.remove(id);
    }

    public Collection<Student> filterAgeStudent(int age){
        return students.values().stream().filter(s -> s.getAge() == age).collect(Collectors.toList());
    }
}
