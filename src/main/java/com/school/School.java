package com.school;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "city")
    private String city;

    // Ми залишаємо ТІЛЬКИ ЦЕ поле для телефону. 
    // Поле "phone" (якщо воно у вас було) ми видаляємо, щоб не було дублікатів.
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "director")
    private String director;

    @OneToMany(mappedBy = "school")
    private Set<SchoolClass> classes;

    @OneToMany(mappedBy = "school")
    private Set<Teacher> teachers;

    public School() {
    }

    // --- Геттери та Сеттери ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    // Уважно тут: геттер і сеттер працюють саме з phoneNumber
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Set<SchoolClass> getClasses() { return classes; }
    public void setClasses(Set<SchoolClass> classes) { this.classes = classes; }

    public Set<Teacher> getTeachers() { return teachers; }
    public void setTeachers(Set<Teacher> teachers) { this.teachers = teachers; }
}