import javax.swing.table.*;
import java.sql.*;

public class OrderTable extends AbstractTableModel {

    public OrderTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/inv", "root", "root");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(""
                    + "SELECT * \n" +
                    "	FROM orders\n" +
                    "		LEFT JOIN inv.customer on custid = idcustomer\n" +
                    "        LEFT JOIN inv.inventory on invid = idinv;");
                    
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        int count = 0;
        try {
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("Model" + ex.getMessage());
        }
        return count;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retVal = null;

        try {
            rs.absolute(rowIndex + 1);
            switch (columnIndex) {
                case 0:
                    retVal = rs.getString("idOrders");
                    break;
                case 1:
                    retVal = rs.getString("customer.lastname");
                    break;
                case 2:
                    retVal = rs.getString("inventory.category");
                    break;
                case 3:
                    retVal = rs.getString("inventory.description");
                    break;
                case 4:
                    retVal = rs.getString("price");
            }
        } catch (SQLException sex) {
            sex.printStackTrace();
        }

        return retVal;
    }
    
    private String[] columnNames = 
    {
        "OrderID",
        "Customer",
        "Category",
        "Dwscription",
        "Price"
    };
    
    private ResultSet rs;
}
