package com.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class SchoolControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SchoolRepository repository;

    @Test
    public void testIntegrationIndexPage() throws Exception {
        School school = new School();
        school.setStudentName("Integration Test Student");
        school.setLessonStatus("Проведено");
        repository.save(school);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
                
        repository.delete(school);
    }
}