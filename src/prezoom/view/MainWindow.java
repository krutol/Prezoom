package prezoom.view;

import javax.swing.*;
import java.awt.*;

/** This main GUI window which holds all the child GUI windows
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class MainWindow extends JFrame
{
    //public static prezoom.controller.StateManager stateManager = new prezoom.controller.StateManager();

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
    public static ShapeInspectorPanel inspectorPanel;


    public static CameraInspectorPanel cameraInspectorPanel;

    /**
     * Creates a new, initially invisible <code>Frame</code> with the
     * specified title. Instantiates all the child windows.
     * <p>
     *
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title for the frame
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public MainWindow(String title) throws HeadlessException
    {
        super(title);

        //JPanel mainPanel = new JPanel();
        //add(mainPanel);                                         //add panels to the main JFrame

        menuBar = new MenuBar();
        toolPanel = new ToolPanel();

        JPanel NorthPanel = new JPanel(new BorderLayout());
        NorthPanel.add(menuBar,"North");
        NorthPanel.add(toolPanel,"Center");

        add(NorthPanel,"North");

        centerCanvas = new CenterCanvas(false);
        add(centerCanvas,"Center");

        statusBar = new StatusBar();
        add(statusBar, "South");

        statePanel = new StatePanel();
        JPanel holderPanel = new JPanel(new BorderLayout());
        holderPanel.add(statePanel, "North");
        holderPanel.setBackground(Color.darkGray);
        JScrollPane stateScroll = new JScrollPane(holderPanel);
        stateScroll.setPreferredSize(new Dimension(160,-1));
        stateScroll.getVerticalScrollBar().setUnitIncrement(15);
        add(stateScroll,"West");

        //create Shape inspector panel
        inspectorPanel = new ShapeInspectorPanel("Shape Inspect");

        //holder panel for Shape inspector and camera inspector
        JPanel holderInspectorPanel = new JPanel(new GridLayout(2, 1));

        //add Shape inspector panel to holder
        holderInspectorPanel.add(inspectorPanel);

        //create camera inspector panel
        cameraInspectorPanel = new CameraInspectorPanel("Camera Inspect");

        //add Camera inspector to holder panel
        holderInspectorPanel.add(cameraInspectorPanel);

        //create scroll pane
//        JScrollPane inspectorScroll1= new JScrollPane(holderInspectorPanel);
//        inspectorScroll1.setPreferredSize(new Dimension(150,500));
//        inspectorScroll1.getVerticalScrollBar().setUnitIncrement(15);

        //add the holder
        add(holderInspectorPanel,"East");

//        add(colorPalette, "South");
//        add(paintToolPanel, "West");
//        add(new JScrollPane(drawingPanel), "Center");

        // this.setIconImage(Image);    //setting JFrame's icon image
        this.setSize(1366, 768);     //set size of the application
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //set default close operation
        this.setLocationRelativeTo(null);                               //set locating to the middle of the screen
        this.setVisible(true);                                          //set visible
        // setStaringColor();


    }

}
