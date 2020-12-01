package prezoom.controller;

import prezoom.model.GAttributes;
import prezoom.model.GAttributesI;
import prezoom.model.GObject.GObject;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/** This class is the manager to manage the state related functions for the attributes of graphical objects.
 *  Each {@link GObject} has a manager ot manage its own attributes.
 * @author Zhijie Lan<p>
 * create date: 2020/11/6
 **/
public class GAttributeManager implements Serializable
{
    /**
     * the list that store all attributes for each state.
     */
    ArrayList<GAttributesI> state_Attributes_list = new ArrayList<>();
    /**
     * the attribute for the current state
     */
    private GAttributesI cur_Attributes;

    /**
     * To construct a manager, the object's attributes will be generated and duplicated from the current state to the end state,
     * but attributes for states before the current state are set to 0, which means the object only exists from the current state.
     *
     * @param x location, x
     * @param y location, y
     * @param col paint color
     * @param filled whether filled
     * @param lineWidth width of lines
     * @param width width of the object, if applicable
     * @param height height of the object, if applicable
     * @param x2 x2 of the object, if applicable
     * @param y2 y2 of the object, if applicable
     * @param visible whether visible
     * @param fontName font name
     * @param fontSize font size
     * @param fontStyle plain, bold, or italic
     * @param textString the text content
     */
    public GAttributeManager(Double x, Double y, Color col, Boolean filled,
                             Integer lineWidth, Double width, Double height,
                             Double x2, Double y2, Boolean visible,
                             String fontName, Integer fontStyle, Double fontSize, String textString)
    {
        for (int i = 0; i< StateManager.getTotal_State_Number(); i++)
        {
            GAttributesI attributes;
            if (i!=getCurrent_State())
            {
                //state_Attributes_list.add(null);
                attributes = new GAttributes(x == null?null:0.0,
                        y == null?null:0.0, col == null?null:new Color(238,238,238), filled == null?null:false,
                        lineWidth == null?null:0, width == null?null:0.0, height == null?null:0.0,
                        x2 == null?null:0.0, y2 == null?null:0.0, visible == null?null:true,
                        fontName == null?null:"", fontStyle == null?null:0, fontSize == null?null:0.0,
                        textString==null?null:"");

            }
            else
            {
                attributes = new GAttributes(x, y, col, filled, lineWidth, width, height,
                        x2, y2, visible, fontName, fontStyle, fontSize, textString);
            }
            state_Attributes_list.add(i,attributes);
        }
        updateCur_Attributes();
    }

    /**
     * get the state form teh state manager
     * @return the current state number
     */
    private int getCurrent_State()
    {
        return StateManager.getCurrent_State();
    }

    /**
     * get the object's attribute for the current state
     * @return the attribute
     */
    public GAttributesI getCur_Attributes()
    {
        //return state_Attributes_map.get(getCurrent_State());
        if (getCurrent_State()< state_Attributes_list.size())
            return state_Attributes_list.get(getCurrent_State());
        else
            return null;
    }

    /**
     * update the {@link #cur_Attributes}.
     * and add interpolation from previous value to the current value
     * skip if there is no attribute for the current state
     */
    public void updateCur_Attributes()
    {
        GAttributesI preAttributes = cur_Attributes;
        cur_Attributes = getCur_Attributes();

        if (cur_Attributes != null)
        {
            InterpolationFactory.buildInterpolation(preAttributes, cur_Attributes);
        }

    }

    /**
     * insert a new attribute of a new state into {@link #state_Attributes_list} at the current state index.
     * The new attribute will clone the previous info of the current state
     */
    public void insertAttributeState()
    {
        GAttributesI attributes = new GAttributes();
        if (!state_Attributes_list.isEmpty())
        {
            if(state_Attributes_list.get(getCurrent_State()-1) == null)
                attributes = null;
            else
            {
                try
                {
                    attributes = state_Attributes_list.get(getCurrent_State()-1).clone();
                } catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        state_Attributes_list.add(getCurrent_State(), attributes);

        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_Attributes();
    }

    /**
     * delete the attribute of a given state
     * @param state the state to be deleted
     */
    public void deleteAttributeState(int state)
    {

        state_Attributes_list.remove(state);

        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_Attributes();
    }

    /**
     * after drawing, add cloned attributes to the following states
     */
    public void finishDrawingNew()
    {
        for (int i = getCurrent_State()+1; i< StateManager.getTotal_State_Number(); i++)
        {
            GAttributesI attributes = null;
            try
            {
                attributes = cur_Attributes.clone();
            } catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            state_Attributes_list.add(i,attributes);
        }

    }
}
