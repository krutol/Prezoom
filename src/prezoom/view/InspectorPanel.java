package prezoom.view;

import prezoom.controller.GObjectManager;
import prezoom.model.AttributeMapI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.table.*;

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

public class InspectorPanel extends JPanel
{
    private final JInspectorTable jInspectorTable;
    private final CustomTableModel customTableModel;
    private final RowEditorModel rowEditorModel;
    private final PanelKeyboardListener panelKeyListener;
    private AttributeMapI curr_attr;

    //class to model each row model, which corresponds to single attribute in JTable
    //which means per attribute each row will have different model
    public static class RowEditorModel
    {
        //store table cell editors for each attribute in data
        private final Hashtable<Integer, TableCellEditor> data;

        public RowEditorModel()
        {
            data = new Hashtable<>();
        }

        //get the number of attribute stored in data
        public int getSize(){
            return data.size();
        }

        //add an editor for individual row
        public void addEditorForRow(int row, TableCellEditor e)
        {
            data.put(row, e);
        }

        //remove an editor for individual row
        public void removeEditorForRow(int row)
        {
            data.remove(row);
        }

        //get editor corresponding to n row which is the nth attribute in rowEditorModel
        public TableCellEditor getEditor(int row)
        {
            return data.get(row);
        }

        //clean the attribute editors map
        public void clear()
        {
            this.data.clear();
        }

        //check if the object e exists as attribute edior view component
        public int valueExistsAtCell(JInspectorTable table, Object e)
        {
            Iterator<Integer> iter = data.keys().asIterator();
            while (iter.hasNext())
            {
                Integer key = iter.next();
                String value = data.get(key).getCellEditorValue() + "";
                Component comp;
                comp = data.get(key).getTableCellEditorComponent(table, value, true, key, 1);
                //System.out.println("here-"+comp);
                //System.out.println("here-"+e);

                if (comp.equals(e))
                {
                    return key;
                }
            }
            return -1;
        }

    }

    //custom table model with row editor model
    public static class CustomTableModel extends DefaultTableModel
    {
        //row editor model
        RowEditorModel localRowEditorModel;

        //initialize column names which are two one to hold attribute name
        //and other to hold attribute value
        //set the row editor model for this custom table model
        public CustomTableModel(RowEditorModel rowEditorModel, String[] col_names, int i)
        {
            super(col_names, i);
            this.localRowEditorModel = rowEditorModel;
        }

        //attribute names
        public String[] prop_names = {"Label", "X", "Y",
                "Width", "Height"};

        //set all the attributes for a table
        public void setPropNames(String[] props)
        {
            this.prop_names = props;

        }

        //get the attributes
        public String[] getPropNames()
        {
            return this.prop_names;
        }

        //get the name and value of the attribute, names are stored in nth row and column 0
        //values are stored in nth row and column 1
        public Object getValueAt(int row, int col)
        {
            //if row is greater than the total attributes length return null
            if (row > prop_names.length - 1)
                return null;

            //if column is 0 then return the name of the attribute
            //as names are stored at nth row column 0
            if (col == 0)
                return prop_names[row];

            //return null if there is no value stored for editor for that row
            if (localRowEditorModel.getEditor(row) == null)
                return null;

            //get the value of the attribute from editor
            return localRowEditorModel.getEditor(row).getCellEditorValue();
            //return super.getValueAt(row,color);
        }

        //get the nth attribute name from column 0
        public String getPropName(int row)
        {
            if (row > -1)
                return prop_names[row];
            return null;
        }

        //get the value from the prop_names which corresponds to the attribute names
        public String getAttribute(int i)
        {
            if (i > this.prop_names.length - 1)
                return null;
            return this.prop_names[i];
        }

        //column 0 is ineditable, other columns are editable
        public boolean isCellEditable(int row, int col)
        {
            return col != 0;
        }
    }

    //extending from JTable to model row editor model per row
    public static class JInspectorTable extends JTable
    {
        protected RowEditorModel rm;

        public JInspectorTable(TableModel tm)
        {
            super(tm);
            rm = null;
        }

        //set the row editor model for the table
        public void setRowEditorModel(RowEditorModel rm)
        {
            this.rm = rm;
        }

        //get the row editor model for the table
        public RowEditorModel getRowEditorModel()
        {
            return rm;
        }

        //get the editor from row editor model at a cell identified
        //by row, col
        public TableCellEditor getCellEditor(int row, int col)
        {
            TableCellEditor tmpEditor = null;
            if (rm != null)
                tmpEditor = rm.getEditor(row);
            if (tmpEditor != null)
                return tmpEditor;
            return super.getCellEditor(row, col);
        }
    }

