import java.util.ArrayList;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class StateManager
{
    public static int current_State = 0;

    public int getCurrent_State()
    {
        return current_State;
    }

    public void setCurrent_State(int state)
    {
        current_State = state;
    }
}
