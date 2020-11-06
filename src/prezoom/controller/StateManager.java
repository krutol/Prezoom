package prezoom.controller;

import prezoom.Main;
import prezoom.model.GObject;

import javax.swing.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 *
 * This is the class that controls all the state changes in Prezoom
 **/
public class StateManager
{
    public static int current_State = 0; // the current selected state
    public static int total_State_Number = 1; // the total number of states

//    public int getCurrent_State()
//    {
//        return current_State;
//    }

    /**
     * Set the current state, and update corresponding Data
     * @param state the state index
     */
    public static void switchState(int state)
    {
        if (state == current_State)
            return;

        current_State = state;
        Main.app.statusBar.setCurStateText("Current State: "+current_State);

        updateStateData();
    }

    /**
     * update the state data to the current state
     * including camera, objects
     */
    private static void updateStateData()
    {
        // update camera info
        Main.app.centerCanvas.cameraManager.updateCur_CamInfo();
        // update attributes of GObjects
        for (GObject o: Main.app.centerCanvas.objects)
        {
            o.gAttributeManager.updateCur_Attributes();
        }
        // repaint canvas
        Main.app.centerCanvas.repaint();

    }

    /**
     * insert a state at the next of the current state
     * @throws CloneNotSupportedException nothing
     */
    public static void insertState() throws CloneNotSupportedException
    {
        current_State++;
        total_State_Number++;
        Main.app.statusBar.setCurStateText("Current State: "+current_State);

        //insert state button
        Main.app.statePanel.insertStateBtn();

        //insert camera info
        Main.app.centerCanvas.cameraManager.insertCamState();
        //insert attribute to objects
        for (GObject o: Main.app.centerCanvas.objects)
        {
            o.gAttributeManager.insertAttributeState();
        }

        updateStateData();
    }

    /**
     * delete a state
     * @param state the state index to be deleted
     */
    public static void deleteState(int state)
    {
        // make sure having at least 1 state
        if (total_State_Number == 1)
        {
            JOptionPane.showMessageDialog(null, "Cannot delete the one last state");
            return;
        }

        // if delete the last state, and the current state is the last one
        // change the current to the second to last
        if (current_State == state && state == total_State_Number-1)
            current_State--;

        total_State_Number--;


        // delete state button
        Main.app.statePanel.deleteStateBtn(state);

        // delete camera info
        Main.app.centerCanvas.cameraManager.deleteCamState(state);
        // delete attributes form objects
        for (GObject o: Main.app.centerCanvas.objects)
        {
            o.gAttributeManager.deleteAttributeState(state);
        }

        updateStateData();

    }

}
