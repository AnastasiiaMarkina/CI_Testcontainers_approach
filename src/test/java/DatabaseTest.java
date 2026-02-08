import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest extends AbstractDatabaseTest{


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