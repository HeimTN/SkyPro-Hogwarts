package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id){
        Faculty faculty = facultyService.getFaculty(id);
        if(faculty == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getAllStudentInFaculty(@PathVariable long id){
        Collection<Student> result = facultyService.getAllStudentInFaculty(id);
        if(result == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> filterColorFaculty(@RequestParam(required = false) String color,
                                                                  @RequestParam(required = false) String name){
        Collection<Faculty> result = facultyService.getFacultyByNameOrColor(name, color);
        if(result == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public Faculty addFaculty(Faculty faculty){
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(Faculty faculty){
        Faculty faculty1 = facultyService.editFaculty(faculty);
        if(faculty1 == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delFaculty(@PathVariable long id){
        facultyService.delFaculty(id);
        return ResponseEntity.ok().build();
    }
}
