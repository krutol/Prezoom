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

/*
 * @author: Abhishek Sharma
 * This class is the abstract for Inspector panels
 **/

public abstract class AbstractInspectorPanel extends JPanel
{
    public final JTableInspector jTableInspector;
    protected final CustomTableModel customTableModel;
    protected final RowEditorModel rowEditorModel;
    protected PanelKeyboardListener panelKeyListener;
    protected String attrToRemove = null;

    protected AttributeMapI curr_attr;

    protected String title;

    //construct the inspector panel
    //initialize the customtablemodel, row editor model and JInspector table
    //and add to the panel
    public AbstractInspectorPanel(String title){
        this.title = title;
        setBackground(Color.darkGray);
        //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.white);
        setBorder(titledBorder);

        String[] col_names = {"Names", "Values"};
        rowEditorModel = new RowEditorModel();
        customTableModel = new CustomTableModel(rowEditorModel, col_names, 12);

        jTableInspector = new JTableInspector(customTableModel);
        jTableInspector.setRowEditorModel(rowEditorModel);

        setPanelKeyListener();
            //GAttributesI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();

//        JTextField tf = new JTextField("label");
//        DefaultCellEditor ed = new DefaultCellEditor(tf);
//        // tell the RowEditorModel to use ed for row 1
//        //rowEditorModel.addEditorForRow(0,ed);
//
//        tf = new JTextField("text");
//        ed = new DefaultCellEditor(tf);
//        // tell the RowEditorModel to use ed for row 1
//        //rowEditorModel.addEditorForRow(1,ed);
//
//        tf = new JTextField("");
//        ed = new DefaultCellEditor(tf);
//        //rowEditorModel.addEditorForRow(2,ed);
//
//        tf = new JTextField("");
//        ed = new DefaultCellEditor(tf);
//        //rowEditorModel.addEditorForRow(3,ed);
//
//        tf = new JTextField("");
//        ed = new DefaultCellEditor(tf);
            //rowEditorModel.addEditorForRow(4,ed);

            JScrollPane scrollPane = new JScrollPane(jTableInspector);
            add(scrollPane);
            jTableInspector.setPreferredScrollableViewportSize(new Dimension(100, 200));
    }

    public void setPanelKeyListener()
    {
        panelKeyListener = new PanelKeyboardListener(jTableInspector);
    }
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
        public int valueExistsAtCell(JTableInspector table, Object e)
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
    public static class JTableInspector extends JTable
    {
        protected RowEditorModel rm;

        //default constructor
        public JTableInspector()
        {
            super();
            rm = null;
        }

        public JTableInspector(TableModel tm)
        {
            super(tm);
            rm = null;
        }

        public JTableInspector(TableModel tm, TableColumnModel cm)
        {
            super(tm, cm);
            rm = null;
        }

        public JTableInspector(TableModel tm, TableColumnModel cm,
                               ListSelectionModel sm)
        {
            super(tm, cm, sm);
            rm = null;
        }

        public JTableInspector(int rows, int cols)
        {
            super(rows, cols);
            rm = null;
        }

        /*
        public JTableX(final Vector rowData, final Vector columnNames)
        {
            super(rowData, columnNames);
            rowEditorModel = null;
        }

         */
        public JTableInspector(final Object[][] rowData, final Object[] colNames)
        {
            super(rowData, colNames);
            rm = null;
        }

