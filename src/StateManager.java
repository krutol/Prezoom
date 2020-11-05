/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class StateManager
{
    public static int current_State = 0;

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
        Main.app.statusBar.setCurStateText("Current State: "+current_State);
        Main.app.statePanel.insertStateBtn();
        Main.app.statePanel.revalidate();

        Main.app.centerCanvas.cameraManager.insertCamState();


        updateStateData();
    }

    public static void switchState(int state)
    {
        if (state == current_State)
            return;

        current_State = state;
        Main.app.statusBar.setCurStateText("Current State: "+current_State);

        updateStateData();
    }
}
