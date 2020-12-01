package prezoom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import prezoom.controller.PresentManager;

import prezoom.controller.StateManager;

/**
 * This presentation window
 * Team Charlie
 *
 * @author Ajanthan Paramasamy
 * <p>create date: 2020/11/18
 **/
public class PresentationWindow extends JDialog
{
    /**
     * Total Number of States
     */
    final int total_states = StateManager.getTotal_State_Number();
    /**
     * the current state
     */
    int current_state = StateManager.getCurrent_State();

    /**
     * the constructor to initialize a new {@link CenterCanvas} that is in presentation mode
     */
    public PresentationWindow()
    {
        CenterCanvas presentCanvas = new CenterCanvas(true);
        presentCanvas.requestFocus();
        add(presentCanvas, "Center");

        PresentManager.addTextComponentToPresenter(presentCanvas);

        ActionHandler actionHandler = new ActionHandler();
        addKeyListener(actionHandler);


        this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //set default close operation
        this.setLocationRelativeTo(null);                               //set locating to the middle of the screen
        this.setUndecorated(true);
        this.setVisible(true);                                          //set visible
        // setStaringColor();

    }

    /**
     * exit the presentation mode
     */
    public void exitPresent()
    {
        PresentManager.endPresent(PresentationWindow.this);
    }

    /**
     * go to the next state
     */
    public void nextState()
    {
        if (current_state < total_states - 1)
        {
            current_state++;
            StateManager.switchState(current_state);
        } else
        {
            exitPresent();
        }
    }

    /**
     * go to the previous state
     */
    public void previousState()
    {
        if (current_state != 0)
        {
            current_state--;
            StateManager.switchState(current_state);
        }
    }

    /**
     * go to the current state
     */
    public void currentState()
    {
        StateManager.switchState(current_state);
    }

    /**
     * the listener.
     * {@link #exitPresent()} when {@link KeyEvent#VK_ESCAPE}
     * {@link #previousState()} when {@link KeyEvent#VK_LEFT}
     * {@link #nextState()} when {@link KeyEvent#VK_RIGHT}
     * {@link #currentState()} when {@link KeyEvent#VK_SPACE}
     */
    private class ActionHandler implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent ke)
        {  // handler
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
                exitPresent();
            } else if (ke.getKeyCode() == KeyEvent.VK_LEFT)
            {
                previousState();
            } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                nextState();
            } else if(ke.getKeyCode() == KeyEvent.VK_SPACE)
            {
                currentState();
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }

    }

}

