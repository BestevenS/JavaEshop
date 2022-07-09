import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuClass extends JFrame {
    public MenuClass(){
        initCompo();
        Inventory = new Inventory(this);
        Customers = new Customer(this);
        placeOrder = new Order(this);
        customer = new CustomerReport(this);
        Inventory1 = new InventoryReport(this);
        Invoice = new Invoice(this);
        About = new Help(this);
    }
    
    private void initCompo(){
        mb=new JMenuBar();
        setTitle("Invoice Application 2021");
        this.setMinimumSize(new Dimension(350, 150));
        //  creating menu
        //  mnu[0]
        mnu[0] = new JMenu("Files");
        files[0] = new JMenuItem("Inventory");
        files[0].addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                Inventory.setVisible(true);
            }
        });
        files[1] = new JMenuItem("Customers");
        files[1].addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                Customers.setVisible(true);
            }
        });
        files[2] = new JMenuItem("Exit");
        files[2].addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        //  mnu[1]
        mnu[1] = new JMenu("Order");
        order = new JMenuItem("Place Order");
        order.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                placeOrder.setVisible(true);
            }
        });
        
        //  mnu[2]
        mnu[2] = new JMenu("Reports");
        reports[0] = new JMenuItem("Customers");
        reports[0].addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                customer.setVisible(true);
            }
        });
        reports[1] = new JMenuItem("Inventory");
        reports[1].addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                Inventory1.setVisible(true);
            }
        });
        reports[2] = new JMenuItem("INVOICE");
        reports[2].addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                Invoice.setVisible(true);
            }
        });
        
        //  mnu[3]
        mnu[3] = new JMenu("Help");
        help = new JMenuItem("About");
        help.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                About.setVisible(true);
            }
        });
        
        
        //  adding every menu to mb
        for(int i = 0; i<mnu.length; i++){
            mb.add(mnu[i]);
        }
        
        for(int i=0; i<files.length; i++)
            mnu[0].add(files[i]);
        
        mnu[1].add(order);
        
        for(int i = 0; i<reports.length; i++){
            mnu[2].add(reports[i]);
            if(i==1)
                mnu[2].add(new JSeparator());
        }
        
        mnu[3].add(help);
        
        
        setJMenuBar(mb);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    public static void main(String[] args) {
        new MenuClass().setVisible(true);
    }
    private JMenuBar mb;
    private JMenu[] mnu = new JMenu[4];
    private JMenuItem[] files = new JMenuItem[3];
    private JMenuItem[] reports = new JMenuItem[3];
    private JMenuItem order;
    private JMenuItem help;
    private Inventory Inventory;
    private Customer Customers;
    private Order placeOrder;
    private CustomerReport customer;
    private InventoryReport Inventory1;
    private Invoice Invoice;
    private Help About;
}