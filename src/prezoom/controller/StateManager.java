package prezoom.controller;

import prezoom.view.MainWindow;
import prezoom.view.StatePanel;


/** This is the class that controls all the state changes in PreZoom.
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

    /**
     * Set the current state, and call {@link #updateStateData()}
     * @param state the state index
     */
    public static void switchState(int state)
    {
        if (state == current_State
            && !PresentManager.isPresenting)
            return;

        current_State = state;
        MainWindow.statusBar.setCurStateText("Current State: "+current_State);
        MainWindow.statePanel.updatePressedBtn();

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

        MainWindow.cameraPanel.rearrangeValues();

    }

    /**
     * let all state-related class to insert a state at the next of the current state, then load the new state data
     * @see StatePanel#insertStateBtn()
     * @see CameraManager#insertCamState()
     * @see GAttributeManager#insertAttributeState()
     * @see GObjectManager#insertStateToGObjects()
     * @see #updateStateData()
     */
    public static void insertState()
    {
        current_State++;
        total_State_Number++;
        MainWindow.statusBar.setCurStateText("Current State: "+current_State);

        //insert camera info
        CameraManager.insertCamState();
        //insert attribute to objects
        GObjectManager.insertStateToGObjects();
        //insert state button
        MainWindow.statePanel.insertStateBtn();

        updateStateData();

    }

    /**
     * let all state-related class to delete a state, but the last one state cannot be deleted.
     * If trying to delete the last state, all data will be cleared,
     * and then a brand new state will be created
     * then reload the state data
     * @see StatePanel#deleteStateBtn(int)
     * @see CameraManager#deleteCamState(int)
     * @see GObjectManager#deleteStateToGObjects(int)
     * @see GAttributeManager#deleteAttributeState(int)
     * @see #updateStateData()
     * @param state the state index to be deleted
     */
    public static void deleteState(int state)
    {
        // if delete the last state, and the current state is the last one
        // change the current to the second to last
        if (current_State == state && state == total_State_Number-1)
            current_State--;

        total_State_Number--;

        // delete camera info
        CameraManager.deleteCamState(state);
        // delete attributes form objects
        GObjectManager.deleteStateToGObjects(state);
        // delete state button
        MainWindow.statePanel.deleteStateBtn(state);
        // make sure having at least 1 state
        if (total_State_Number == 0)
        {
//            JOptionPane.showMessageDialog(null,
//                    "The data of the last state is cleared\nCannot delete the one last state");
            insertState();
        }

        updateStateData();

    }

    /**
     * get the current state number
     * @return current state, 0-indexed
     */
    public static int getCurrent_State()
    {
        return current_State;
    }

    /**
     * get the total number of states
     * @return the total number
     */
    public static int getTotal_State_Number()
    {
        return total_State_Number;
    }

    /**
     * set the total state number to the given number
     * @param total_State_Number the total number of states
     */
    public static void setTotal_State_Number(int total_State_Number)
    {
        StateManager.total_State_Number = total_State_Number;
    }

    /**
     * delete all data
     */
    public static void clearAllStateData()
    {
        for (int i = total_State_Number-1; i >= 0; i--)
        {
            deleteState(i);
        }
    }

    /**
     * after loading saved data from saving files,
     * update all needed data
     */
    public static void reloadSavedData()
    {
        GObjectManager.reloadAllTextArea();

        for (int i = 1; i < total_State_Number; i++)
        {
            current_State = i;
            MainWindow.statePanel.insertStateBtn();
        }
        StateManager.switchState(0);
    }
}
