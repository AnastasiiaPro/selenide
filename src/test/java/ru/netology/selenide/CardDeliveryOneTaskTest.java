package ru.netology.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryOneTaskTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldTestCardDelivery() {
        Selenide.open("http://0.0.0.0:9999/");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planDate);
        $("[data-test-id='name'] input").setValue("Прохоров Прохор");
        $("[data-test-id='phone'] input").setValue("+78111111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__title").shouldBe(Condition.text("Успешно!")).shouldBe(Condition.visible);
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planDate));
    }
}