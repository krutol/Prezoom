package prezoom;

import prezoom.view.MainWindow;

import javax.swing.*;

/** The Main
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class Main
{
    /**
     * The main window of the software which can be the portal where all the components can be accessed
     */
    public static MainWindow app;

    /**
     * Application starting point, creates a new instance of the main window
     * @param args String arguments
     */
    public static void main(String[] args)
    {
        //app = new MainWindow("main");
        SwingUtilities.invokeLater(()->app = new MainWindow("Prezoom"));
    }
}
