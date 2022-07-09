import java.sql.*;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author costis
 */
public class InvModel extends DefaultComboBoxModel<String> {

    public InvModel(Connection con) {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM inventory";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("ComboModel4: " + e.getMessage());
        }
    }

    @Override
    public String getElementAt(int index) {
        String lstn = null;
        try {
            rs.absolute(index + 1);
            lstn = rs.getString("category") + ", " + rs.getString("description") + ", "+ rs.getString("price") + ", " + rs.getInt("idinv");
        } catch (SQLException e) {
            System.out.println("getElementAt(): " + e.getMessage());
        }
        return lstn;
    }

    @Override
    public int getSize() {
        int cnt = 0;

        try {
            rs.last();
            cnt = rs.getRow();
        } catch (SQLException ex) {
            System.out.println("getSize(): " + ex.getMessage());
        }
        return cnt;
    }

    private Statement stmt;
    private ResultSet rs;
}
