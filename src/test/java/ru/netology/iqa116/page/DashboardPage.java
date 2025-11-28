package ru.netology.iqa116.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.iqa116.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private final SelenideElement header = $("[data-test-id=dashboard]");

    public DashboardPage() {
        header.shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Личный кабинет"));
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.find(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }


    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);

        if (start < 0 || finish < 0) {
            throw new IllegalStateException("Не удалось найти баланс в тексте: " + text);
        }

        String value = text.substring(start + balanceStart.length(), finish).replaceAll("\\s", "");
        return Integer.parseInt(value);
    }

}