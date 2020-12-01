package prezoom.view.table;

import prezoom.model.AttributeMapI;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The custom table model for the inspector table
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26
 **/
public class InspectorTableModel extends DefaultTableModel
{
    /**
     * the constructor, adding a {@link javax.swing.event.TableModelListener}.
     * if the table is changed, invoke the corresponding setter from the {@link AttributeMapI#validSetterMap()}
     * to change the value of the {@link #inspectedAtt}
     * @param col_names all the attribute names in the first column
     * @param i the row number
     */
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

    /**
     * the attribute names in the 1st column
     */
    private ArrayList<String> prop_names = new ArrayList<>(Arrays.asList("Label", "X", "Y",
            "Width", "Height"));

    /**
     * the data in the 2nd column
     */
    public Map<Integer, Object> prop_map = new HashMap<>();

    /**
     * the setter map to change values of the {@link #inspectedAtt}
     */
    public Map<String, Method> setter_map;

    /**
     * the source of the table
     */
    public AttributeMapI inspectedAtt;

    /**
     * the table is rearranging by calling {@link InspectorTable#rearrangeValues(AttributeMapI)}.
     * ignore table changes event.
     */
    public boolean isRearranging = false;

    /**
     * set the attribute names in the 1st column
     * @param props the attribute name list
     */
    public void setPropNames(ArrayList<String> props)
    {
        this.prop_names = props;
        fireTableDataChanged();
    }

    /**
     * get the attribute name list in the 1st column
     * @return the attribute name list
     */
    public ArrayList<String> getPropNames()
    {
        return this.prop_names;
    }

    /**
     * get the value of the table,
     * names are stored in 1st column
     * values are stored in 2nd column
     * @param row row number
     * @param col column number
     * @return the value at the given row and column
     */
    public Object getValueAt(int row, int col)
    {
        //if row is greater than the total attributes length return null
        if (row > prop_names.size() - 1)
            return null;

        //if column is 0 then return the name of the attribute
        //as names are stored at nth row column 0
        if (col == 0)
            return prop_names.get(row);

        //return null if there is no value stored for editor for that row
        if (prop_map.size() <= row)
            return null;

        //get the value of the attribute from editor
        return prop_map.get(row);
        //return super.getValueAt(row,color);
    }

    /**
     * change the value at the given row and column
     * @param value the new value
     * @param row row number
     * @param col column number
     */
    public void setValueAt(Object value, int row, int col)
    {
        if (col != 1)
            return;

        prop_map.put(row,value);
        fireTableCellUpdated(row, col);
    }

    /**
     * the 1st name column cannot be edited
     * @param row row number
     * @param col column number
     * @return true if column != 0
     */
    public boolean isCellEditable(int row, int col)
    {
        return col != 0;
    }
}
