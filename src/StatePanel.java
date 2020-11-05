import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class StatePanel extends JPanel
{
    public StateManager stateManager = new StateManager();


    public StatePanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(100, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
        TitledBorder title = BorderFactory.createTitledBorder("States");
        title.setTitleColor(Color.white);
        setBorder(title);

        add(new JButton("State"));
        add(new JButton("State"));
        add(new JButton("State"));
        add(new JButton("State"));
        add(new JButton("State"));
    }
}
