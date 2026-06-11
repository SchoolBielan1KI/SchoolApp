package com.school;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    // Связь: Много Учеников принадлежат одному Классу
    @ManyToOne
    @JoinColumn(name = "class_ID")
    private SchoolClass schoolClass;

    // Связь: Один Ученик имеет много Записей в журнале
    @OneToMany(mappedBy = "student")
    private Set<Journal> journalEntries;
    
    // Пустой конструктор
    public Student() {
    }

    // --- Геттеры и Сеттеры ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Set<Journal> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(Set<Journal> journalEntries) {
        this.journalEntries = journalEntries;
    }
}