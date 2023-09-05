package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student){
        logger.info("Create student {}", student);
        return studentRepository.save(student);
    }

    public Student getStudent(long id){
        logger.debug("Find student by id: {}", id);
        Student student = studentRepository.findById(id).get();
        logger.info("Get student {} by id: {}", student,id);
        return student;
    }

    public Student editStudent(Student student){
        logger.info("Edit student {}", student);
        return studentRepository.save(student);
    }

    public void delStudent(long id){
        logger.info("Delete student by id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterAgeStudent(int age){
        logger.info("Get all student by filterAge where age: {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentByAgeBetween(int minAge, int maxAge){
        logger.info("Get all student by filterAgeBetween where minAge: {} and maxAge: {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    public Faculty getFacultyByStudent(Long id){
        logger.info("Get student's faculty by id student: {}", id);
        return studentRepository.findById(id).get().getFaculty();
    }

    public Integer countAllStudent(){
        logger.info("Get count all students");
        return studentRepository.countAllStudent();
    }
    public Integer avgAgeStudent(){
        logger.info("Get average age all student");
        return studentRepository.avgAgeStudent();
    }
    public Collection<Student> last5Students(){
        logger.info("Get last 5 Students");
        return studentRepository.last5Student();
    }

    //Решил попробовать сделать фильтр не только по 'А', а по любой букве
    public Collection<Student> firstCharStudent(char firstChar){
        List<Student> students = studentRepository.findAll();
        String firstTemp = firstChar+"";
        return students.stream().parallel().filter(s -> s.getName().startsWith(firstTemp.toUpperCase())).collect(Collectors.toList());
    }

    public Integer avgAgeStudentStream(){
        return (int)Math.round(studentRepository.findAll().stream().parallel().mapToInt(Student::getAge).average().orElse(0));
    }
}
