package prezoom.view.table;

import prezoom.model.AttributeMapI;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26<p>
 **/

//extending from JTable to model row editor model per row
public class InspectorTable extends JTable
{
    private Class<?> editorClass;

    public InspectorTable(TableModel tm)
    {
        super(tm);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setPreferredScrollableViewportSize(new Dimension(100, 200));
        this.setDefaultRenderer(Color.class, new ColorRenderer(false));
        this.setDefaultEditor(Color.class, new ColorEditor());
        this.putClientProperty("terminateEditOnFocusLost", true);
    }

    //get the editor from row editor model at a cell identified
    //by row, col
    public TableCellEditor getCellEditor(int row, int col)
    {
        Object obj = getValueAt(row, col);
        if (obj != null)
            return getDefaultEditor(obj.getClass());


        return super.getCellEditor(row, col);
    }

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

    public TableCellRenderer getCellRenderer(int row, int col)
    {
        Object obj = getValueAt(row, col);
        if (obj != null)
            return getDefaultRenderer(obj.getClass());

        return super.getCellRenderer(row, col);
    }

    public Class<?> getColumnClass(int col)
    {
        if (editorClass != null)
            return editorClass;

        return super.getColumnClass(col);
    }

    public void resetTable()
    {
        ((InspectorTableModel)this.getModel()).prop_map.clear();
    }

    /**
     * Set the values in the inspector panel text boxes
     * x, y, width, height
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
        getterMap.remove("preZoomFactor");
        String[] names = getterMap.keySet().toArray(new String[0]);

        if (!Arrays.equals(model.getPropNames(), names))
            model.setPropNames(getterMap.keySet().toArray(new String[0]));

        for (int i = 0; i < names.length; i++)
        {
            try
            {
                model.setValueAt(getterMap.get(names[i]).invoke(currAttr), i, 1);
            } catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }

        model.isRearranging = false;
    }

}

