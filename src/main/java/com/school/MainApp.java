package com.school;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class MainApp {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        try {
            mainApp.setup();
            mainApp.insertSampleData();
            mainApp.queryAndLogData();
        } catch (Exception e) {
            log.error("Помилка в MainApp: ", e);
        } finally {
            mainApp.shutdown();
        }
    }

    protected void setup() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        log.info("SessionFactory успішно створено.");
    }

    protected void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            log.info("SessionFactory успішно закрито.");
        }
    }

    protected void insertSampleData() {
        log.info("--- ПОЧАТОК ВСТАВКИ ДАНИХ ---");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // ==========================================
            // 1. ШКОЛА: Ліцей №1
            // ==========================================
            School school1 = new School();
            school1.setName("Ліцей №1");
            school1.setCity("Київ");
            school1.setStreet("вул. Шевченка");
            school1.setNumber("12");
            // ТУТ БУЛА ПОМИЛКА: замінили setPhone на setPhoneNumber
            school1.setPhoneNumber("380441234567"); 
            school1.setDirector("Бондаренко І.В.");
            session.save(school1);

            Teacher tKovalenko = createTeacher(session, "Коваленко Сергій Петрович", "Вищий", school1);
            Teacher tMelnyk = createTeacher(session, "Мельник Ольга Василівна", "Перший", school1);
            Teacher tBilyk = createTeacher(session, "Білик Ірина Сергіївна", "Молодший", school1);

            createJournalEntry(session, school1, "10-А", "Петренко Олена Іванівна", tKovalenko, "Математика", "5", "2024-09-14");
            createJournalEntry(session, school1, "10-Б", "Сидоренко Максим Олегович", tKovalenko, "Математика", "4", "2024-09-14");
            createJournalEntry(session, school1, "10-А", "Іваненко Іван Іванович", tMelnyk, "Фізика", "5", "2024-09-14");
            createJournalEntry(session, school1, "8-В", "Ткаченко Наталя Андріївна", tBilyk, "Українська мова", "5", "2024-09-16");
            createJournalEntry(session, school1, "8-А", "Лисенко Артем Володимирович", tBilyk, "Українська мова", "4", "2024-09-16");

            // ==========================================
            // 2. ШКОЛА: Гімназія №5
            // ==========================================
            School school2 = new School();
            school2.setName("Гімназія №5");
            school2.setCity("Черкаси");
            school2.setStreet("просп. Перемоги");
            school2.setNumber("33");
            // ТУТ БУЛА ПОМИЛКА: замінили setPhone на setPhoneNumber
            school2.setPhoneNumber("380442345678");
            school2.setDirector("Лавренко М.П.");
            session.save(school2);

            Teacher tShevchenko = createTeacher(session, "Шевченко Андрій Миколайович", "Другий", school2);
            Teacher tKravchuk = createTeacher(session, "Кравчук Михайло Іванович", "Вищий", school2);

            createJournalEntry(session, school2, "9-Б", "Ковальчук Анна Сергіївна", tShevchenko, "Історія", "4", "2024-09-15");
            createJournalEntry(session, school2, "9-А", "Бондаренко Дмитро Олегович", tShevchenko, "Історія", "3", "2024-09-15");
            createJournalEntry(session, school2, "10-Б", "Марченко Катерина Юріївна", tKravchuk, "Математика", "5", "2024-09-16");
            createJournalEntry(session, school2, "10-А", "Петренко Олена Іванівна", tKravchuk, "Математика", "4", "2024-09-16");

            // ==========================================
            // 3. ШКОЛА: Школа №123
            // ==========================================
            School school3 = new School();
            school3.setName("Школа №123");
            school3.setCity("Київ");
            school3.setStreet("вул. Лесі Українки");
            school3.setNumber("3");
            // ТУТ БУЛА ПОМИЛКА: замінили setPhone на setPhoneNumber
            school3.setPhoneNumber("380443456789");
            school3.setDirector("Сидоренко О.П.");
            session.save(school3);

            Teacher tPylypenko = createTeacher(session, "Пилипенко Тетяна Василівна", "Вищий", school3);

            createJournalEntry(session, school3, "11-А", "Савченко Юлія Іванівна", tPylypenko, "Хімія", "5", "2024-09-15");
            createJournalEntry(session, school3, "11-Б", "Гончарук Олексій Вікторович", tPylypenko, "Хімія", "4", "2024-09-15");
            createJournalEntry(session, school3, "11-В", "Павленко Ірина Олександрівна", tPylypenko, "Англійська мова", "5", "2024-09-17");

            tx.commit();
            log.info("--- ДАНІ УСПІШНО ВСТАВЛЕНО ---");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            log.error("Помилка при вставці даних: ", e);
        } finally {
            session.close();
        }
    }

    // --- Допоміжні методи ---

    private Teacher createTeacher(Session session, String name, String lvl, School school) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setLvl(lvl);
        teacher.setSchool(school);
        session.save(teacher);
        return teacher;
    }

    private void createJournalEntry(Session session, School school, String className, String studentName, 
                                    Teacher teacher, String subject, String rating, String dateStr) {
        
        SchoolClass sClass = new SchoolClass();
        sClass.setName(className);
        sClass.setSchool(school);
        session.save(sClass);

        Student student = new Student();
        student.setName(studentName);
        student.setSchoolClass(sClass);
        session.save(student);

        Journal journal = new Journal();
        journal.setSubject(subject);
        journal.setRating(rating);
        journal.setDate(LocalDate.parse(dateStr));
        journal.setStudent(student);
        journal.setTeacher(teacher);
        session.save(journal);
    }

    protected void queryAndLogData() {
        log.info("--- РЕЗУЛЬТАТЫ ВЫБОРКИ (ПОВНІ ДАНІ) ---");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            String hql = "SELECT new com.school.ScheduleInfoDTO(" +
                         "s.name, " +
                         "sc.name, " +
                         "j.subject, " +
                         "j.rating, " +
                         "t.name, " +
                         "j.date, " +
                         "sch.name, " +
                         "sch.phoneNumber, " +  // Тут теж використовуємо правильне поле
                         "sch.director) " +
                         "FROM Journal j " +
                         "JOIN j.student s " +
                         "JOIN s.schoolClass sc " +
                         "JOIN sc.school sch " +
                         "JOIN j.teacher t";

            Query<ScheduleInfoDTO> query = session.createQuery(hql, ScheduleInfoDTO.class);
            List<ScheduleInfoDTO> results = query.getResultList();

            tx.commit();

            if (results.isEmpty()) {
                log.warn("Вибірка не дала результатів.");
            } else {
                // Заголовок таблиці
                log.info("| Дата       | Учень                     | Клас         | Предмет         | Оц | Вчитель      | Школа      | Телефон      | Директор        |");
                log.info("---------------------------------------------------------------------------------------------------------------------------------------------");
                for (ScheduleInfoDTO dto : results) {
                    log.info(dto.toString()); 
                }
            }
            log.info("--- КІНЕЦЬ ВИБІРКИ (Знайдено записів: " + results.size() + ") ---");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            log.error("Помилка при виконанні запиту: ", e);
        } finally {
            session.close();
        }
    }
}