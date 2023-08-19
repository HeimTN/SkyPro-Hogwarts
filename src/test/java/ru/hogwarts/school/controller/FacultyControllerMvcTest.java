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
public class FacultyControllerMvcTest {
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
    private FacultyController facultyController;

    @Test
    public void testPostFaculty() throws Exception{
        Long id = (long)1;
        String name = "TestFaculty";
        String color = "TestColor";
        JSONObject facultyObj = new JSONObject();
        facultyObj.put("name", name);
        facultyObj.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        faculty.setId(id);

        Mockito.when(facultyRepository.save(Mockito.any(Faculty.class))).thenReturn(faculty);
        Mockito.when(facultyRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty").content(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Long id = (long)1;
        String newName = "UpdatedFaculty";
        String newColor = "UpdatedColor";
        JSONObject updatedFacultyObj = new JSONObject();
        updatedFacultyObj.put("name", newName);
        updatedFacultyObj.put("color", newColor);

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName(newName);
        updatedFaculty.setColor(newColor);
        updatedFaculty.setId(id);

        Mockito.when(facultyRepository.save(Mockito.any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty").content(updatedFacultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));
    }

    @Test
    public void testGetFaculty() throws Exception {
        Long id = (long)1;
        String name = "TestFaculty";
        String color = "TestColor";

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        faculty.setId(id);

        Mockito.when(facultyRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(facultyService, Mockito.times(1)).delFaculty(Mockito.eq(id));
    }


    //Понятия не имею почему этот тест падает, ответ приходит со статусом 200, но тело ответа пустое. В контролере и сервисе вроде все хорошо
    //Мок вроде тоже правильно прописан, но в теле ответа просто стоит [], как будто пустой json, хотя в контроллере прописано что если список пустой
    //то надо возвращать 404
    @Test
    public void testGetFacultyByNameOrColor() throws Exception{
        Long id = 1L;
        String name = "TestFaculty";
        String color = "TestColor";

        List<Faculty> facultyList = new ArrayList<>();
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        faculty.setId(id);
        facultyList.add(faculty);

        Mockito.when(facultyRepository.findByNameOrColorIgnoreCase(Mockito.anyString(), Mockito.anyString())).thenReturn(facultyList);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty")
                .param("color", color)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }
}
