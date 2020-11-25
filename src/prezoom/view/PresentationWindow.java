package prezoom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import prezoom.view.MainWindow;

import prezoom.controller.StateManager;
/** This main GUI window which holds all the child GUI windows
 * Team Charlie
 * @author Ajanthan Paramasamy >
 * create date: 2020/11/18
 **/
public class PresentationWindow extends JDialog
{
    //public static prezoom.controller.StateManager stateManager = new prezoom.controller.StateManager();

    public PresentationWindow(String title, boolean isPlayFromCurrent)
    {
        //super(title);

        CenterCanvas present = new CenterCanvas(true);
        present.requestFocus();
        add(present,"Center");
        
        final int[] indexes = new int[2];
        
        // Get Total Number of States
        indexes[0] = StateManager.getTotal_State_Number();

        if (isPlayFromCurrent) {
        	indexes[1] = StateManager.getCurrent_State(); 
        }

        addKeyListener(new KeyAdapter() {
            // Get Total Number of States
            final int total_states = indexes[0];
            // Assume Current State's Index is 0
            int current_state = indexes[1];

            public void keyPressed(KeyEvent ke) {  // handler
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
                else if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(current_state != 0) {
                        current_state -= 1;
                        StateManager.switchState(current_state);
                    }
                } else if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (current_state < total_states-1) {
                        current_state += 1;
                        StateManager.switchState(current_state);
                    } else {

                        dispose();
                    }
                }
            }
        });
//        add(colorPalette, "South");
//        add(paintToolPanel, "West");
//        add(new JScrollPane(drawingPanel), "Center");

        // this.setIconImage(Image);    //setting JFrame's icon image
        this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //set default close operation
        this.setLocationRelativeTo(null);                               //set locating to the middle of the screen
        this.setUndecorated(true);
        this.setVisible(true);                                          //set visible
        // setStaringColor();


    }

}
