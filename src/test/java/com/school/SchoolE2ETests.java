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
        // Беремо URL з аргументів командного рядка (п. 5.4)
        String baseUrl = System.getProperty("E2E_BASE_URL", "http://localhost:8080");
        page.navigate(baseUrl);
    }

    @Test
    void testCreateAndDeleteStudent() {
        // 1. Відкриття та перевірка заголовка
        assertEquals("School App", page.title()); // Заміни на реальний тайтл твого сайту

        // 2. Створення запису (заповнення форм)
        page.fill("input[name='studentName']", "Ivan Ivanov");
        page.fill("input[name='schoolClass']", "11-A");
        page.fill("input[name='teacherName']", "Petro Petrov");
        page.fill("input[name='subject']", "Math");
        page.fill("input[name='taskTheme']", "Algebra");
        page.fill("input[name='grade']", "12");
        page.fill("input[name='lessonStatus']", "Completed");

        page.click("button[type='submit']");

        // 3. Перевірка відображення (припускаємо, що після додавання ми на сторінці списку)
        assertTrue(page.isVisible("text=Ivan Ivanov"));

        // 4. Видалення (тут тобі треба знайти селектор для кнопки видалення твого запису)
        // Наприклад, кнопка видалення поруч із записом "Ivan Ivanov":
        page.click("tr:has-text('Ivan Ivanov') >> text=Видалити"); 

        // Перевірка, що запис зник
        assertFalse(page.isVisible("text=Ivan Ivanov"));
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }
}