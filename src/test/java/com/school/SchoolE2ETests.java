package com.school;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class SchoolE2ETests {
    static Playwright playwright;
    static Browser browser;
    Page page;
    Random random = new Random();

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void createContext() {
        page = browser.newPage();
        // Используем правильный URL
        String baseUrl = System.getenv("E2E_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://schoolapp-57rw.onrender.com";
        }
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @Test
    void testCreateAndDeleteStudent() {
        // Генерируем уникальные данные для каждого прогона теста
        String uniqueId = UUID.randomUUID().toString().substring(0, 6);
        String studentName = "Студент_" + uniqueId;
        String[] classes = {"9-А", "10-Б", "11-В"};
        String[] teachers = {"Петренко П.П.", "Сидоренко Н.В.", "Коваленко С.П."};
        
        String randomClass = classes[random.nextInt(classes.length)];
        String randomTeacher = teachers[random.nextInt(teachers.length)];

        try {
            // 1. Создание
            page.waitForSelector("input[name='studentName']", new Page.WaitForSelectorOptions().setTimeout(60000));
            
            page.fill("input[name='studentName']", studentName);
            page.fill("input[name='schoolClass']", randomClass);
            page.fill("input[name='teacherName']", randomTeacher);
            page.fill("input[name='subject']", "Математика");
            page.fill("input[name='taskTheme']", "Тема_" + uniqueId);
            page.fill("input[name='grade']", "10");
            page.fill("input[name='lessonStatus']", "Виконано");

            page.click("button[type='submit']");

            // 2. Ждем появления записи
            Locator row = page.locator("tr:has-text('" + studentName + "')");
            row.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));

            // 3. ПРИМУСОВОЕ УДАЛЕНИЕ (Максимально "человеческий" подход)
            Locator deleteBtn = row.locator("text=Видалити");
            deleteBtn.scrollIntoViewIfNeeded();
            page.waitForTimeout(500); // Даем JS время отреагировать на прокрутку
            
            // Клик левой кнопкой мыши с задержкой
            deleteBtn.click(new Locator.ClickOptions().setButton(MouseButton.LEFT).setDelay(100));

            // 4. Ожидание исчезновения
            row.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(20000));

            assertFalse(row.isVisible(), "Запись '" + studentName + "' не удалилась!");
            
        } catch (Exception e) {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-error.png")));
            throw e;
        }
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }
}