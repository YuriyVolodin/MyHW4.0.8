package ru.netology.iqa116.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode(int userId) {
        String sql = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1";
        try (Connection conn = getConnection()) {
            return QUERY_RUNNER.query(conn, sql, new BeanHandler<>(DataHelper.VerificationCode.class), userId);
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (Connection conn = getConnection()) {
            QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes");
            QUERY_RUNNER.execute(conn, "DELETE FROM card_transactions");
            QUERY_RUNNER.execute(conn, "DELETE FROM cards");
            QUERY_RUNNER.execute(conn, "DELETE FROM users");
        }
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        try (Connection conn = getConnection()) {
            QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes");
        }
    }
}
