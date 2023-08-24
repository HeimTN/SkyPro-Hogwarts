package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Student;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String name = "TestStudent";
    private final int age = 20;

    @Test
    public void contentLoads(){
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void testPostStudent(){
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/student",student, String.class)).isNotNull();
    }

    @Test
    public void testGetStudent(){
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/1", String.class)).isNotNull();
    }


    @Test
    public void testPutStudent() throws Exception {
        JSONObject student = new JSONObject();
        student.put("id", 1);
        student.put("name", name);
        student.put("age", age);
        student.put("faculty_id", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(student.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:"+port+"/student",
                HttpMethod.PUT,
                entity,
                String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetFacultyByStudent(){
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/1", String.class)).isNotNull();
    }
    @Test
    public void testGetStudentByAge(){
        Assertions.assertThat(restTemplate.getForObject("http://localhost:"+port+"/student?age=20", String.class)).isNotNull();
    }
    @Test
    public void testGetStudentByMinMaxAge(){
        Assertions.assertThat(restTemplate.getForObject("http://localhost:"+port+"/student?minAge=19&maxAge=21", String.class)).isNotNull();
    }
    @Test
    public void testDeleteStudent(){
        Assertions.assertThat(restTemplate.exchange(
                "http://localhost:"+port+"/student/1",
                HttpMethod.DELETE,
                null,
                String.class).getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