    //construct the inspector panel
    //initialize the customtablemodel, row editor model and JInspector table
    //and add to the panel
    public InspectorPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        TitledBorder title = BorderFactory.createTitledBorder("Shape Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        String[] col_names = {"Names", "Values"};
        rowEditorModel = new RowEditorModel();
        customTableModel = new CustomTableModel(rowEditorModel, col_names, 12);

        jInspectorTable = new JInspectorTable(customTableModel);
        jInspectorTable.setRowEditorModel(rowEditorModel);
        panelKeyListener = new PanelKeyboardListener(jInspectorTable);

        JScrollPane scrollPane = new JScrollPane(jInspectorTable);
        add(scrollPane);
        jInspectorTable.setPreferredScrollableViewportSize(new Dimension(100, 200));
    }

    //table cell renderer class to paint the background of cell holding the value of the color
    //attribute to show the color on the cell background
    class CustomTableCellRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object obj, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cell = super.getTableCellRendererComponent(
                    table, obj, isSelected, hasFocus, row, column);
            if (GObjectManager.inspectedObj != null)
            {
                //GAttributesI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
                if (curr_attr != null)
                {
                    String attr = customTableModel.getAttribute(row);
                    //System.out.println("attr-" + attr + ", row-" + row);
                    if (attr != null && attr.equals("color"))
                    {
                        try
                        {
                            cell.setBackground((Color) curr_attr.validGetterMap().get("color").invoke(curr_attr));
                        } catch (IllegalAccessException | InvocationTargetException e)
                        {
                            e.printStackTrace();
                        }
                    } else if (attr == null)
                    {
                    } else
                        cell.setBackground(Color.white);
                }
            }
            return cell;
        }
    }

    //class to implement select a cell with single click in Jtable
    private static class CustomCellEditor extends DefaultCellEditor
    {

        public CustomCellEditor(JTextField comp)
        {
            super(comp);
        }

        public boolean isCellEditable(EventObject evt)
        {
            if (evt instanceof MouseEvent)
            {
                int clickCount;

                // For single-click activation
                clickCount = 1;

                // For double-click activation
                //clickCount = 2;

                // For triple-click activation
                //clickCount = 3;

                return ((MouseEvent) evt).getClickCount() >= clickCount;
            }
            return true;
        }
    }

    private void invokeSetter(Map<String, Method> setterMap, String key, AttributeMapI attr, Object param)
    {
        try
        {
            setterMap.get(key).invoke(attr,param);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set the values in the inspector panel textboxes
     * x, y, width, height
     */
    public void rearrangeValues()
    {
        //if inspected object is not available return null
        if (GObjectManager.inspectedObj == null)
            return;

        //get the AttrbuteMapI interface from the inspectedObj
        AttributeMapI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
        curr_attr = currAttr;
        if (currAttr == null)
            return;

        //set the cell renderer for the table for column 1
        TableColumn tcol = jInspectorTable.getColumnModel().getColumn(1);
        tcol.setCellRenderer(new CustomTableCellRenderer());

        //clear the row editor model before refreshing custom table model
        jInspectorTable.getRowEditorModel().clear();
        Map<String, Method> cur_map = currAttr.validGetterMap();
        Map<String, Method> setter_map = currAttr.validSetterMap();

        //refresh the custom table model with the attributes of the selected shape
        customTableModel.setPropNames(cur_map.keySet().toArray(new String[0]));
        int i = 0;
        Component editedComp;

        //iterate through getter map
        for (Map.Entry<String, Method> entry : cur_map.entrySet())
        {
            //get the attribute corresponding to the index of attribute in the
            //in the custom table model
            String attr = customTableModel.getAttribute(i);
            try
            {

                switch (Objects.requireNonNull(attr))
                {
                    case "color":
                    {
                        editedComp = new JTextField("");

                        //case color set the color attribute for the JTextField
                        editedComp.setBackground((Color) entry.getValue().invoke(currAttr));

                        //add the mouselistener
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
                                            "Select a color", (Color) cur_map.get("color").invoke(currAttr));

                                    //set the text field background to the color that has been
                                    //selected by the color chooser
                                    ((JTextField) e.getSource()).setBackground(color);

                                    //edit the last+1 row to get the focus out of color textbox
                                    jInspectorTable.editCellAt(rowEditorModel.getSize(), 0, null);
                                    jInspectorTable.requestFocus();

                                    //set the value of attribute for the shape
                                    invokeSetter(setter_map, entry.getKey(), currAttr, color);
                                } catch (IllegalAccessException | InvocationTargetException err)
                                {
                                    err.printStackTrace();
                                }

                            }
                        });

                        //create a custom cell editor for the text fild view
                        DefaultCellEditor ed = new CustomCellEditor((JTextField) editedComp);

                        //add the editor for a row
                        jInspectorTable.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    //if its boolean visible or filled
                    case "visible":
                    case "filled":
                    {
                        //boolean values will be displayed using checkbox view field
                        editedComp = new JCheckBox((((Boolean) entry.getValue().invoke(currAttr))).toString() + "");
                        ((JCheckBox) editedComp).addItemListener(e ->
                        {
                            if (e.getStateChange() == ItemEvent.SELECTED)
                                invokeSetter(setter_map, entry.getKey(), currAttr, Boolean.TRUE);
                            else
                                invokeSetter(setter_map, entry.getKey(), currAttr, Boolean.FALSE);

                        });
                        DefaultCellEditor ed = new DefaultCellEditor((JCheckBox) editedComp);
                        jInspectorTable.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    case "lineWidth":
                    {
                        //the view component for linewidth or integer is textfield
                        editedComp = new JTextField(entry.getValue().invoke(currAttr) + "");
                        editedComp.addKeyListener(panelKeyListener);
                        DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                        jInspectorTable.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    //default is for double values and the view component is textfield
                    default:
                    {
                        editedComp = new JTextField(entry.getValue().invoke(currAttr).toString());
                        editedComp.addKeyListener(panelKeyListener);
                        DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                        jInspectorTable.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
            i++;
        }

        //once the values have been set fire table data changed to update the table
        customTableModel.fireTableDataChanged();
    }

    //key listner for the inspector panel
    protected static class PanelKeyboardListener implements KeyListener
    {
        AttributeMapI currAttr;
        JInspectorTable table;

        PanelKeyboardListener(JInspectorTable table){
            this.table = table;
        }
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        public void keyTyped(KeyEvent e)
        {
            //System.out.println(e+"KEY TYPED: ");
        }

        public void setCurrAttr() {
            if (GObjectManager.inspectedObj == null)
                return;

            currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
        }

        //handle keypressed event
        public void keyPressed(KeyEvent e)
        {
            setCurrAttr();
            if (currAttr == null)
                return;

            //get the getter and setter map
            Map<String, Method> getter_map = currAttr.validGetterMap();
            Map<String, Method> cur_map = currAttr.validSetterMap();

            String text = "";

            //check if the event source i.e. the view component is present in the
            //row editor model if so return the position which is the row at which
            //attrbute is stored
            int exists = table.getRowEditorModel().valueExistsAtCell(table, e.getSource());
            if(exists>-1){
                //if event source is present
                //get the attribute name
                String attr = ((CustomTableModel)table.getModel()).getAttribute(exists);
                String textBoxText = "";
                if(e.getSource().getClass().equals(JTextField.class)) {
                    textBoxText = ((JTextField) e.getSource()).getText();
                }else if(e.getSource().getClass().equals(JComboBox.class)){

                }

                //if keycode is backspace remove the last character from the value of the textbox
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    if (textBoxText.length() > 0)
                        text = textBoxText.substring(0, textBoxText.length() - 1);
                } else  //else add the char being entered to the textbox value
                {
                    text = textBoxText + e.getKeyChar();
                }

                try{

                    //invoke the get method from the getter map
                    Object value = getter_map.get(attr).invoke(currAttr);

                    //if the value is double
                    if(value instanceof Double){
                        //if textbox is empty set the 0.0 as value
                        if (text.length() == 0)
                            cur_map.get(attr).invoke(currAttr, 0.0);

                        //else update the value from textbox by calling the setter method
                            // on the method object using setter map
                        else
                            cur_map.get(attr).invoke(currAttr, Double.parseDouble(text));

                    }else if(value instanceof Integer){
                        //if textbox is empty set the 0 as value
                        if (text.length() == 0)
                            cur_map.get(attr).invoke(attr, 0);
                        else
                            cur_map.get(attr).invoke(currAttr, Integer.parseInt(text));

                    }else if(value instanceof Boolean){
                        //if textbox is empty set the 0 as value
                        if (text.length() == 0)
                            cur_map.get(attr).invoke(currAttr, Boolean.FALSE);
                        else
                            cur_map.get(attr).invoke(currAttr, Boolean.parseBoolean(text));

                    }else if(value instanceof Color){
                        //if textbox is empty set the color as default
                        if (text.length() == 0)
                            cur_map.get(attr).invoke(currAttr, new Color(0,0,0));
                        else
                            cur_map.get(attr).invoke(currAttr, value);

                    }else{
                        if (text.length() == 0)
                            cur_map.get(attr).invoke(currAttr, 0.0);
                        else
                            cur_map.get(attr).invoke(currAttr, Double.parseDouble(text));
                    }

                }catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex){
                    System.out.println(ex);
                }
            }
        }

        /**
         * Handle the key-released event from the text field.
         */
        public void keyReleased(KeyEvent e)
        {
            //System.out.println(e+"KEY RELEASED: ");
        }
    }

}
