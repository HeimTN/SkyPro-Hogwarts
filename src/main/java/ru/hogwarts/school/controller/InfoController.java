package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("/getPort")
    public Integer getPort(){
        return port;
    }

    @GetMapping("/iterate-sum")
    public Integer sumIterate(){
        return Stream.iterate(1, a -> a + 1).limit(1000000).parallel().reduce(0, Integer::sum);
    }
}
