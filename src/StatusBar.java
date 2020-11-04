import javax.swing.*;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/
public class StatusBar extends JPanel
{
    private final JLabel statusText;
    private final JLabel zoomText;


    public StatusBar()
    {
        setBackground(Color.darkGray);
        setLayout(new BorderLayout());

        statusText = new JLabel("");
        statusText.setForeground(Color.white);
        add(statusText,"Center");

        zoomText = new JLabel("Zoom: 100.00 %");
        zoomText.setForeground(Color.white);
        add(zoomText,"East");
    }

    void setStatusText(String str)
    {
        statusText.setText(str);
    }

    void setZoomText(String str)
    {
        zoomText.setText(str);
    }
}
