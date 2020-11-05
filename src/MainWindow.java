import javax.swing.*;
import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class MainWindow extends JFrame
{
    //public static StateManager stateManager = new StateManager();

    protected MenuBar menuBar;
    public CenterCanvas centerCanvas;
    public StatusBar statusBar;
    public ToolPanel toolPanel;
    public StatePanel statePanel;
    public InspectorPanel inspectorPanel;



    /**
     * Creates a new, initially invisible <code>Frame</code> with the
     * specified title.
     * <p>
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

        centerCanvas = new CenterCanvas();
        add(centerCanvas,"Center");

        statusBar = new StatusBar();
        add(statusBar, "South");

        statePanel = new StatePanel();
        add(statePanel,"West");

        inspectorPanel = new InspectorPanel();
        add(inspectorPanel, "East");

//        add(colorPalette, "South");
//        add(paintToolPanel, "West");
//        add(new JScrollPane(drawingPanel), "Center");

        // this.setIconImage(Image);    //setting JFrame's icon image
        this.setSize(1366, 768);     //set size of the application
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //set default close operation
        this.setLocationRelativeTo(null);                               //set locating to the middle of the screen
        this.setVisible(true);                                          //set visible
        // setStaringColor();


    }


}
