package com.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SchoolController.class)
public class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService service;

    // ДОДАНО: Мок репозиторію, щоб Spring не шукав реальну базу даних при запуску тестів
    @MockBean
    private SchoolRepository schoolRepository;

    @Test
    public void testIndexPage() throws Exception {
        when(service.getAllRecords()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("records"));
        
        verify(service, times(1)).getAllRecords();
    }

    @Test
    public void testShowAddForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attributeExists("school"));
    }

    @Test
    public void testAddRecord() throws Exception {
        mockMvc.perform(post("/add")
                .param("studentName", "Антон")
                .param("subject", "Хімія"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(service, times(1)).saveRecord(any(School.class));
    }

    @Test
    public void testDeleteRecord() throws Exception {
        mockMvc.perform(post("/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(service, times(1)).deleteRecord("100");
    }
}