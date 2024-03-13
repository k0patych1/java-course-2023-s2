package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class LiquibaseMigrationsTest extends IntegrationTest {
    @Test
    public void liquibaseMigrationsTest() {
        assertTrue(POSTGRES.isRunning());

        assertTrue(checkIfTableExists("link"));
        assertTrue(checkIfTableExists("chat"));
        assertTrue(checkIfTableExists("subscriptions"));
    }

    private boolean checkIfTableExists(String tableName) {
        try (Connection connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword())) {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + tableName);
            return pstmt.executeQuery().getMetaData().getTableName(1).equals(tableName);
        } catch (SQLException e) {
            return false;
        }
    }
}
