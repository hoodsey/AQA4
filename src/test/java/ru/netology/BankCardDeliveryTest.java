package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BankCardDeliveryTest {
    private static WebDriver driver;


    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown(){
        driver.quit();
        driver = null;
    }

    @Test
    void ShouldSuccessForm(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(generateDate(8));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(3)));
    }
    @Test
    void lastDateTest(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(generateDate(-2));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        //$(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        //$(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(3)));
    }
}
