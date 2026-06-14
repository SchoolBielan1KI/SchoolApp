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
        
        // Исправленный URL
        String baseUrl = System.getenv("E2E_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://schoolapp-57rw.onrender.com"; 
        }
        
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @Test
    void testCreateAndDeleteStudent() {
        try {
            page.waitForSelector("input[name='studentName']", new Page.WaitForSelectorOptions().setTimeout(60000));

            page.fill("input[name='studentName']", "Сидоренко Максим Олегович");
            page.fill("input[name='schoolClass']", "10-Б"); // Здесь кириллическая 'А'
            page.fill("input[name='teacherName']", "Коваленко Сергій Петрович");
            page.fill("input[name='subject']", "Математика");
            page.fill("input[name='taskTheme']", "	Алгебраїчні вирази");
            page.fill("input[name='grade']", "4");
            page.fill("input[name='lessonStatus']", "Проведено");

            page.click("button[type='submit']");

            // 3. Ждем появления записи в списке
            page.waitForSelector("text=Сидоренко Максим Олегович", new Page.WaitForSelectorOptions().setTimeout(30000));

            // 4. Удаление
            page.onDialog(dialog -> dialog.accept());
            
            Locator row = page.locator("tr:has-text('Сидоренко Максим Олегович')");
            row.locator("text=Видалити").click(new Locator.ClickOptions().setForce(true));

            // 5. Ожидание удаления
            page.waitForFunction("!document.body.innerText.includes('Сидоренко Максим Олегович')", null, 
                new Page.WaitForFunctionOptions().setTimeout(30000));

            assertFalse(page.isVisible("text=Сидоренко Максим Олегович"), "Запись не удалилась!");
            
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