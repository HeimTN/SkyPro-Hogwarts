package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private long idCheck = 0;

    public Faculty addFaculty(Faculty faculty){
        faculty.setId(++idCheck);
        facultyMap.put(idCheck, faculty);
        return faculty;
    }

    public Faculty getFaculty(long id){
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty){
        if(facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty delFaculty(long id){
        return facultyMap.remove(id);
    }

    public Collection<Faculty> filterColorFaculty(String color){
        return facultyMap.values().stream().filter(faculty -> faculty.getColor().equals(color)).collect(Collectors.toList());
    }
}
