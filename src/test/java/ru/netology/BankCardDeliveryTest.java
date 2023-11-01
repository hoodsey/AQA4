package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class BankCardDeliveryTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        $("[data-test-id='date'] [placeholder='Дата встречи']").sendKeys(Keys.CONTROL,"a" + Keys.DELETE);

    }


    @Test
    void ShouldSuccessForm(){
        $("[data-test-id='city'] input").setValue("Краснодар");
        String date =  generateDate(8);
        $("[data-test-id='date'] input").val(generateDate(8));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(8)));
    }
    @Test
    void lastDateTest(){
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(generateDate(-2));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] [class='input__sub']").shouldBe(ownText("Заказ на выбранную дату невозможен"));

    }
    @Test
    void CityWithOutCatalog(){
        $("[data-test-id='city'] input").setValue("Канадлакша");
        $("[data-test-id='date'] input").setValue(generateDate(-2));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] [class='input__sub']").shouldHave(ownText("Доставка в выбранный город недоступна"));
    }
    @Test
    void IncorrectName(){
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(generateDate(4));
        $("[data-test-id='name'] input").setValue("Anastasiia 123");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] [class='input__sub']").shouldHave(ownText("Имя и Фамилия указаные неверно."));
    }
    @Test
    void IncorrectPhone(){
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(generateDate(4));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("98818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] [class='input__sub']").shouldHave(ownText("Телефон указан неверно."));
    }
    @Test
    void WithOutAgree(){
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(generateDate(4));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $(".button").click();
        $("[data-test-id='agreement'] [class='checkbox__text']").shouldHave(ownText("Я соглашаюсь с условиями обработки"));
    }

    @Test
    void shouldEmptyCity() {
        $("[data-test-id='date'] input").setValue(generateDate(4));
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] [class='input__sub']").shouldHave(ownText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldEmptyName() {
        $("[data-test-id='date'] input").setValue(generateDate(4));
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='phone'] input").setValue("+79818042544");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] [class='input__sub']").shouldHave(ownText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldEmptyPhone() {
        $("[data-test-id='date'] input").setValue(generateDate(4));
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='name'] input").setValue("Кабакова Анастасия");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] [class='input__sub']").shouldHave(ownText("Поле обязательно для заполнения"));
    }

}
