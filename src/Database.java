import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Database() {
        connect2DB();
    }

    private void connect2DB() {
        // check for the driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            String msg = "The com.mysql.cj.jdbc.Driver is missing\n"
                    + "install and rerun the application";
            System.out.println(msg);
            System.exit(1);
        }

        // connect to db
        try {
            Customer.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inv?useSSL=false", "root", "root");
        } catch (SQLException e) {
            String msg = "Error Connecting to Database:\n" + e.getMessage();
            System.out.println(msg);
        }
    }
}
