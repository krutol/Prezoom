package prezoom.view;

import prezoom.controller.CameraManager;
import prezoom.model.AttributeMapI;
import prezoom.view.InspectorPanel.RowEditorModel;
import prezoom.view.InspectorPanel.CustomTableModel;
import prezoom.view.InspectorPanel.PanelKeyboardListener;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26<p>
 **/
public class CameraInspectorPanel extends JPanel
{
    private final InspectorPanel.JInspectorTable jInspectorTable;
    private final CustomTableModel customTableModel;
    private final RowEditorModel rowEditorModel;
    private final PanelKeyboardListener panelKeyListener;

    //construct the camera inspector panel
    public CameraInspectorPanel()
    {
        //set background for the panel
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        //create a titled border on the camera inspector panel
        TitledBorder title = BorderFactory.createTitledBorder("Camera Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        //two column names
        String[] col_names = {"Names", "Values"};

        //create a row editor model for inspector table
        rowEditorModel = new RowEditorModel();

        //create a custom table model for inspector table
        customTableModel = new CustomTableModel(rowEditorModel, col_names, 6);

        //create the inspector table
        jInspectorTable = new InspectorPanel.JInspectorTable(customTableModel);
        jInspectorTable.setRowEditorModel(rowEditorModel);

        //create a key listener for camera inspector attribute values
        panelKeyListener = new CameraPanelKeyboardListener(jInspectorTable);

        //add an scroll pane
        JScrollPane scrollPane = new JScrollPane(jInspectorTable);
        add(scrollPane);
        jInspectorTable.setPreferredScrollableViewportSize(new Dimension(100, 200));
    }

    /**
     * Set the values in the inspector panel text boxes
     * x, y, width, height
     */
    public void rearrangeValues()
    {
        if (CameraManager.getCorrectCamera() == null)
            return;

        AttributeMapI currAttr = CameraManager.getCorrectCamera();

        //clear the row editor model
        jInspectorTable.getRowEditorModel().clear();
        Map<String, Method> cur_map = currAttr.validGetterMap();
        Map<String, Method> setter_map = currAttr.validSetterMap();

        //set the attribute names in custom table model
        customTableModel.setPropNames(cur_map.keySet().toArray(new String[0]));
        int i = 0;
        Component editedComp;

        //iterate through each entry in the getter map
        for (Map.Entry<String, Method> entry : cur_map.entrySet())
        {
            //get the attribute name at the with position in the custom table model
            String attr = customTableModel.getAttribute(i);
            try
            {
                if (attr != null)
                {
                    //add the text field to handle double values in the attribute value fields
                    editedComp = new JTextField(entry.getValue().invoke(currAttr).toString());

                    //add key listener
                    editedComp.addKeyListener(panelKeyListener);

                    //create a cell editor at the with(row count), column 1
                    DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);

                    //add the editor to the row editor model
                    jInspectorTable.getRowEditorModel().addEditorForRow(i, ed);
                }
            } catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
            i++;
        }
        customTableModel.fireTableDataChanged();
    }

    //key listener class to handle key events for camera inspector table cells
    private class CameraPanelKeyboardListener extends PanelKeyboardListener
    {
        CameraPanelKeyboardListener(InspectorPanel.JInspectorTable table)
        {
            super(table);
        }

        //set current (currAttr) attribute to cameraInfo from camera manager
        public void setCurrAttr()
        {
            if (CameraManager.getCorrectCamera() == null)
                return;

            currAttr = CameraManager.getCorrectCamera();
        }
    }

}

