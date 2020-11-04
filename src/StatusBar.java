import javax.swing.*;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/
public class StatusBar extends JPanel
{
    private final JLabel statusText;


    public StatusBar()
    {
        setBackground(Color.darkGray);
        statusText = new JLabel("");
        statusText.setForeground(Color.white);
        add(statusText);
    }

    void setStatusText(String str)
    {
        statusText.setText(str);
    }
}
