package com.school;

import java.time.LocalDate;

public class ScheduleInfoDTO {
    private String studentName;
    private String className;
    private String subject;
    private String rating;
    private String teacherName;
    private LocalDate date;       // Нове поле: Дата
    private String schoolName;    // Нове поле: Назва школи
    private String schoolPhone;   // Нове поле: Телефон
    private String director;      // Нове поле: Директор

    // Оновлений конструктор, який приймає всі ці дані
    public ScheduleInfoDTO(String studentName, String className, String subject, 
                           String rating, String teacherName, LocalDate date, 
                           String schoolName, String schoolPhone, String director) {
        this.studentName = studentName;
        this.className = className;
        this.subject = subject;
        this.rating = rating;
        this.teacherName = teacherName;
        this.date = date;
        this.schoolName = schoolName;
        this.schoolPhone = schoolPhone;
        this.director = director;
    }

    // Гарний вивід у консоль
    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-25s | %-12s | %-15s | %-2s | %-12s | %-10s | %-12s | %-15s |",
            date, studentName, className, subject, rating, teacherName, schoolName, schoolPhone, director
        );
        // Або простіший варіант, якщо форматування поїде:
        // return "Дата: " + date + ", Учень: " + studentName + ", Предмет: " + subject + 
        //        ", Оцінка: " + rating + ", Школа: " + schoolName + ", Тел: " + schoolPhone;
    }
    
    // Геттери (за бажанням, для Hibernate вони не критичні в конструкторі, але краще мати)
    public String getStudentName() { return studentName; }
    public String getClassName() { return className; }
    public String getSubject() { return subject; }
    public String getRating() { return rating; }
    public String getTeacherName() { return teacherName; }
    public LocalDate getDate() { return date; }
}