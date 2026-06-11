package com.school;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class JournalTest {
    @Test
    void testJournal() {
        Journal j = new Journal();
        j.setId(1);
        j.setSubject("Math");
        j.setDate(LocalDate.now());
        j.setRating("5");
        
        Student s = new Student();
        j.setStudent(s);
        
        Teacher t = new Teacher();
        j.setTeacher(t);

        assertEquals(1, j.getId());
        assertEquals("Math", j.getSubject());
        assertEquals(LocalDate.now(), j.getDate());
        assertEquals("5", j.getRating());
        assertEquals(s, j.getStudent());
        assertEquals(t, j.getTeacher());
    }
}