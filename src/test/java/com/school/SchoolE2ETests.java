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
            // 1. Создание
            page.waitForSelector("input[name='studentName']", new Page.WaitForSelectorOptions().setTimeout(60000));
            page.fill("input[name='studentName']", "Ivan Ivanov");
            page.fill("input[name='schoolClass']", "11-A");
            page.fill("input[name='teacherName']", "Petro Petrov");
            page.fill("input[name='subject']", "Math");
            page.fill("input[name='taskTheme']", "Algebra");
            page.fill("input[name='grade']", "12");
            page.fill("input[name='lessonStatus']", "Completed");
            page.click("button[type='submit']");

            // 2. Ждем появления
            page.waitForSelector("text=Ivan Ivanov", new Page.WaitForSelectorOptions().setTimeout(30000));

            // 3. Пытаемся удалить
            // Используем force: true, чтобы нажать, даже если есть наложение
            page.locator("tr:has-text('Ivan Ivanov') >> text=Видалити").click(new Locator.ClickOptions().setForce(true));

            // 4. ЕСЛИ ЕСТЬ ПОДТВЕРЖДЕНИЕ: ищем кнопку "Так" или "Delete" в модальном окне
            // Если её нет, Playwright просто пропустит этот шаг (try-catch для этого)
            try {
                page.locator("text=Так").click(); 
            } catch (Exception ignored) { }

            // 5. Ждем, пока запись гарантированно исчезнет
            // Проверяем 5 секунд, что текст исчез
            boolean isGone = page.waitForCondition(() -> !page.isVisible("text=Ivan Ivanov"), 
                new Page.WaitForConditionOptions().setTimeout(10000));

            // 6. Финальный ассерт
            assertFalse(page.isVisible("text=Ivan Ivanov"), "Запись все еще на странице!");
            
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