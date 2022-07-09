import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Costis
 */

public class Order extends JDialog {

    public Order(JFrame fr) {
        super(fr, true);
        connect2DB();
        initComponents();
    }

    private void initComponents() {
        setTitle("Place Order");
        //setMaximumSize(new Dimension(400,400));
        fp = new JPanel(new GridLayout(6,2));
        sp = new JPanel(new FlowLayout());
        bp = new JPanel(new FlowLayout());
        
        cb=new JComboBox<>();
        cb.setModel(new OrderModel(con));
        cb.setSelectedIndex(0); // Αν δεν μπει, δεν έχει επιλογή
        cb.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                refreshLabel();
            }
        });
        
        ib=new JComboBox<>();
        ib.setModel(new InvModel(con));
        ib.setSelectedIndex(0); // Αν δεν μπει, δεν έχει επιλογή
        ib.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                refreshLabeli();
            }
        });
        
        lcustomer = new JLabel("Customer:");
        linventory = new JLabel("Inventory Item:");
        lprice = new JLabel("Item PriceL:");
        lquantity = new JLabel("Quantity:");
        ltotalPrice = new JLabel("Total Price:");
        
        tPrice = new JTextField();
        tquantity = new JTextField();
        ttotalPrice = new JTextField();
        
        bal = new JButton("Add Line");
        bal.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                addLine();
            }
        });
        
        bdl = new JButton("Delete Line");
        bdl.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                deleteLine();
            }
        });
        bexit = new JButton("Exit");
        bexit.setMaximumSize(new Dimension(100,50));
        bexit.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        t = new JTable(new OrderTable());
        
        spp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fp, sp);
        scp = new JScrollPane(t);
        
        fp.add(lcustomer);
        fp.add(cb);
        fp.add(linventory);
        fp.add(ib);
        fp.add(lprice);
        fp.add(tPrice);
        fp.add(lquantity);
        fp.add(tquantity);
        fp.add(ltotalPrice);
        fp.add(ttotalPrice);
        
        bp.add(bal);
        bp.add(bdl);
        
        fp.add(bp);
        
        sp.add(scp);
        sp.add(bexit, FlowLayout.LEFT);
        
        add(bexit, BorderLayout.SOUTH);
        
        add(spp);
        
        pack();
    }
    
    private void connect2DB(){
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inv", "root", "root");
        } catch (SQLException e) {
            String msg = "Error Connecting to Database:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, msg, this.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteLine(){
        if (current > 0) {
            try {
                current = rs.getInt(1);
                rs.deleteRow();
                JOptionPane.showMessageDialog(this, "Order deleted successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            refreshLabel();
        }
    }
    
    private void refreshLabel() {
        lcustomer.setText((String) cb.getSelectedItem());
    }
    
    private void refreshLabeli() {
        linventory.setText((String) ib.getSelectedItem());
    }
    
    private void addLine(){
        if(tPrice!=null && tquantity!=null && ttotalPrice!=null){
            
        }
        else 
            System.out.println("You have to fill all fields");
    }
    
    private JTextField tPrice, tquantity, ttotalPrice;
    private Connection con;
    private JComboBox<String> cb, ib;
    private JLabel lcustomer, linventory, lprice, lquantity, ltotalPrice;
    private JSplitPane spp;
    private JScrollPane scp;
    private JTable t;
    private JButton bal, bdl, bexit;
    private JPanel fp, sp, bp;
    private int current = 0;
    private ResultSet rs;
}