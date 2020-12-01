package prezoom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** This main GUI window which holds all the child GUI windows
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class MainWindow extends JFrame
{
    /**
     * the menu bar
     */
    public static MenuBar menuBar;
    /**
     * the canvas to present objects
     */
    public static CenterCanvas centerCanvas;
    /**
     * the status bar
     */
    public static StatusBar statusBar;
    /**
     * the panel that contains different function buttons
     */
    public static ToolPanel toolPanel;
    /**
     * The panel that shows the state sequentially
     */
    public static StatePanel statePanel;
    /**
     * The panel that shows editable attributes of selected objects and other useful info
     */
    public static AttributePanel attributePanel;

    /**
     * The panel that shows editable attributes of camera and other useful info
     */
    public static CameraPanel cameraPanel;


    /**
     * The main window constructor. add all child view components.
     * @param title name of the window
     */
    public MainWindow(String title)
    {
        super(title);

        menuBar = new MenuBar();
        toolPanel = new ToolPanel();

        JPanel NorthPanel = new JPanel(new BorderLayout());
        NorthPanel.add(menuBar, "North");
        NorthPanel.add(toolPanel, "Center");

        add(NorthPanel, "North");

        centerCanvas = new CenterCanvas(false);
        add(centerCanvas, "Center");

        statusBar = new StatusBar();
        add(statusBar, "South");

        statePanel = new StatePanel();
        JPanel holderPanel = new JPanel(new BorderLayout());
        holderPanel.add(statePanel, "North");
        holderPanel.setBackground(Color.darkGray);
        JScrollPane stateScroll = new JScrollPane(holderPanel);
        stateScroll.setPreferredSize(new Dimension(160, -1));
        stateScroll.getVerticalScrollBar().setUnitIncrement(15);
        add(stateScroll, "West");

        //create Shape inspector panel
        attributePanel = new AttributePanel();

        //holder panel for Shape inspector and camera inspector
        JPanel holderInspectorPanel = new JPanel(new GridLayout(2, 1));

        //add Shape inspector panel to holder
        holderInspectorPanel.add(attributePanel);

        //create camera inspector panel
        cameraPanel = new CameraPanel();

        //add Camera inspector to holder panel
        holderInspectorPanel.add(cameraPanel);

        //add the holder
        add(holderInspectorPanel, "East");

        // this.setIconImage(Image);    //setting JFrame's icon image
        this.setSize(1366, 768);     //set size of the application
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //set default close operation
        this.setLocationRelativeTo(null);                               //set locating to the middle of the screen
        this.setVisible(true);                                          //set visible
        // setStaringColor();


        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit PreZoom?",
                        "Confirm Exit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION)
                {
                    System.exit(0);
                }

            }
        });
    }



}
