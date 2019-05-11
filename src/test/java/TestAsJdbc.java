import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * Tester.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2019/05/11 umjammer initial version <br>
 */
public class TestAsJdbc {
    static Connection conn = null;

    @BeforeAll
    public static void testVault() throws Exception {
        DriverManager.registerDriver(new vavi.sql.mdb.jdbc.Driver());
        conn = DriverManager.getConnection("jdbc:mdb:" + "tmp/ias.mdb");
    }

    /** */
    public void testSelectByPreparedStatement() throws Exception {
        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement("SELECT * FROM Objects WHERE mycode=? AND mystring=?");
        pstmt.setInt(1, 2);
        pstmt.setString(2, "文字列その2");

        ResultSet rs = pstmt.executeQuery();

        for (int index = 0; rs.next(); index++) {
            System.out.println("検索結果:" + rs.getInt(1) + ",'" + rs.getString(2) + "'");
        }

        rs.close();
        pstmt.close();
    }

    /** */
    @Test
    public void testSelectByStatement() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Objects");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + ", \"" + rs.getString(2) + "\", " + rs.getInt(3));
        }

        rs.close();
        stmt.close();
    }
}
