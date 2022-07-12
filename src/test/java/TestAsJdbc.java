import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


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
        DriverManager.registerDriver(new vavi.sql.mdb.jdbc.Driver());
        conn = DriverManager.getConnection("jdbc:mdb:" + "src/test/resources/nwind.mdb");
    }

    @Disabled("prepareStatement is not implemented yet")
    @Test
    public void testSelectByPreparedStatement() throws Exception {
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement("SELECT * FROM Suppliers WHERE SupplierID=? AND Title=?");
        pstmt.setInt(1, 4);
        pstmt.setString(2, "Marketing Manager");

        ResultSet rs = pstmt.executeQuery();

        for (int index = 0; rs.next(); index++) {
            System.out.println("Result: " + rs.getInt(1) + ",'" + rs.getString(2) + "'");
        }

        rs.close();
        pstmt.close();
    }

    @Test
    public void testSelectByStatement() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customers");
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
        }

        rs.close();
        stmt.close();
    }

    @Test
    public void test1() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");
        while (rs.next()) {
            System.out.println(
                    rs.getInt(1) + ", " +
                    rs.getString(2) + ", " +
                    rs.getString(3));
        }

        rs.close();
        stmt.close();
    }

    @Test
    public void test2() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Suppliers");
        while (rs.next()) {
            System.out.println(
                    rs.getInt(1) + ", " +
                    rs.getString(2) + ", " +
                    rs.getString(3));
        }

        rs.close();
        stmt.close();
    }
}
