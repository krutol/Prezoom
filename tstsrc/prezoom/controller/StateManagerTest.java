package prezoom.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import prezoom.view.MainWindow;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class StateManagerTest
{
    static int total = StateManager.getTotal_State_Number();
    static int current = StateManager.getCurrent_State();
    static MainWindow mainWindow = new MainWindow("");

    @BeforeAll
    static void setUp()
    {
        mainWindow.setVisible(false);
    }

    @Test
    void switchState()
    {
        insertState();

        assertEquals(total, StateManager.getTotal_State_Number());
        assertEquals(current, StateManager.getCurrent_State());

        StateManager.switchState(0);
        current = 0;
        assertEquals(current, StateManager.getCurrent_State());

        StateManager.switchState(1);
        current = 1;
        assertEquals(current, StateManager.getCurrent_State());

        StateManager.switchState(2);
        current = 2;
        assertEquals(current, StateManager.getCurrent_State());



    }

    @Test
    void getCurrent_State()
    {
        assertEquals(current,StateManager.getCurrent_State());
    }

    @Test
    void getTotal_State_Number()
    {
        assertEquals(total,StateManager.getTotal_State_Number());
    }

    @Test
    void insertState()
    {
        int size = CameraManager.state_CamInfo_list.size();
        assertDoesNotThrow(StateManager::insertState);
        total++;
        current++;
        assertEquals(size+1, CameraManager.state_CamInfo_list.size());
        assertEquals(current, StateManager.getCurrent_State());
        assertEquals(total, StateManager.getTotal_State_Number());

        assertDoesNotThrow(StateManager::insertState);
        total++;
        current++;
        assertEquals(size+2, CameraManager.state_CamInfo_list.size());
        assertEquals(current, StateManager.getCurrent_State());
        assertEquals(total, StateManager.getTotal_State_Number());

        assertDoesNotThrow(StateManager::insertState);
        total++;
        current++;
        assertEquals(size+3, CameraManager.state_CamInfo_list.size());
        assertEquals(current, StateManager.getCurrent_State());
        assertEquals(total, StateManager.getTotal_State_Number());

    }

    @Test
    void deleteState()
    {
        insertState();

        assertDoesNotThrow(()->StateManager.deleteState(3));
        total--;
        current--;
        assertEquals(total, CameraManager.state_CamInfo_list.size());
        assertEquals(current, StateManager.getCurrent_State());
        assertEquals(total, StateManager.getTotal_State_Number());

        assertDoesNotThrow(()->StateManager.deleteState(2));
        total--;
        current--;
        assertEquals(total, CameraManager.state_CamInfo_list.size());
        assertEquals(current, StateManager.getCurrent_State());
        assertEquals(total, StateManager.getTotal_State_Number());

        assertDoesNotThrow(()->StateManager.deleteState(1));
        total--;
        current--;
        assertEquals(total, CameraManager.state_CamInfo_list.size());
        assertEquals(current, StateManager.getCurrent_State());
        assertEquals(total, StateManager.getTotal_State_Number());

    }
}