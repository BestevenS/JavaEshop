import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.lang.Integer.parseInt;
import java.sql.*;

public class Customer extends JDialog {

    public Customer(JFrame fr) {
        super(fr, true);
        initComponents();
        connect2DB();
        prepareForm();
    }

    private void initComponents() {
        setTitle("Customer");
        tb = new JToolBar();
        tb.setFloatable(false);
        bAdd = new JButton("Add");
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAdd();
            }
        });
        bDelete = new JButton("Delete");
        bDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDelete();
            }
        });
        bFrst = new JButton("First");
        bFrst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doFirst();
            }
        });
        bPrv = new JButton("Previous");
        bPrv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doPrevious();
            }
        });
        bNxt = new JButton("Next");
        bNxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNext();
            }
        });
        bLst = new JButton("Last");
        bLst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLast();
            }
        });
        bOK = new JButton("OK");
        bOK.setEnabled(false);
        bOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doOK();
            }
        });
        bCancel = new JButton("Cancel");
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });
        bModi = new JButton("Modify");
        bModi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doModify();
            }
        });

        tb.add(bFrst);
        tb.add(bPrv);
        tb.add(bNxt);
        tb.add(bLst);
        tb.add(bAdd);
        
        tb.add(bModi);
        tb.add(bDelete);
        tb.add(bOK);
        tb.add(bCancel);

        form = new JPanel(new BorderLayout(5,1));
        fp = new JPanel(new GridLayout(5, 4));

        lid = new JLabel("ID:", JLabel.RIGHT);
        lln = new JLabel("Lastname:", JLabel.RIGHT);
        lfn = new JLabel("Firstname:", JLabel.RIGHT);
        lAfm = new JLabel("AFM:", JLabel.RIGHT);
        lphone = new JLabel("Telephone:", JLabel.RIGHT);
        

        tid = new JTextField();
        tfn = new JTextField();
        tln = new JTextField();
        tAfm = new JTextField();
        tphone = new JTextField();

        fp.add(lid);
        fp.add(tid);
        //fp.add(new JLabel());
        //fp.add(new JLabel());
        fp.add(lln);
        fp.add(tln);
        fp.add(lfn);
        fp.add(tfn);
        fp.add(lAfm);
        fp.add(tAfm);
        fp.add(lphone);
        fp.add(tphone);

        form.add(fp);
        add(tb, BorderLayout.NORTH);
        add(form);
        pack();
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

    private void prepareForm() {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            query = "SELECT * FROM customer";
            rs = stmt.executeQuery(query);

            if (rs.first()) {
                data2Form();
                current = 1;
            } else {
                current = 0;
            }

            tid.setEditable(false);
            tfn.setEditable(false);
            tln.setEditable(false);
            tAfm.setEditable(false);
            tphone.setEditable(false);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doAdd() {
        doLast();
        int tidText = parseInt(tid.getText());
        tid.setText(String.valueOf(1+tidText));
        tfn.setEditable(true);
        tfn.setText(null);
        tln.setEditable(true);
        tln.setText(null);
        tAfm.setEditable(true);
        tAfm.setText(null);
        tphone.setEditable(true);
        tphone.setText(null);
        
        bOK.setEnabled(true);
    }
    
    private void doFirst() {
        // first
        try {

            if (rs.first()) {
                data2Form();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void doLast() {
        // last
        try {

            if (rs.last()) {
                data2Form();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }

    private void doPrevious() {
        // previous
        try {
            if (!rs.isFirst()) {
                if (rs.previous()) {
                    data2Form();

                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }

    private void doNext() {
        // next
        try {
            if (!rs.isLast()) {
                if (rs.next()) {
                    data2Form();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doOK() {
        // OK
        form2DB();
        if (mode == 0) {
            doLast();
        }
        current += 1;
        
        //tid.setEditable(false);
        tfn.setEditable(false);
        tln.setEditable(false);
        tAfm.setEditable(false);
        tphone.setEditable(false);
        bOK.setEnabled(false);
    }

    private void doCancel() {
        setVisible(false);
        closeJDBC(rs, stmt, con);
    }
    
    private void doDelete() {
        if (current > 0) {
            try {
                current = rs.getInt(1);
                rs.deleteRow();
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                data2Form();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void doModify() {
        // modify
        String mod = "MODIFY";
        mode = 1;
        if (current > 0) {
            try {
                current = rs.getInt(1);
                if (mod.equals("DELETE")) {
                    rs.deleteRow();
                }
            } catch (SQLException e) {
                System.out.println("doModify: " + e.getMessage());
            }
        }

        tfn.setEditable(true);
        tln.setEditable(true);
        tAfm.setEditable(true);
        tphone.setEditable(true);

        bOK.setEnabled(true);
        bCancel.setEnabled(true);
    }

    private void data2Form() {
        try {
            tid.setText(String.valueOf(rs.getInt("idcustomer")));
            tln.setText(rs.getString("lastname"));
            tfn.setText(rs.getString("firstname"));
            tAfm.setText(rs.getString("afm"));
            tphone.setText(rs.getString("telephone"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void space2Form() {
        tid.setText(null);
        tfn.setText(null);
        tln.setText(null);
        tAfm.setText(null);
        tphone.setText(null);
    }

    private void form2DB() {
        try {
            if (mode == 0) { // register
                rs.moveToInsertRow();
            }

            rs.updateString("lastname", tln.getText());
            rs.updateString("firstname", tfn.getText());
            rs.updateString("afm",tAfm.getText());
            rs.updateString("telephone", tphone.getText());

            if (mode == 0) {
                rs.insertRow();
            } else { // modify
                rs.updateRow();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeJDBC(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                if (!statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String query;
    public static Connection con;
    private Statement stmt;
    private ResultSet rs;
    private JToolBar tb;
    private JPanel form, fp;
    private JLabel lid, lfn, lln, lphone, lAfm;
    private JTextField tid, tfn, tln, tAfm, tphone;
    private JButton bAdd, bDelete, bFrst, bPrv, bNxt, bLst, bOK, bCancel, bModi;

    private int current = 0;
    private int mode = 0;
}