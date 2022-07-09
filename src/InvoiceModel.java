import java.sql.*;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author costis
 */
public class InvoiceModel extends DefaultComboBoxModel<String> {

    public InvoiceModel(Connection con) {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * "
                    + "FROM customer";
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
            lstn = rs.getString("lastname") + ", " + rs.getString("firstname") + ", " + rs.getInt("idcustomer");
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
    private static ResultSet rs;
}
