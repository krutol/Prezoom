package prezoom.view.table;

import prezoom.model.AttributeMapI;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26<p>
 **/

//custom table model with row editor model
public class InspectorTableModel extends DefaultTableModel
{

    //initialize column names which are two one to hold attribute name
    //and other to hold attribute value
    //set the row editor model for this custom table model
    public InspectorTableModel(String[] col_names, int i)
    {
        super(col_names, i);
        this.addTableModelListener(e ->
        {
            if (isRearranging
                    || e.getFirstRow() != e.getLastRow()
                    || inspectedAtt == null)
                return;

            try
            {
                String name = (String) getValueAt(e.getLastRow(), 0);
                Object value = getValueAt(e.getLastRow(), 1);
                setter_map.get(name).invoke(inspectedAtt, value);
            } catch (IllegalAccessException | InvocationTargetException illegalAccessException)
            {
                illegalAccessException.printStackTrace();
            }
        });
    }

    //attribute names
    public String[] prop_names = {"Label", "X", "Y",
            "Width", "Height"};

    public Map<Integer, Object> prop_map = new HashMap<>();

    public Map<String, Method> setter_map;

    public AttributeMapI inspectedAtt;

    public boolean isRearranging = false;

    //set all the attributes for a table
    public void setPropNames(String[] props)
    {
        this.prop_names = props;
        fireTableDataChanged();
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
        if (prop_map.size() <= row)
            return null;

        //get the value of the attribute from editor
        return prop_map.get(row);
        //return super.getValueAt(row,color);
    }

    public void setValueAt(Object value, int row, int col)
    {
        if (col != 1)
            return;

        prop_map.put(row,value);
        fireTableCellUpdated(row, col);
    }

    //column 0 is uneditable, other columns are editable
    public boolean isCellEditable(int row, int col)
    {
        return col != 0;
    }
}
