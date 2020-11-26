package prezoom.controller;

import prezoom.view.CenterCanvas;
import prezoom.view.MainWindow;
import prezoom.view.PresentationWindow;

import javax.swing.*;
import java.awt.*;

/**
 * The class to manage the presentation
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/25<p>
 **/
public class PresentManager
{
    /**
     * whether is in presenting
     */
    public static boolean isPresenting = false;

    public static double presentZoomFactor;

    /**
     * generate a presentation window to present
     * switch state to beginning if necessary
     * @param playFromBeginning whether play from the first state
     */
    public static void startPresent(boolean playFromBeginning)
    {
        isPresenting = true;
        if (playFromBeginning)
        {
            StateManager.switchState(0);
        }

        setPresentZoomFactor();
        CameraManager.initializePresentationCamera();

        new PresentationWindow();
    }

    /**
     * when present is over,
     * kill the present canvas,
     * move back all text components, etc.
     * @param presentWindow the presenting dialog
     */
    public static void endPresent(PresentationWindow presentWindow)
    {
        presentWindow.dispose();
        resetTextComponentToCanvas();
        isPresenting = false;
    }

    /**
     * when start presenting, add all text areas components to the present canvas,
     * and set the text areas being not editable and focusable
     * @param presentCanvas the present canvas
     */
    public static void addTextComponentToPresenter(CenterCanvas presentCanvas)
    {
        for(JTextArea t: GObjectManager.jTextAreaList)
        {
            t.setEditable(false);
            t.setFocusable(false);
            presentCanvas.add(t);
        }
    }

    /**
     * when finishing presenting, reset all text areas back to the edit canvas
     */
    public static void resetTextComponentToCanvas()
    {
        for(JTextArea t: GObjectManager.jTextAreaList)
        {
            t.setEditable(true);
            t.setFocusable(true);
            MainWindow.centerCanvas.add(t);
        }
    }

    public static void setPresentZoomFactor()
    {
        double hZoom = Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                /MainWindow.centerCanvas.getHeight();
        double wZoom = Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                /MainWindow.centerCanvas.getWidth();

        presentZoomFactor = (hZoom+wZoom)/2;
    }
}
