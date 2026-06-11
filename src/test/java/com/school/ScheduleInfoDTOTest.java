package com.school;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class ScheduleInfoDTOTest {
    @Test
    void testDTO() {
        LocalDate date = LocalDate.now();
        ScheduleInfoDTO dto = new ScheduleInfoDTO("Petro", "10-A", "Math", "5", "Ivanov", date, "School 1", "123", "Director");
        
        assertEquals("Petro", dto.getStudentName());
        assertEquals("10-A", dto.getClassName());
        assertEquals("Math", dto.getSubject());
        assertEquals("5", dto.getRating());
        assertEquals("Ivanov", dto.getTeacherName());
        assertEquals(date, dto.getDate());
        assertNotNull(dto.toString());
    }
}