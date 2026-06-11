package com.school;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {
    @Test
    void testTeacher() {
        Teacher t = new Teacher();
        t.setId(1);
        t.setName("Petrenko");
        t.setLvl("High");
        School s = new School();
        t.setSchool(s);
        t.setJournalEntries(new HashSet<>());

        assertEquals(1, t.getId());
        assertEquals("Petrenko", t.getName());
        assertEquals("High", t.getLvl());
        assertEquals(s, t.getSchool());
        assertNotNull(t.getJournalEntries());
    }
}