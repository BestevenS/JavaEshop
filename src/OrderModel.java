import java.sql.*;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author costis
 */
public class OrderModel extends DefaultComboBoxModel<String> {

    public OrderModel(Connection con) {
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
    public static void printMeth() throws SQLException{
        int equalsNumber = 61;
        int idc = rs.getInt("idcustomer");
        System.out.println("For Customer ID: " + idc);
        System.out.println("\tCustomer Name: " + rs.getString("lastname") + " "+ rs.getString("firstname"));
        CustomerReport.prettyEquals(equalsNumber);
        System.out.println("Order Category\t\tDescription\tQuantity\tPrice");
        CustomerReport.prettyEquals(equalsNumber);
        
        CustomerReport.prettyEquals(equalsNumber);
    }
    
    private Statement stmt;
    private static ResultSet rs;
}
