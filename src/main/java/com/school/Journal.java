package com.school;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    // ВАЖНО: Ваша колонка `subject` - это массив. 
    // Этот код попытается прочитать его как массив строк.
    @Column(name = "subject")
    private String subject;

    // ВАЖНО: Ваша колонка `date` имеет странный тип `bit varying`.
    // Но данные в ней (2024-09-14) выглядят как ДАТА.
    // Я использую `LocalDate`. Если это вызовет ошибку, 
    // тип колонки в PostgreSQL нужно изменить на `DATE`.
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "rating")
    private String rating;

    // Связь: Много Записей в журнале принадлежат одному Ученику
    @ManyToOne
    @JoinColumn(name = "student_ID")
    private Student student;

    // Связь: Много Записей в журнале принадлежат одному Учителю
    @ManyToOne
    @JoinColumn(name = "teacher_ID")
    private Teacher teacher;

    // Пустой конструктор
    public Journal() {
    }

    // --- Геттеры и Сеттеры ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}