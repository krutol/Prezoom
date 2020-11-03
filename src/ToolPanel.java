import javax.swing.*;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class ToolPanel extends JPanel
{

    public ToolPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(-1, 50));
        setLayout(new BorderLayout(8, 8));

        JButton b = new JButton("ToolPanel");

        add(b);
    }
}
