import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class Help extends JDialog {
    public Help(JFrame fr){
       super(fr, true);
       initCompo();
    }
    
    private void initCompo(){
        setMinimumSize(new Dimension(150,150));
        
        p = new JPanel(new BorderLayout(2,2));
        lb=new JLabel("Name: Stefanos Chidiroglou");
        lb1=new JLabel("E-mail: stefanostei18@gmail.com");
        p.add(lb1, BorderLayout.NORTH);
        p.add(lb);
        add(p);
        pack();
    }
    
    private JPanel p;
    private JLabel lb, lb1;
    
}
