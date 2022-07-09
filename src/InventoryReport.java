import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class InventoryReport extends JDialog {
    public InventoryReport(JFrame fr){
       super(fr, true);
       connect2DB();
       initCompo();
    }
    
    private void initCompo(){
        setTitle("Inventory Report");
        setMinimumSize(new Dimension(400,100));
        bprint=new JButton("Print Customers");
        bprint.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    doPrint();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerReport.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bprint.setSize(new Dimension(150,25));
        bp.add(bprint);
        add(bp);
        pack();
    }
    
    private void doPrint() throws SQLException{
        int numOfEquals = 74;
        System.out.println("INVENTORY REPORT\n\n");
        System.out.println(" Code Category\t\t"
                + "Description\t\t"
                + "Price\t\t"
                + "Quantity");
        prettyEquals(numOfEquals);
        
        dataOutput();
        
        prettyEquals(numOfEquals);
        
    }
    
    private void dataOutput(){
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            query = "SELECT * FROM inventory";
            rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                data2Form();
                System.out.printf("%5s %-17s %-23s %-15s %-20s\n", tid, tdes, tcat, tprice, tq);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void prettyEquals(int x){
        for(int i = 0; i<x; i++)
            System.out.print("=");
        System.out.println();
    }
    
    private void connect2DB() {
        // check for the driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            String msg = "The com.mysql.cj.jdbc.Driver is missing\n"
                    + "install and rerun the application";
            JOptionPane.showMessageDialog(this, msg, this.getTitle(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // connect to db
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inv?useSSL=false", "root", "root");
        } catch (SQLException e) {
            String msg = "Error Connecting to Database:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, msg, this.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void data2Form() {
        try {
            tid = rs.getInt("idinv");
            tcat = rs.getString("category");
            tdes = rs.getString("description");
            tprice = rs.getString("price");
            tq = rs.getString("quantity");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Data
    private int tid;
    private String tcat, tdes, tprice, tq;
    
    private String query;
    private Statement stmt;
    private ResultSet rs;
    private Connection con;
    private JButton bprint;
    private JPanel bp = new JPanel(new FlowLayout());
}
