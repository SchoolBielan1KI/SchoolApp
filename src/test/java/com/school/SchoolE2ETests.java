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
    BrowserContext context;
    Page page;
    Random random = new Random();

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void createContext() {
        // 1. Фиксируем десктопный размер экрана, чтобы верстка не ломалась в мобильную
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = context.newPage();
        
        String baseUrl = System.getenv("E2E_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://schoolapp-57rw.onrender.com";
        }
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @Test
    void testCreateAndDeleteStudent() {
        // Генерируем полностью уникальные данные для изоляции теста
        String uniqueId = UUID.randomUUID().toString().substring(0, 6);
        String studentName = "АвтоТест_" + uniqueId;
        
        String[] classes = {"9-А", "10-Б", "11-В"};
        String[] teachers = {"Петренко П.П.", "Сидоренко Н.В.", "Коваленко С.П."};
        String randomClass = classes[random.nextInt(classes.length)];
        String randomTeacher = teachers[random.nextInt(teachers.length)];

        try {
            // 1. Ожидание и заполнение формы полями на украинском языке
            page.waitForSelector("input[name='studentName']", new Page.WaitForSelectorOptions().setTimeout(60000));
            
            page.fill("input[name='studentName']", studentName);
            page.fill("input[name='schoolClass']", randomClass);
            page.fill("input[name='teacherName']", randomTeacher);
            page.fill("input[name='subject']", "Математика");
            page.fill("input[name='taskTheme']", "Тема_" + uniqueId);
            page.fill("input[name='grade']", "10");
            page.fill("input[name='lessonStatus']", "Виконано");

            // Отправляем форму и ждем, пока утихнут сетевые запросы
            page.click("button[type='submit']");
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // 2. Убеждаемся, что строка физически отрендерилась в таблице
            Locator row = page.locator("tr:has-text('" + studentName + "')");
            row.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));

            // 3. НАДЕЖНОЕ УДАЛЕНИЕ
            // Шаг А: Наводим курсор на строку (активирует JS/CSS hover эффекты)
            row.hover();
            page.waitForTimeout(300);

            // Шаг Б: Находим кнопку удаления внутри этой строки и скроллим к ней
            Locator deleteBtn = row.locator("text=Видалити");
            deleteBtn.scrollIntoViewIfNeeded();
            page.waitForTimeout(300);
            
            // Шаг В: Кликаем левой кнопкой мыши с задержкой (имитация реального нажатия)
            deleteBtn.click(new Locator.ClickOptions().setButton(MouseButton.LEFT).setDelay(150));
            
            // Шаг Г: Ждем завершения сетевых запросов после удаления
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(1000); 

            // 4. Проверяем результат (с подстраховкой в виде перезагрузки)
            if (page.isVisible("text=" + studentName)) {
                page.reload();
                page.waitForLoadState(LoadState.NETWORKIDLE);
            }

            assertFalse(page.isVisible("text=" + studentName), "Запись '" + studentName + "' осталась в базе данных после удаления!");
            
        } catch (Exception e) {
            // Делаем скриншот всей страницы в высоком разрешении при ошибке
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("test-error.png")).setFullPage(true));
            throw e;
        }
    }

    @AfterEach
    void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    static void tearDown() {
        if (playwright != null) {
            playwright.close();
        }
    }
}