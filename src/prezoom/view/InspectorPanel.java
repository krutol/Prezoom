package prezoom.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class InspectorPanel extends JPanel
{


    public InspectorPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(100, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER,5,10));

        TitledBorder title = BorderFactory.createTitledBorder("Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        JLabel label = new JLabel("Label:");
        label.setForeground(Color.white);
        add(label);

        JTextField txt = new JTextField(3);
        add(txt);

        label = new JLabel("X:");
        label.setForeground(Color.white);
        add(label);

        txt = new JTextField(5);
        add(txt);

        label = new JLabel("Y:");
        label.setForeground(Color.white);
        add(label);

        txt = new JTextField(5);
        add(txt);

        label = new JLabel("Width:");
        label.setForeground(Color.white);
        add(label);

        txt = new JTextField(3);
        add(txt);

        label = new JLabel("Height:");
        label.setForeground(Color.white);
        add(label);

        txt = new JTextField(3);
        add(txt);



    }
}
