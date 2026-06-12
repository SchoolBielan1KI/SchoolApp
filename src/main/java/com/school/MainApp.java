package com.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    @Autowired
    private SchoolRepository schoolRepository;

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        insertSampleData();
    }

    protected void insertSampleData() {
        LOG.info("--- ПОЧАТОК ВСТАВКИ ДАНИХ ДО MONGODB ---");
        
        // Очищаємо стару колекцію перед кожним запуском, щоб дані не дублювалися
        schoolRepository.deleteAll();

        try {
            // Школа Ліцей №1
            createRecord("Петренко Олена Іванівна", "10-А", "Коваленко Сергій Петрович", "Математика", "Алгебраїчні вирази", "5", "Проведено");
            createRecord("Сидоренко Максим Олегович", "10-Б", "Коваленко Сергій Петрович", "Математика", "Алгебраїчні вирази", "4", "Проведено");
            createRecord("Іваненко Іван Іванович", "10-А", "Мельник Ольга Василівна", "Фізика", "Закони Ньютона", "5", "Проведено");
            createRecord("Ткаченко Наталя Андріївна", "8-В", "Білик Ірина Сергіївна", "Українська мова", "Синтаксис речення", "5", "Проведено");
            createRecord("Лисенко Артем Володимирович", "8-А", "Білик Ірина Сергіївна", "Українська мова", "Синтаксис речення", "4", "Проведено");

            // Школа Гімназія №5
            createRecord("Ковальчук Анна Сергіївна", "9-Б", "Шевченко Андрій Миколайович", "Історія", "Гетьманщина", "4", "Проведено");
            createRecord("Бондаренко Дмитро Олегович", "9-А", "Шевченко Андрій Миколайович", "Історія", "Гетьманщина", "3", "Проведено");
            createRecord("Марченко Катерина Юріївна", "10-Б", "Kравчук Михайло Іванович", "Математика", "Тригонометрія", "5", "Проведено");
            createRecord("Петренко Олена Іванівна", "10-А", "Kравчук Михайло Іванович", "Математика", "Тригонометрія", "4", "Проведено");

            // Школа №123
            createRecord("Савченко Юлія Іванівна", "11-А", "Пилипенко Тетяна Василівна", "Хімія", "Органічні сполуки", "5", "Проведено");
            createRecord("Гончарук Олексій Вікторович", "11-Б", "Пилипенко Тетяна Василівна", "Хімія", "Органічні сполуки", "4", "Проведено");
            createRecord("Павленко Ірина Олександрівна", "11-В", "Пилипенко Тетяна Василівна", "Англійська мова", "Present Perfect", "5", "Проведено");

            LOG.info("--- ДАНІ УСПІШНО ВСТАВЛЕНО ДО MONGODB (Всього записів: " + schoolRepository.count() + ") ---");
        } catch (Exception e) {
            LOG.error("Помилка при заповненні бази даних: ", e);
        }
    }

    private void createRecord(String student, String schoolClass, String teacher, String subject, String theme, String grade, String status) {
        School record = new School();
        record.setStudentName(student);
        record.setSchoolClass(schoolClass);
        record.setTeacherName(teacher);
        record.setSubject(subject);
        record.setTaskTheme(theme);
        record.setGrade(grade);
        record.setLessonStatus(status);
        schoolRepository.save(record);
    }
}