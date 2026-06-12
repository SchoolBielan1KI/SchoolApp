package com.school;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lessons")
public class School {

    @Id
    private String id;
    
    private String studentName;
    private String schoolClass;
    private String teacherName;
    private String subject;
    private String taskTheme;
    private String grade;
    private String lessonStatus;

    public School() {
    }

    // --- Геттери та Сеттери ---

    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }

    public String getStudentName() { 
        return studentName; 
    }
    
    public void setStudentName(String studentName) { 
        this.studentName = studentName; 
    }

    public String getSchoolClass() { 
        return schoolClass; 
    }
    
    public void setSchoolClass(String schoolClass) { 
        this.schoolClass = schoolClass; 
    }

    public String getTeacherName() { 
        return teacherName; 
    }
    
    public void setTeacherName(String teacherName) { 
        this.teacherName = teacherName; 
    }

    public String getSubject() { 
        return subject; 
    }
    
    public void setSubject(String subject) { 
        this.subject = subject; 
    }

    public String getTaskTheme() { 
        return taskTheme; 
    }
    
    public void setTaskTheme(String taskTheme) { 
        this.taskTheme = taskTheme; 
    }

    public String getGrade() { 
        return grade; 
    }
    
    public void setGrade(String grade) { 
        this.grade = grade; 
    }

    public String getLessonStatus() { 
        return lessonStatus; 
    }
    
    public void setLessonStatus(String lessonStatus) { 
        this.lessonStatus = lessonStatus; 
    }
}