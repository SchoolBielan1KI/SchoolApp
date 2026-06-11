package com.school;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class SchoolClassTest {
    @Test
    void testSchoolClass() {
        SchoolClass sc = new SchoolClass();
        sc.setId(1);
        sc.setName("10-A");
        School s = new School();
        sc.setSchool(s);
        sc.setStudents(new HashSet<>());

        assertEquals(1, sc.getId());
        assertEquals("10-A", sc.getName());
        assertEquals(s, sc.getSchool());
        assertNotNull(sc.getStudents());
    }
}