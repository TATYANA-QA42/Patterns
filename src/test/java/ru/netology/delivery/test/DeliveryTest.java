package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.generateDate;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        val daysToAddForFirstMeeting = 4;
        val firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        val daysToAddForSecondMeeting = 5;
        val secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] [placeholder='Город']").setValue(validUser.getCity());
        $("[data-test-id='date'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[data-test-id='name'] [type='text']").setValue(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(withText("Запланировать")).click();
        $$("button").find(exactText("Перепланировать")).click();
        $("[class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }


    @Test
    @DisplayName("Should successful plan and replan meeting")
    void numberPhone() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        val daysToAddForFirstMeeting = 4;
        val firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] [placeholder='Город']").setValue(validUser.getCity());
        $("[data-test-id='date'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[data-test-id='name'] [type='text']").setValue(validUser.getName());
        $("[name='phone']").setValue("+79225410");
        $("[data-test-id='agreement']").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void errorCity() {
        val validUser = DataGenerator.Registration.generateUser2("ru");
        val daysToAddForFirstMeeting = 4;
        val firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] [placeholder='Город']").setValue(validUser.getCity());
        $("[data-test-id='date'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[data-test-id='name'] [type='text']").setValue(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(withText("Запланировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
