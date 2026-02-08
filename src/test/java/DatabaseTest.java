import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class DatabaseTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @Test
    void testDatabaseConnection() throws Exception {

        Connection conn = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE users(id SERIAL, name VARCHAR(100));");
        stmt.execute("INSERT INTO users(name) VALUES ('Ivan');");

        ResultSet rs = stmt.executeQuery("SELECT name FROM users");

        rs.next();

        assertEquals("Ivan", rs.getString("name"));
    }
}