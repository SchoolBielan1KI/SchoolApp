package com.school;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "lvl")
    private String lvl;

    // Связь: Много Учителей принадлежат одной Школе
    @ManyToOne
    @JoinColumn(name = "school_ID")
    private School school;

    // Связь: Один Учитель имеет много Записей в журнале
    @OneToMany(mappedBy = "teacher")
    private Set<Journal> journalEntries;
    
    // Пустой конструктор
    public Teacher() {
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

    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Journal> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(Set<Journal> journalEntries) {
        this.journalEntries = journalEntries;
    }
}