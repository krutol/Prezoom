import javax.swing.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/
public class StatusBar extends JPanel
{
    private final JLabel statusText;


    public StatusBar()
    {
        statusText = new JLabel("");
        add(statusText);
    }

    void setStatusText(String str)
    {
        statusText.setText(str);
    }
}
