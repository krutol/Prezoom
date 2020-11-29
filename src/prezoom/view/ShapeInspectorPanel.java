package prezoom.view;

import prezoom.controller.GObjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * TODO
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/3<p>
 * Changed(Abhishek Sharma):
 * --> class: InspectorPanel method:rearrangeValues()
 * --code: update the Inspector panel with the selectedObj attributes in centerCanvas
 * <p>
 * --> added class:PanelKeyboardListner, method: keyPressed()
 * --code:update center canvas object with the values in inspector panel attributes
 **/

public class ShapeInspectorPanel extends AbstractInspectorPanel
{

    public ShapeInspectorPanel(String title){
        super(title);
    }

    //set the current attribute from GObjectManager
    public void setCurrAttr() {
        if (GObjectManager.inspectedObj == null)
            return;

        curr_attr = GObjectManager.inspectedObj.getCurrentAttributes();
    }


    protected void addAttributesToTable(Map<String, Method> getterMap, Map<String, Method> setterMap)
    {
        Component editedComp;
        int i = 0;
        //iterate through getter map
        for (Map.Entry<String, Method> entry : getterMap.entrySet())
        {
            //get the attribute corresponding to the index of attribute
            //in the custom table model
            String attr = customTableModel.getAttribute(i);
            try
            {
                switch (Objects.requireNonNull(attr))
                {
                    case "color":
                    {
                        editedComp = new JTextField("");

                        //case color, set the color attribute for the JTextField
                        editedComp.setBackground((Color) entry.getValue().invoke(curr_attr));

                        //add the mouselistener
                        /*
                        editedComp.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                super.mouseClicked(e);
                                try
                                {
                                    //if clicked on the color value holding cell show
                                    //color chooser dialog
                                    Color color = JColorChooser.showDialog(MainWindow.centerCanvas,
                                            "Select a color", (Color) getterMap.get("color").invoke(curr_attr));

                                    //set the text field background to the color that has been
                                    //selected by the color chooser
                                    ((JTextField) e.getSource()).setBackground(color);

                                    //edit the last+1 row to get the focus out of color textbox
                                    jTableInspector.editCellAt(rowEditorModel.getSize(), 0, null);
                                    jTableInspector.requestFocus();

                                    //set the value of attribute for the shape
                                    invokeSetter(setterMap, entry.getKey(), curr_attr, color);
                                } catch (IllegalAccessException | InvocationTargetException err)
                                {
                                    err.printStackTrace();
                                }

                            }
                        });


                         */
                        editedComp.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                try
                                {
                                    //if clicked on the color value holding cell show
                                    //color chooser dialog
                                    Color color = JColorChooser.showDialog(MainWindow.centerCanvas,
                                            "Select a color", (Color) getterMap.get("color").invoke(curr_attr));

                                    //set the text field background to the color that has been
                                    //selected by the color chooser
                                    ((JTextField) e.getSource()).setBackground(color);

                                    //edit the last+1 row to get the focus out of color textbox
                                    jTableInspector.editCellAt(rowEditorModel.getSize(), 0, null);
                                    jTableInspector.requestFocus();

                                    //set the value of attribute for the shape
                                    invokeSetter(setterMap, entry.getKey(), curr_attr, color);
                                } catch (IllegalAccessException | InvocationTargetException err)
                                {
                                    err.printStackTrace();
                                }

                            }

                            @Override
                            public void focusLost(FocusEvent arg0) {
                            }
                        });

                        //create a custom cell editor for the text fild view
                        DefaultCellEditor ed = new AbstractInspectorPanel.CustomCellEditor((JTextField) editedComp);

                        //add the editor for a row
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    //if its boolean visible or filled
                    case "visible":
                    case "filled":
                    {
                        //boolean values will be displayed using checkbox view field
                        editedComp = new JCheckBox((((Boolean) entry.getValue().invoke(curr_attr))).toString() + "");
                        ((JCheckBox) editedComp).addItemListener(e ->
                        {
                            if (e.getStateChange() == ItemEvent.SELECTED)
                                invokeSetter(setterMap, entry.getKey(), curr_attr, Boolean.TRUE);
                            else
                                invokeSetter(setterMap, entry.getKey(), curr_attr, Boolean.FALSE);

                        });
                        DefaultCellEditor ed = new DefaultCellEditor((JCheckBox) editedComp);
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    case "lineWidth":
                    {
                        //the view component for linewidth or integer is textfield
                        editedComp = new JTextField(entry.getValue().invoke(curr_attr) + "");
                        editedComp.addKeyListener(panelKeyListener);
                        DefaultCellEditor ed = new AbstractInspectorPanel.CustomCellEditor((JTextField) editedComp);
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    //default is for double values and the view component is textfield
                    default:
                    {
                        editedComp = new JTextField(entry.getValue().invoke(curr_attr).toString());
                        editedComp.addKeyListener(panelKeyListener);
                        DefaultCellEditor ed = new AbstractInspectorPanel.CustomCellEditor((JTextField) editedComp);
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                }
                if(editedComp instanceof JTextField) {
                    String textString = ((JTextField) editedComp).getText();
                    ((JTextField) editedComp).setSelectionStart(0);
                    ((JTextField) editedComp).setSelectionEnd(textString.length());
                }

            } catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
            i++;
        }

    }
}
