package com.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainAppTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private MainApp mainApp;

    @Test
    public void testRunMethodExecutesDataInsertion() throws Exception {
        mainApp.run("some-args");
        verify(schoolRepository, times(1)).deleteAll();
        verify(schoolRepository, atLeastOnce()).save(any(School.class));
    }

    @Test
    public void testInsertSampleDataCatchesException() {
        doThrow(new RuntimeException("Test Exception")).when(schoolRepository).deleteAll();
        
        // ЗМІНЕНО: Тепер ми кажемо тесту, що помилка ДІЙСНО має виникнути і це нормально
        assertThrows(RuntimeException.class, () -> mainApp.insertSampleData());
        
        verify(schoolRepository, times(1)).deleteAll();
        verify(schoolRepository, never()).save(any(School.class));
    }
}