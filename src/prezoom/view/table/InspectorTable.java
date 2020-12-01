package prezoom.view.table;

import prezoom.model.AttributeMapI;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * The custom table that can display the attributes of objects
 * and each row has corresponding editor and renderer according to the class type of the row.
 * using {@link AttributeMapI#validGetterMap()} and {@link AttributeMapI#validSetterMap()} to update table
 * and edit values so that unnecessary switch/case and if statements are avoid
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26<p>
 **/
public class InspectorTable extends JTable
{
    /**
     * The Class type for the current editing cell
     */
    private Class<?> editorClass;

    /**
     * The constructor
     * @param tm the table model
     */
    public InspectorTable(TableModel tm)
    {
        super(tm);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setPreferredScrollableViewportSize(new Dimension(100, 200));
        this.setDefaultRenderer(Color.class, new ColorRenderer(false));
        this.setDefaultEditor(Color.class, new ColorEditor());
        this.putClientProperty("terminateEditOnFocusLost", true);
    }

    /**
     * get the editor according to the Class type of each cell
     * @param row row number
     * @param col column number
     * @return the cell editor
     */
    public TableCellEditor getCellEditor(int row, int col)
    {
        Object obj = getValueAt(row, col);
        if (obj != null)
            return getDefaultEditor(obj.getClass());


        return super.getCellEditor(row, col);
    }

    /**
     * use this method to let each row have different cell editor.
     * Set the {@link #editorClass} as the class type of the given cell before calling the super's method
     * because in the super's method, {@link JTable#prepareEditor(TableCellEditor, int, int)}, it will call
     * {@link #getColumnClass(int)} to determine the cell's editor type. By default, the JTable does not have
     * the function to set the class type for each row.
     * @see #getColumnClass(int)
     * @param editor  the <code>TableCellEditor</code> to set up
     * @param row     the row of the cell to edit,
     *                where 0 is the first row
     * @param column  the column of the cell to edit,
     *                where 0 is the first column
     * @return the <code>Component</code> being edited
     */
    public Component prepareEditor(TableCellEditor editor, int row, int column)
    {
        Object obj = getValueAt(row, column);
        Component component;
        if (obj != null)
        {
            this.editorClass = obj.getClass();//The class type of each row
            component = super.prepareEditor(editor,row,column);
            this.editorClass = null;
        }else
        {
            component = super.prepareEditor(editor,row,column);
        }

        return component;

    }

    /**
     * get the renderer according to the Class type of each cell
     * @param row row number
     * @param col column number
     * @return the cell renderer
     */
    public TableCellRenderer getCellRenderer(int row, int col)
    {
        Object obj = getValueAt(row, col);
        if (obj != null)
            return getDefaultRenderer(obj.getClass());

        return super.getCellRenderer(row, col);
    }

    /**
     * return the class type.
     * return {@link #editorClass} when it is not null.
     * otherwise return super
     * @param col the column number
     * @return the class type
     */
    public Class<?> getColumnClass(int col)
    {
        if (editorClass != null)
            return editorClass;

        return super.getColumnClass(col);
    }

    /**
     * clear the data of the table
     */
    public void resetTable()
    {
        ((InspectorTableModel)this.getModel()).prop_map.clear();
    }

    /**
     * update the values to the inspector table
     */
    public void rearrangeValues(AttributeMapI currAttr)
    {
        if (currAttr == null)
            return;

        //clear the row editor model before refreshing custom table model
        this.resetTable();
        //Map<String, Object> prop_Map = currAttr.validAttributeMap();
        Map<String, Method> getterMap = currAttr.validGetterMap();
        Map<String, Method> setterMap = currAttr.validSetterMap();

        //refresh the custom table model with the attributes of the selected shape
        InspectorTableModel model = (InspectorTableModel) this.getModel();
        model.isRearranging = true;
        model.setter_map = setterMap;
        model.inspectedAtt = currAttr;


        ArrayList<String> names = new ArrayList<>(getterMap.keySet());
        names.remove("preZoomFactor");
        names.remove("textString");
        if (!model.getPropNames().equals(names))
            model.setPropNames(names);

        for (int i = 0; i < names.size(); i++)
        {
            try
            {
                model.setValueAt(getterMap.get(names.get(i)).invoke(currAttr), i, 1);
            } catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }

        model.isRearranging = false;
    }

}

