import prezoom.controller.CameraManager;
import prezoom.controller.GAttributeManager;
import prezoom.view.StatePanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class StateManager
{
    public static int current_State = 0;
    public static int total_State_Number = 1;

//    public int getCurrent_State()
//    {
//        return current_State;
//    }

    /**
     * Set the current state, and update corresponding class
     * @param state the state number
     */
    public static void setCurrent_State(int state)
    {
        current_State = state;
        updateStateData();
    }

    private static void updateStateData()
    {
        Main.app.centerCanvas.cameraManager.updateCur_CamInfo();
        Main.app.centerCanvas.repaint();

    }

    public static void insertState() throws CloneNotSupportedException
    {
        current_State++;
        total_State_Number++;
        Main.app.statusBar.setCurStateText("Current State: "+current_State);
        //GObject[] newObject = Main.app.centerCanvas.objects.clone();
        ArrayList<GObject> newObject = (ArrayList<GObject>) Main.app.centerCanvas.objects.clone();
        Main.app.centerCanvas.objectsState.add(newObject);
        Main.app.centerCanvas.objects = newObject;
        Main.app.statePanel.insertStateBtn();
        Main.app.statePanel.revalidate();
        Main.app.inspectorPanel.revalidate();

        Main.app.centerCanvas.cameraManager.insertCamState();

        for(GObject o: Main.app.centerCanvas.objects){
            o.gAttributeManager.insertAttributeState();
        }
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
    public static void deleteState(int state) {
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
        Main.app.statePanel.deleteStateBtn(state);
        Main.app.centerCanvas.cameraManager.deleteCamState(state);
        for(GObject o: Main.app.centerCanvas.objects){
            o.gAttributeManager.deleteAttributeState(state);
        }
        updateStateData();
        Main.app.centerCanvas.repaint();
        Main.app.statePanel.repaint();
    }
    public static void switchState(int state)
    {
        if (state == current_State)
            return;
        if(Main.app.centerCanvas.objectsState.size() > current_State)
            Main.app.centerCanvas.objectsState.remove(current_State);
        Main.app.centerCanvas.objectsState.add(current_State, (ArrayList<GObject>)Main.app.centerCanvas.objects.clone());
        current_State = state;

        Main.app.centerCanvas.objects = Main.app.centerCanvas.objectsState.get(state);
        Main.app.statusBar.setCurStateText("Current State: "+current_State);

        updateStateData();
    }
}
