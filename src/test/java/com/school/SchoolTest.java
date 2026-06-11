package com.school;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class SchoolTest {
    @Test
    void testSchool() {
        School s = new School();
        s.setId(1);
        s.setName("School 1");
        s.setCity("Kyiv");
        s.setStreet("Main");
        s.setNumber("10");
        s.setPhoneNumber("12345");
        s.setDirector("Ivanov");
        s.setClasses(new HashSet<>());
        s.setTeachers(new HashSet<>());

        assertEquals(1, s.getId());
        assertEquals("School 1", s.getName());
        assertEquals("Kyiv", s.getCity());
        assertEquals("Main", s.getStreet());
        assertEquals("10", s.getNumber());
        assertEquals("12345", s.getPhoneNumber());
        assertEquals("Ivanov", s.getDirector());
        assertNotNull(s.getClasses());
        assertNotNull(s.getTeachers());
    }
}