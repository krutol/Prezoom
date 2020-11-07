package prezoom.view;

import javax.swing.*;
import java.awt.*;

/** The bar at the bottom to show some info
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/
public class StatusBar extends JPanel
{
    private final JLabel statusText;
    private final JLabel zoomText;
    private final JLabel curStateText;

    public StatusBar()
    {
        setBackground(Color.darkGray);
        setLayout(new BorderLayout());

        curStateText = new JLabel("Current State: 0");
        curStateText.setForeground(Color.white);
        curStateText.setPreferredSize(new Dimension(500,-1));
        add(curStateText,"West");

        statusText = new JLabel("");
        statusText.setForeground(Color.white);
        add(statusText,"Center");

        zoomText = new JLabel("Zoom: 100.00 %");
        zoomText.setForeground(Color.white);
        add(zoomText,"East");

    }

    public void setCurStateText(String str)
    {
        curStateText.setText(str);
    }

    public void setStatusText(String str)
    {
        statusText.setText(str);
    }

    public void setZoomText(String str)
    {
        zoomText.setText(str);
    }

}
