package com.school;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SchoolE2ETests {
    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void createContext() {
        page = browser.newPage();
        
        // 1. Спочатку намагаємось взяти URL зі змінних середовища (це найкраще для CI/GitHub Actions)
        String baseUrl = System.getenv("E2E_BASE_URL");
        
        // 2. Якщо змінної немає, беремо з системної властивості (якщо ти запускаєш локально командою mvn test -D...)
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getProperty("E2E_BASE_URL", "http://localhost:8080");
        }
        
        page.navigate(baseUrl);
    }

    @Test
    void testCreateAndDeleteStudent() {
        // Переконайся, що заголовок сторінки точно відповідає тому, що у тебе в <title>
        // Якщо тест впаде тут, подивись в браузері, який у тебе title, і впиши його сюди
        // assertTrue(page.title().contains("School")); 

        // Створення запису
        page.fill("input[name='studentName']", "Ivan Ivanov");
        page.fill("input[name='schoolClass']", "11-A");
        page.fill("input[name='teacherName']", "Petro Petrov");
        page.fill("input[name='subject']", "Math");
        page.fill("input[name='taskTheme']", "Algebra");
        page.fill("input[name='grade']", "12");
        page.fill("input[name='lessonStatus']", "Completed");

        page.click("button[type='submit']");

        // Перевірка, що запис з'явився (за замовчуванням текст на сторінці)
        assertTrue(page.isVisible("text=Ivan Ivanov"));

        // Видалення (припускаємо, що кнопка має текст "Видалити")
        // Якщо не знаходить, спробуй селектор за класом або за індексом
        page.click("text=Видалити"); 

        // Перевірка, що запис зник
        assertFalse(page.isVisible("text=Ivan Ivanov"));
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }
}