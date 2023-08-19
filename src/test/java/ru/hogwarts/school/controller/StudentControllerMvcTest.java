package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testPostStudent() throws Exception{
        Long id = (long)1;
        String name = "TestStudent";
        int age = 19;
        JSONObject studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        Mockito.when(studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.post("/student").content(studentObj.toString())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Long id = (long)1;
        String newName = "UpdatedStudent";
        int newAge = 20;
        JSONObject updatedStudentObj = new JSONObject();
        updatedStudentObj.put("name", newName);
        updatedStudentObj.put("age", newAge);

        Student updatedStudent = new Student();
        updatedStudent.setName(newName);
        updatedStudent.setAge(newAge);
        updatedStudent.setId(id);

        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(MockMvcRequestBuilders.put("/student").content(updatedStudentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.age").value(newAge));
    }

    @Test
    public void testGetStudent() throws Exception {
        Long id = 1L;
        String name = "TestStudent";
        int age = 19;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);

        Mockito.when(studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(studentService, Mockito.times(1)).delStudent(Mockito.eq(id));
    }

    @Test
    public void testFilterByAge() throws Exception {
        int age = 19;

        List<Student> filteredStudents = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("Student1");
        student1.setAge(age);
        filteredStudents.add(student1);

        Mockito.when(studentRepository.findByAge(Mockito.anyInt())).thenReturn(filteredStudents);

        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                        .param("age", String.valueOf(age))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Student1"))
                .andExpect(jsonPath("$[0].age").value(age));
    }

    @Test
    public void testFilterByAgeRange() throws Exception {
        int minAge = 18;
        int maxAge = 25;

        List<Student> filteredStudents = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("Student1");
        student1.setAge(20);
        filteredStudents.add(student1);

        Mockito.when(studentRepository.findByAgeBetween(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(filteredStudents);

        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                        .param("minAge", String.valueOf(minAge))
                        .param("maxAge", String.valueOf(maxAge))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Student1"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    public void testGetFacultyByStudent() throws Exception {
        long studentId = 1L;

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("TestFaculty");
        Student student = new Student();
        student.setId(studentId);
        student.setName("Test");
        student.setAge(20);
        student.setFaculty(faculty);

        Mockito.when(studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}/faculty", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()));
    }
}
