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
            // Чекаємо поки сторінка завантажиться і поле стане доступним
            page.waitForSelector("input[name='studentName']", new Page.WaitForSelectorOptions().setTimeout(45000));

            page.fill("input[name='studentName']", "Ivan Ivanov");
            page.fill("input[name='schoolClass']", "11-A");
            page.fill("input[name='teacherName']", "Petro Petrov");
            page.fill("input[name='subject']", "Math");
            page.fill("input[name='taskTheme']", "Algebra");
            page.fill("input[name='grade']", "12");
            page.fill("input[name='lessonStatus']", "Completed");

            page.click("button[type='submit']");

            // Чекаємо появи напису в списку
            assertTrue(page.waitForSelector("text=Ivan Ivanov") != null);

            // Видалення
            page.click("text=Видалити"); 

            // Перевірка, що запис зник
            page.waitForLoadState(LoadState.NETWORKIDLE);
            assertFalse(page.isVisible("text=Ivan Ivanov"));
            
        } catch (Exception e) {
            // Робимо скріншот, якщо впало
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-error.png")));
            throw e;
        }
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }
}