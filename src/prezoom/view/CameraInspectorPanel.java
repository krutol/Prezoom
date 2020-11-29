/**
 *
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26<p>
 **/

package prezoom.view;

import prezoom.controller.CameraManager;
import prezoom.model.AttributeMapI;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CameraInspectorPanel extends AbstractInspectorPanel
{

    public CameraInspectorPanel(String title){
        super(title);
        attrToRemove = "preZoomFactor";
    }

    public void setCurrAttr() {
        if (CameraManager.getCorrectCamera() == null)
            return;

        AttributeMapI currAttr = CameraManager.getCorrectCamera();
        curr_attr = currAttr;
    }

    /**
     * Set the values in the inspector panel text boxes
     * x, y, width, height
     */
    public void addAttributesToTable(Map<String, Method> getterMap, Map<String, Method> setterMap)
    {
        Component editedComp;
        int i = 0;
        getterMap = removeAttribute(getterMap);
        setterMap = removeAttribute(setterMap);
        //iterate through each entry in the getter map
        for (Map.Entry<String, Method> entry : getterMap.entrySet())
        {
            //get the attribute name at the ith position in the custom table model
            String attr = customTableModel.getAttribute(i);
            try
            {
                if (attr!=null)
                {
                    //add the text field to handle double values in the attribute value fields
                    editedComp = new JTextField(entry.getValue().invoke(curr_attr).toString());

                    //add key listener
                    editedComp.addKeyListener(panelKeyListener);

                    //create a cell editor at the with(row count), column 1
                    DefaultCellEditor ed = new CustomCellEditor((JTextField) editedComp);

                    //add the editor to the row editor model
                    jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                }
            } catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
            i++;
        }
        customTableModel.fireTableDataChanged();
    }
    public void setPanelKeyListener()
    {
        panelKeyListener = new CameraPanelKeyboardListener(jTableInspector);
    }

    //key listener class to handle key events for camera inspector table cells
    private class CameraPanelKeyboardListener extends PanelKeyboardListener
    {
        CameraPanelKeyboardListener(JTableInspector table){
            super(table);
        }

        //set current (currAttr) attribute to cameraInfo from camera manager
        public void setCurrAttr() {
            if (CameraManager.getCorrectCamera() == null)
                return;

            currAttr = CameraManager.getCorrectCamera();
        }
    }
}