        // new constructor
        public JTableInspector(TableModel tm, RowEditorModel rm)
        {
            super(tm, null, null);
            this.rm = rm;
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

        //set the fous to last row first column
        public void setFocusOnLastRowFirstColumn(Object view){

            //if view being edited is present in the table
            if(getRowEditorModel().valueExistsAtCell(this, view)>-1){

                //edit cell at last row first column to remove
                //focus from the currently being edited cell
                this.editCellAt(getRowEditorModel().getSize(), 0);
            }
        }

        //set the fous to last row first column
        public void setFocusOnLastRowFirstColumn(){
            //edit the cell in last row first column
            this.editCellAt(getRowEditorModel().getSize(), 0);
        }

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
    public static class CustomCellEditor extends DefaultCellEditor
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

    protected void invokeSetter(Map<String, Method> setterMap, String key, AttributeMapI attr, Object param)
    {
        try
        {
            setterMap.get(key).invoke(attr,param);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    abstract protected void setCurrAttr();

    protected void buildTable(){
        //set the cell renderer for the table for column 1
        TableColumn tcol = jTableInspector.getColumnModel().getColumn(1);
        tcol.setCellRenderer(new CustomTableCellRenderer());

        //clear the row editor model before refreshing custom table model
        jTableInspector.getRowEditorModel().clear();
        Map<String, Method> cur_map = removeAttribute(curr_attr.validGetterMap());
        Map<String, Method> setter_map = removeAttribute(curr_attr.validSetterMap());

        //refresh the custom table model with the attributes of the selected shape
        customTableModel.setPropNames(cur_map.keySet().toArray(new String[0]));

        //add attribute names and values to the table
        addAttributesToTable(cur_map, setter_map);

        //once the values have been set fire table data changed to update the table
        customTableModel.fireTableDataChanged();

    }


    abstract protected void addAttributesToTable(Map<String, Method> getterMap, Map<String, Method> setterMap);

    //remove attribute which does not need to show in the inspector
    protected Map<String, Method> removeAttribute(Map<String, Method> map){
        if(attrToRemove != null)
            map.remove(attrToRemove);

        return map;
    }
    /**
     * Set the values in the inspector panel textboxes
     * x, y, width, height
     */
    public void rearrangeValues()
    {
        setCurrAttr();

        //if curr_attr is null return
        if (curr_attr == null)
            return;

        //build table for attributes
        buildTable();
    }

    //key listener for the inspector panel
    protected class PanelKeyboardListener implements KeyListener
    {
        AttributeMapI currAttr;
        JTableInspector table;

        PanelKeyboardListener(JTableInspector table){
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

        public void keyDown(KeyEvent e) {
        }
        //handle keypressed event
        public void keyPressed(KeyEvent e)
        {
            //((JTextField)e.getSource()).setSelectionStart(((JTextField)e.getSource()).getCaretPosition());
        }

        /**
         * Handle the key-released event from the text field.
         */
        public void keyReleased(KeyEvent e)
        {
            JTextField field = (JTextField) e.getSource();
            setCurrAttr();
            if (currAttr == null)
                return;

            //get the getter and setter map
            Map<String, Method> getter_map = removeAttribute(currAttr.validGetterMap());
            Map<String, Method> cur_map = removeAttribute(currAttr.validSetterMap());

            String text = "";

            //check if the event source i.e. the view component is present in the
            //row editor model if so return the position which is the row at which
            //attribute is stored
            int exists = table.getRowEditorModel().valueExistsAtCell(table, (JTextField)e.getSource());
            if(exists>-1){
                //if event source is present
                //get the attribute name
                String attr = ((CustomTableModel)table.getModel()).getAttribute(exists);
                String textBoxText = "";
                String selectedText = null;
                int startSelection = -1;
                int endSelection = -1;

                JTextField source = null;

                if(e.getSource().getClass().equals(JTextField.class)) {
                    source = (JTextField)e.getSource();

                    //  get the textbox value
                    textBoxText = ((JTextField) e.getSource()).getText();

                    //  get the selection start
                    startSelection = ((JTextField) e.getSource()).getSelectionStart();

                    // get selection end
                    endSelection = ((JTextField) e.getSource()).getSelectionEnd();

                    //  if start greater than end stores smaller value in start
                    //  and store greater value in end
                    if(startSelection > endSelection) {
                        int temp = startSelection;
                        startSelection = endSelection;
                        endSelection = temp;
                    }
                }else if(e.getSource().getClass().equals(JComboBox.class)){

                }

                //if keycode is backspace remove the last character from the value of the textbox
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    if (textBoxText.length() > 0) {
                        //  if there is a selection
                        if(startSelection >- 1 && endSelection > -1
                                && startSelection != endSelection){
                            //  if end is length of the text
                            if(endSelection == textBoxText.length()+1) {
                                text = textBoxText.substring(0, startSelection);
                            }else
                                text = textBoxText.substring(0, startSelection)+textBoxText.substring(endSelection+1);
                        }else {
                            text = textBoxText.substring(0, textBoxText.length());
                        }
                    }
                } else  //else add the char being entered to the textbox value
                {
                    text = textBoxText;
                }
                try{

                    //invoke the get method from the getter map
                    Object value = ((Method)getter_map.get(attr)).invoke(currAttr);

                    //if the value is double
                    if(value instanceof Double){
                        //if textbox is empty set the 0.0 as value
                        if (text.length() == 0)
                            ((Method)cur_map.get(attr)).invoke(currAttr, new Double(0.0));

                            //else update the value from textbox by calling the setter method
                            // on the method object using setter map
                        else
                            ((Method)cur_map.get(attr)).invoke(currAttr, Double.parseDouble(text));

                    }else if(value instanceof Integer){
                        //if textbox is empty set the 0 as value
                        if (text.length() == 0)
                            ((Method)cur_map.get(attr)).invoke(attr, new Integer(0));
                        else
                            ((Method)cur_map.get(attr)).invoke(currAttr, Integer.parseInt(text));

                    }else if(value instanceof Boolean){
                        //if textbox is empty set the 0 as value
                        if (text.length() == 0)
                            ((Method)cur_map.get(attr)).invoke(currAttr, new Boolean(false));
                        else
                            ((Method)cur_map.get(attr)).invoke(currAttr, Boolean.parseBoolean(text));

                    }else if(value instanceof Color){
                        //if textbox is empty set the color as default
                        if (text.length() == 0)
                            ((Method)cur_map.get(attr)).invoke(currAttr, new Color(0,0,0));
                        else
                            ((Method)cur_map.get(attr)).invoke(currAttr, value);

                    }else{
                        if (text.length() == 0)
                            ((Method)cur_map.get(attr)).invoke(currAttr, 0.0);
                        else
                            ((Method)cur_map.get(attr)).invoke(currAttr, Double.parseDouble(text));
                    }

                }catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex){
                    System.out.println(ex);
                }
            }
        }
    }

}

