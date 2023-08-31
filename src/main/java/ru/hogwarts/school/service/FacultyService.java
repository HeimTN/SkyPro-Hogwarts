package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    public FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }


    public Faculty addFaculty(Faculty faculty){
        logger.info("Create Faculty {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id){
        logger.debug("Find faculty by id: {}", id);
        Faculty faculty = facultyRepository.findById(id).get();
        logger.info("Get faculty {} by id {}", faculty, id);
        return faculty;
    }

    public Faculty editFaculty(Faculty faculty){
        logger.info("Edit faculty {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void delFaculty(long id){
        logger.info("Delete faculty by id: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterColorFaculty(String color){
        logger.info("Get all Faculty by color: {}", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getFacultyByNameOrColor(String name, String color){
        logger.info("Get Faculty by name: {}, or color: {}",name,color);
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getAllStudentInFaculty(Long id){
        logger.info("Get all students in faculty by id: {}", id);
        return facultyRepository.findById(id).get().getStudents();
    }
}
