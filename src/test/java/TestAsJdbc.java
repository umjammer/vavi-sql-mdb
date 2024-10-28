import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test JDBC.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2019/05/11 umjammer initial version <br>
 */
public class TestAsJdbc {
    static Connection conn = null;

    @BeforeAll
    public static void testVault() throws Exception {
        conn = DriverManager.getConnection("jdbc:mdb:" + "src/test/resources/nwind.mdb");
    }

    @ParameterizedTest
    @CsvSource({"AND,25,1", "OR,24,6"})
    public void testSelectByPreparedStatement(String op, int id, int rows) throws Exception {
        PreparedStatement pstmt;

        pstmt = conn.prepareStatement("SELECT * FROM Suppliers WHERE SupplierID=? " + op + " ContactTitle=?");
        pstmt.setInt(1, id);
        pstmt.setString(2, "Marketing Manager");

        ResultSet rs = pstmt.executeQuery();

        ResultSetMetaData md = rs.getMetaData();
        int columns = rs.getMetaData().getColumnCount();
        Function<Integer, String> getNameAt = i -> { try { return md.getColumnName(i + 1); } catch (SQLException e) { throw new RuntimeException(e); }};
        Function<Integer, String> getObjectAt = i -> { try { return String.valueOf(rs.getObject(i + 1)).replaceAll("[\r\n]", " "); } catch (SQLException e) { throw new RuntimeException(e); }};
        System.err.println(String.join(", ", IntStream.range(0, columns).mapToObj(getNameAt::apply).toArray(String[]::new)));
        int c = 0;
        while (rs.next()) {
            System.out.println(String.join(", ", IntStream.range(0, columns).mapToObj(getObjectAt::apply).toArray(String[]::new)));
            c++;
        }

        assertEquals(rows, c);

        rs.close();
        pstmt.close();
    }

    @Test
    public void testSelectByStatement() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customers");

        int c = 0;
        while (rs.next()) {
            System.out.println(
                    rs.getString(1) + ", " +
                    rs.getString(2) + ", " +
                    rs.getString(3) + ", " +
                    rs.getString(4) + ", " +
                    rs.getString(5) + ", " +
                    rs.getString(6) + ", " +
                    rs.getString(7) + ", " +
                    rs.getString(8) + ", " +
                    rs.getString(9) + ", " +
                    rs.getString(10) + ", " +
                    rs.getString(11));
            c++;
        }

        assertEquals(91, c);

        rs.close();
        stmt.close();
    }

    @Test
    public void test1() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");

        int c = 0;
        while (rs.next()) {
            System.out.println(
                    rs.getInt(1) + ", " +
                    rs.getString(2) + ", " +
                    rs.getString(3));
            c++;
        }

        assertEquals(9, c);

        rs.close();
        stmt.close();
    }

    @Test
    public void test2() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Suppliers");

        int c = 0;
        while (rs.next()) {
            System.out.println(
                    rs.getInt(1) + ", " +
                    rs.getString(2) + ", " +
                    rs.getString(3));
            c++;
        }

        assertEquals(29, c);

        rs.close();
        stmt.close();
    }
}
