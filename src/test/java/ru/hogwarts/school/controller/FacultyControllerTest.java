package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String name = "TestFaculty";
    private final String color = "TestColor";
    private final String url = "http://localhost:"+port+"/faculty";

    @Test
    public void contentLoads(){
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testPostFaculty(){
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        Assertions.assertThat(restTemplate.postForObject(url, faculty, String.class)).isNotNull();
    }
    @Test
    public void testGetFaculty(){
        Assertions.assertThat(restTemplate.getForObject(url+"/1",String.class)).isNotNull();
    }
    @Test
    public void testGetFacultyByNameOrColor(){
        Assertions.assertThat(restTemplate.getForObject(url+"?name="+name, String.class)).isNotNull();
        Assertions.assertThat(restTemplate.getForObject(url+"?color="+color, String.class)).isNotNull();
    }
    @Test
    public void testPutFaculty() throws Exception{
        JSONObject faculty = new JSONObject();
        faculty.put("id", 1);
        faculty.put("name", name);
        faculty.put("color", color);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(faculty.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }
    @Test
    public void testDeleteFaculty(){
        Assertions.assertThat(restTemplate.exchange(
                url+"/1",
                HttpMethod.DELETE,
                null,
                String.class).getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
