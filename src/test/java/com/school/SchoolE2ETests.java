package com.school;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
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
            // 1. Чекаємо на завантаження форми
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

            // 3. Чекаємо на появу запису
            Locator studentRow = page.locator("tr:has-text('Ivan Ivanov')");
            studentRow.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));

            // 4. Налаштування діалогу (натискання "ОК" при видаленні)
            page.onDialog(dialog -> dialog.accept());

            // 5. Видалення конкретного рядка
            studentRow.locator("text=Видалити").click();

            // 6. Жорстке очікування: чекаємо, поки сам рядок зникне з DOM
            studentRow.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(30000));

            // 7. Фінальна перевірка
            assertFalse(studentRow.isVisible(), "Запис 'Ivan Ivanov' все ще присутній на сторінці!");
            
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