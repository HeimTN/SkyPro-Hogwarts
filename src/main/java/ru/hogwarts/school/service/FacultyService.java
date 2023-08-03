package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id){
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void delFaculty(long id){
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterColorFaculty(String color){
        return facultyRepository.findByColor(color);
    }
}
