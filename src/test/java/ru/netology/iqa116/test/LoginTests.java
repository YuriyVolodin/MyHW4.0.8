package ru.netology.iqa116.test;

import org.junit.jupiter.api.*;
import ru.netology.iqa116.data.DataHelper;
import ru.netology.iqa116.data.SQLHelper;
import ru.netology.iqa116.page.LoginPage1;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.iqa116.data.SQLHelper.cleanAuthCodes;
import static ru.netology.iqa116.data.SQLHelper.cleanDatabase;

public class LoginTests {
    LoginPage1 loginPage;
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999/", LoginPage1.class);
    }

    @Test
    void shouldSuccessfullyLogin() {
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);

        DataHelper.VerificationCode verificationCodeObj = SQLHelper.getVerificationCode(1);
        verificationPage.validVerify(verificationCodeObj.getCode());
    }
}
