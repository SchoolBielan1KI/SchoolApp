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

            page.fill("input[name='studentName']", "Ivan Ivanov");
            page.fill("input[name='schoolClass']", "11-A");
            page.fill("input[name='teacherName']", "Petro Petrov");
            page.fill("input[name='subject']", "Math");
            page.fill("input[name='taskTheme']", "Algebra");
            page.fill("input[name='grade']", "12");
            page.fill("input[name='lessonStatus']", "Completed");

            page.click("button[type='submit']");

            page.waitForSelector("text=Ivan Ivanov", new Page.WaitForSelectorOptions().setTimeout(30000));

            page.onDialog(dialog -> dialog.accept());
            page.locator("tr:has-text('Ivan Ivanov') >> text=Видалити").click();

            page.waitForFunction("!document.body.innerText.includes('Ivan Ivanov')", null, 
                new Page.WaitForFunctionOptions().setTimeout(30000));

            assertFalse(page.isVisible("text=Ivan Ivanov"), "Запись 'Ivan Ivanov' не удалилась!");
            
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