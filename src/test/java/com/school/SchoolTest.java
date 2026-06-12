package com.school;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SchoolTest {

    @Test
    public void testGettersAndSetters() {
        School school = new School();
        
        assertNull(school.getId()); // Спочатку має бути null
        
        school.setId("101");
        school.setStudentName("Олексій Петренко");
        school.setSchoolClass("11-А");
        school.setTeacherName("Ірина Коваль");
        school.setSubject("Фізика");
        school.setTaskTheme("Оптика");
        school.setGrade("5");
        school.setLessonStatus("Проведено");

        assertEquals("101", school.getId());
        assertEquals("Олексій Петренко", school.getStudentName());
        assertEquals("11-А", school.getSchoolClass());
        assertEquals("Ірина Коваль", school.getTeacherName());
        assertEquals("Фізика", school.getSubject());
        assertEquals("Оптика", school.getTaskTheme());
        assertEquals("5", school.getGrade());
        assertEquals("Проведено", school.getLessonStatus());
    }
}