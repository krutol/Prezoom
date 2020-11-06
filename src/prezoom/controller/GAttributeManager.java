package prezoom.controller;

import prezoom.model.GAttributes;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/6
 **/
public class GAttributeManager
{
    protected ArrayList<GAttributes> state_Attributes_list = new ArrayList<>();
    //int current_State = 0;
    public GAttributes cur_Attributes;

    public GAttributeManager(double x, double y, Color col, Boolean filled, int lineWidth, int width, int height, double x2, double y2, Boolean visible)
    {
        for (int i = 0; i< StateManager.total_State+1; i++)
        {
            if (i<getCurrent_State())
                state_Attributes_list.add(null);
            else
            {
                GAttributes attributes = new GAttributes(x,y,col,filled,lineWidth, width, height, x2, y2, visible);
                state_Attributes_list.add(i,attributes);
            }
        }
        updateCur_Attributes();
    }

    public int getCurrent_State()
    {
        return StateManager.current_State;
    }

    public GAttributes getCur_Attributes()
    {
        //return state_Attributes_map.get(getCurrent_State());
        return state_Attributes_list.get(getCurrent_State());
    }

    public void updateCur_Attributes()
    {
        if (getCur_Attributes() != null)
            this.cur_Attributes = getCur_Attributes();
    }

    public void insertState() throws CloneNotSupportedException
    {
        GAttributes attributes = new GAttributes();
        if (!state_Attributes_list.isEmpty())
        {
            if(state_Attributes_list.get(getCurrent_State()-1) == null)
                attributes = null;
            else
                attributes = (GAttributes) state_Attributes_list.get(getCurrent_State()-1).clone();
        }

        state_Attributes_list.add(getCurrent_State(), attributes);
        updateCur_Attributes();
    }
}
