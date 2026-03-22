package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @Test
    void shouldRegisterMeeting() {
        String planningDate = generateDate(3);

        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Самара");

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);

        $("[data-test-id='name'] input").setValue("Попихин Антон");

        $("[data-test-id='phone'] input").setValue("+79012345678");

        $("[data-test-id='agreement']").click();

        $$("button").find(Condition.exactText("Забронировать")).click();

        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}