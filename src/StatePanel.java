import javax.swing.*;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class StatePanel extends JPanel
{
    public StatePanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(100, -1));
        setLayout(new BorderLayout(8, 8));

        JButton b = new JButton("State");

        add(b);
    }
}
