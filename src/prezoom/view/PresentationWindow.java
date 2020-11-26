package prezoom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import prezoom.controller.PresentManager;

import prezoom.controller.StateManager;

/**
 * This main GUI window which holds all the child GUI windows
 * Team Charlie
 *
 * @author Ajanthan Paramasamy >
 * create date: 2020/11/18
 **/
public class PresentationWindow extends JDialog
{
    //public static prezoom.controller.StateManager stateManager = new prezoom.controller.StateManager();

    public PresentationWindow()
    {
        CenterCanvas presentCanvas = new CenterCanvas(true);
        presentCanvas.requestFocus();
        add(presentCanvas, "Center");

        PresentManager.addTextComponentToPresenter(presentCanvas);

        addKeyListener(new KeyAdapter()
        {
            // Get Total Number of States
            final int total_states = StateManager.getTotal_State_Number();
            // Assume Current State's Index is 0
            int current_state = StateManager.getCurrent_State();

            public void keyPressed(KeyEvent ke)
            {  // handler
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    PresentManager.endPresent(PresentationWindow.this);
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    if (current_state != 0)
                    {
                        current_state--;
                        StateManager.switchState(current_state);
                    }
                } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    if (current_state < total_states - 1)
                    {
                        current_state++;
                        StateManager.switchState(current_state);
                    } else
                    {
                        PresentManager.endPresent(PresentationWindow.this);
                    }
                }
            }
        });
//        add(colorPalette, "South");
//        add(paintToolPanel, "West");
//        add(new JScrollPane(drawingPanel), "Center");

        // this.setIconImage(Image);    //setting JFrame's icon image
        this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //set default close operation
        this.setLocationRelativeTo(null);                               //set locating to the middle of the screen
        this.setUndecorated(true);
        this.setVisible(true);                                          //set visible
        // setStaringColor();


    }

}