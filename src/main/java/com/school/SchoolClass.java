package com.school;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "class")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    // Связь: Много Классов принадлежат одной Школе
    @ManyToOne
    @JoinColumn(name = "school_ID")
    private School school;
    
    // Связь: Один Класс имеет много Учеников
    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students;

    // Пустой конструктор
    public SchoolClass() {
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}