package prezoom.controller;

import prezoom.view.CenterCanvas;
import prezoom.view.MainWindow;
import prezoom.view.StatePanel;

import javax.swing.*;

/** This is the class that controls all the state changes in Prezoom.
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class StateManager
{
    /**
     * the current selected state
     */
    private static int current_State = 0;
    /**
     * the total number of states
     */
    private static int total_State_Number = 1;

//    public int getCurrent_State()
//    {
//        return current_State;
//    }

    /**
     * Set the current state, and call {@link #updateStateData()}
     * @param state the state index
     */
    public static void switchState(int state)
    {
        if (state == current_State)
            return;

        current_State = state;
        MainWindow.statusBar.setCurStateText("Current State: "+current_State);

        updateStateData();
    }

    /**
     * update the state data to the current state
     * including camera {@link CameraManager#updateCur_CamInfo()}, objects {@link GAttributeManager#updateCur_Attributes()}
     */
    private static void updateStateData()
    {
        // update camera info
        CameraManager.updateCur_CamInfo();
        // update attributes of GObjects
        GObjectManager.updateAllObject();
//        for (GObject o: Main.app.centerCanvas.objects)
//        {
//            o.getAttributeManager().updateCur_Attributes();
//        }
        // repaint canvas
        //Main.app.centerCanvas.repaint();

    }

    /**
     * let all state-related class to insert a state at the next of the current state, then load the new state data
     * @see StatePanel#insertStateBtn()
     * @see CameraManager#insertCamState()
     * @see GAttributeManager#insertAttributeState()
     * @see #updateStateData()
     * @throws CloneNotSupportedException nothing
     */
    public static void insertState() throws CloneNotSupportedException
    {
        current_State++;
        total_State_Number++;
        MainWindow.statusBar.setCurStateText("Current State: "+current_State);

        //insert state button
        MainWindow.statePanel.insertStateBtn();

        //insert camera info
        CameraManager.insertCamState();
        //insert attribute to objects
        GObjectManager.insertStateToGObjects();
//        for (GObject o: Main.app.centerCanvas.objects)
//        {
//            o.getAttributeManager().insertAttributeState();
//        }

        updateStateData();

    }

    /**
     * let all state-related class to delete a state, but the last one state cannot be deleted, then reload the state data
     * @see StatePanel#deleteStateBtn(int)
     * @see CameraManager#deleteCamState(int)
     * @see GAttributeManager#deleteAttributeState(int)
     * @see #updateStateData()
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
        MainWindow.statePanel.deleteStateBtn(state);

        // delete camera info
        CameraManager.deleteCamState(state);
        // delete attributes form objects
        GObjectManager.deleteStateToGObjects(state);
//        for (GObject o: Main.app.centerCanvas.objects)
//        {
//            o.getAttributeManager().deleteAttributeState(state);
//        }

        updateStateData();

    }

    public static int getCurrent_State()
    {
        return current_State;
    }

    public static int getTotal_State_Number()
    {
        return total_State_Number;
    }
}
