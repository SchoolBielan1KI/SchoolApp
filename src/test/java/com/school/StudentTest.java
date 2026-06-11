package com.school;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    void testStudent() {
        Student s = new Student();
        s.setId(1);
        s.setName("Petro");
        SchoolClass sc = new SchoolClass();
        s.setSchoolClass(sc);
        s.setJournalEntries(new HashSet<>());

        assertEquals(1, s.getId());
        assertEquals("Petro", s.getName());
        assertEquals(sc, s.getSchoolClass());
        assertNotNull(s.getJournalEntries());
    }
}