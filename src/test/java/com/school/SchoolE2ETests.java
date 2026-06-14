package com.school;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;
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
        
        String baseUrl = System.getenv("E2E_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getProperty("E2E_BASE_URL", "http://localhost:8080");
        }
        
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @Test
    void testCreateAndDeleteStudent() {
        try {
            // 1. Очікування завантаження форми
            page.waitForSelector("input[name='studentName']", new Page.WaitForSelectorOptions().setTimeout(60000));

            // 2. Заповнення форми
            page.fill("input[name='studentName']", "Ivan Ivanov");
            page.fill("input[name='schoolClass']", "11-A");
            page.fill("input[name='teacherName']", "Petro Petrov");
            page.fill("input[name='subject']", "Math");
            page.fill("input[name='taskTheme']", "Algebra");
            page.fill("input[name='grade']", "12");
            page.fill("input[name='lessonStatus']", "Completed");

            page.click("button[type='submit']");

            // 3. Очікування появи запису в списку
            page.waitForSelector("text=Ivan Ivanov", new Page.WaitForSelectorOptions().setTimeout(30000));

            // 4. Налаштування автоматичного підтвердження діалогів (якщо сайт питає "Ви впевнені?")
            page.onDialog(dialog -> dialog.accept());

            // 5. Видалення конкретного рядка
            page.locator("tr:has-text('Ivan Ivanov') >> text=Видалити").click();

            // 6. Очікування завершення видалення
            page.waitForLoadState(LoadState.NETWORKIDLE);
            
            // 7. Перевірка
            assertFalse(page.isVisible("text=Ivan Ivanov"), "Запис 'Ivan Ivanov' не був видалений!");
            
        } catch (Exception e) {
            // Скріншот для відладки в артефактах GitHub
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-error.png")));
            throw e;
        }
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }
}