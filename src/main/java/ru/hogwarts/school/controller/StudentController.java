package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id){
        Student student = studentService.getStudent(id);
        if(student == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable long id){
        Faculty result = studentService.getFacultyByStudent(id);
        if(result == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> filterAgeStudent(@RequestParam(required = false) Integer age,
                                                                @RequestParam(required = false) Integer minAge,
                                                                @RequestParam(required = false) Integer maxAge){
        Collection<Student> result = null;
        if(age != null){
            result = studentService.filterAgeStudent(age);
        } else
        if(minAge != null && maxAge != null){
            result = studentService.getStudentByAgeBetween(minAge, maxAge);
        }
        if(result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student){
        Student student1 = studentService.editStudent(student);
        if(student1 == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delStudent(@PathVariable long id){
        studentService.delStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public Integer countAllStudents(){return studentService.countAllStudent();}
    @GetMapping("/avg-age")
    public Integer avgAgeStudent(){return studentService.avgAgeStudent();}
    @GetMapping("/last")
    public Collection<Student> last5Student(){return studentService.last5Students();}
}
