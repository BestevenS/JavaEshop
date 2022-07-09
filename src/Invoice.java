import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author costis
 */
public class Invoice extends JDialog {
    
    public Invoice(JFrame fr) {
        super(fr, true);
        connect2DB();
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * "
                    + "FROM orders"
                    + "left join inv.customer on custid = idcustomer"
                    + "left join inv.inventory on invid = idinv";

            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("ComboModel4: " + e.getMessage());
        }
        initCompo();
    }
    
    private void initCompo(){
        setMinimumSize(new Dimension(400,150));
        setTitle("Print Invoice");
        bprint = new JButton("Print");
        bprint.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
                    printMeth();
                } catch (SQLException ex) {
                    Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        p=new JPanel(new FlowLayout(2));
//        DefaultComboBoxModel cm=new ComboModel4(con);
        cb=new JComboBox<>();
        cb.setModel(new InvoiceModel(Customer.con));
        cb.setSelectedIndex(0); // Αν δεν μπει, δεν έχει επιλογή
        cb.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                refreshLabel();
            }
        });
        lb=new JLabel("Invoice For Customer: ");
        p.add(lb);
        p.add(cb, BorderLayout.EAST);
        p.add(bprint, BorderLayout.SOUTH);
        add(p);
        pack();       
    }
    
    private void printMeth() throws SQLException{
        int equalsNumber = 61;
        String idc = rs.getString("customer.idcustomer");
        System.out.println("For Customer ID: " + idc);
        System.out.println("\tCustomer Name: " + rs.getString("customer.lastname") + " "+ rs.getString("customer.firstname"));
        CustomerReport.prettyEquals(equalsNumber);
        System.out.println("Order Category\t\tDescription\tQuantity\tPrice");
        CustomerReport.prettyEquals(equalsNumber);
        System.out.println("");
        CustomerReport.prettyEquals(equalsNumber);
    }
    
    private void refreshLabel() {
        lb.setText((String) cb.getSelectedItem());
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
    
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private JButton bprint;
    private JPanel p;
    private JComboBox<String> cb;
    private JLabel lb;
}
