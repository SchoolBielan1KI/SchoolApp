package com.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {

    @Mock
    private SchoolRepository repository;

    @InjectMocks
    private SchoolService service;

    @Test
    public void testGetAllRecords() {
        School school = new School();
        school.setStudentName("Марія");
        
        when(repository.findAll()).thenReturn(Arrays.asList(school));
        
        List<School> result = service.getAllRecords();
        assertEquals(1, result.size());
        assertEquals("Марія", result.get(0).getStudentName());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSaveRecord() {
        School school = new School();
        service.saveRecord(school);
        verify(repository, times(1)).save(school);
    }

    @Test
    public void testDeleteRecord() {
        String id = "some-id-123";
        service.deleteRecord(id);
        verify(repository, times(1)).deleteById(id);
    }
}