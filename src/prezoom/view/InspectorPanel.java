package prezoom.view;

import prezoom.controller.GObjectManager;
import prezoom.model.MethodMapI;

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
    private final JTableInspector jTableInspector;
    private final CustomTableModel customTableModel;
    private final RowEditorModel rowEditorModel;
    private MethodMapI curr_attr;

    private static class RowEditorModel
    {
        private final Hashtable<Integer, TableCellEditor> data;
        private final JTable table;

        public RowEditorModel(JTable table)
        {
            data = new Hashtable<>();
            this.table = table;
        }

        public void addEditorForRow(int row, TableCellEditor e)
        {
            data.put(row, e);
        }

        public void removeEditorForRow(int row)
        {
            data.remove(row);
        }

        public TableCellEditor getEditor(int row)
        {
            return data.get(row);
        }

        public void clear()
        {
            this.data.clear();
        }

        public int valueExistsAtCell(Object e)
        {
            Iterator<Integer> iter = data.keys().asIterator();
            while (iter.hasNext())
            {
                Integer key = iter.next();
                String value = data.get(key).getCellEditorValue() + "";
                Component comp;
                comp = data.get(key).getTableCellEditorComponent(table, value, true, key, 1);
                if (comp.equals(e))
                {
                    return key;
                }
            }
            return -1;
        }

    }

    private class CustomTableModel extends DefaultTableModel
    {
        public CustomTableModel(String[] col_names, int i)
        {
            super(col_names, i);
        }

        public String[] prop_names = {"Label", "X", "Y",
                "Width", "Height"};

        public void setPropNames(String[] props)
        {
            this.prop_names = props;

        }

        public String[] getPropNames()
        {
            return this.prop_names;
        }

        public Object getValueAt(int row, int col)
        {
            if (row > prop_names.length - 1)
                return null;
            if (col == 0)
                return prop_names[row];

            if (jTableInspector.getRowEditorModel().getEditor(row) == null)
                return null;
            return jTableInspector.getRowEditorModel().getEditor(row).getCellEditorValue();
            //return super.getValueAt(row,col);
        }

        public String getPropName(int row)
        {
            if (row > -1)
                return prop_names[row];
            return null;
        }

        public boolean isCellEditable(int row, int col)
        {
            return col != 0;
        }
    }

    private static class JTableInspector extends JTable
    {
        protected RowEditorModel rm;

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

        public void setRowEditorModel(RowEditorModel rm)
        {
            this.rm = rm;
        }

        public RowEditorModel getRowEditorModel()
        {
            return rm;
        }

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

    public InspectorPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        TitledBorder title = BorderFactory.createTitledBorder("Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        String[] col_names = {"Names", "Values"};
        customTableModel = new CustomTableModel(col_names, 12);

        jTableInspector = new JTableInspector(customTableModel);
        rowEditorModel = new RowEditorModel(jTableInspector);
        jTableInspector.setRowEditorModel(rowEditorModel);
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

    private String getAttribute(int i)
    {
        String[] propNames = customTableModel.getPropNames();
        if (i > propNames.length - 1)
            return null;
        return customTableModel.getPropNames()[i];
    }

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
                    String attr = getAttribute(row);
                    //System.out.println("attr-" + attr + ", row-" + row);
                    if (attr != null && attr.equals("col"))
                    {
                        try
                        {
                            cell.setBackground((Color) curr_attr.validGetterMap().get("col").invoke(curr_attr));
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

    public void invokeSetter(Map<String, Method> setterMap, String key, MethodMapI attr, Object param) throws IllegalAccessException, InvocationTargetException
    {

//        for (Map.Entry<String, Method> entry : setterMap.entrySet())
//        {
//            String currKey = entry.getKey();
//            if (currKey.equals(key))
//            {
//                entry.getValue().invoke(attr, param);
//            }
//        }
        setterMap.get(key).invoke(attr,param);
    }

    /**
     * Set the values in the inspector panel textboxes
     * x, y, width, height
     */
    public void rearrangeValues()
    {
        if (GObjectManager.inspectedObj == null)
            return;

        MethodMapI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
        curr_attr = currAttr;
        if (currAttr == null)
            return;
        TableColumn tcol = jTableInspector.getColumnModel().getColumn(1);
        tcol.setCellRenderer(new CustomTableCellRenderer());
        jTableInspector.getRowEditorModel().clear();
        Map<String, Method> cur_map = currAttr.validGetterMap();
        Map<String, Method> setter_map = currAttr.validSetterMap();

        customTableModel.setPropNames(cur_map.keySet().toArray(new String[0]));
        int i = 0;
        PanelKeyboardListener panelKeyListener = new PanelKeyboardListener();
        Component editedComp;

        for (Map.Entry<String, Method> entry : cur_map.entrySet())
        {
            String attr = getAttribute(i);
            try
            {

                switch (Objects.requireNonNull(attr))
                {
                    case "col":
                    {
                        editedComp = new JTextField("");
                        editedComp.setBackground((Color) entry.getValue().invoke(currAttr));
                        editedComp.addMouseListener(new MouseListener()
                        {

                            public void mouseClicked(MouseEvent e)
                            {
                                try
                                {
                                    Color color = JColorChooser.showDialog(MainWindow.centerCanvas, "Select a color", (Color) cur_map.get("col").invoke(currAttr));
                                    ((JTextField) e.getSource()).setBackground(color);
                                    invokeSetter(setter_map, entry.getKey(), currAttr, color);
                                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ignored)
                                {

                                }
                            }

                            @Override
                            public void mousePressed(MouseEvent e)
                            {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e)
                            {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e)
                            {

                            }

                            @Override
                            public void mouseExited(MouseEvent e)
                            {

                            }
                        });
                        DefaultCellEditor ed = new CustomCellEditor((JTextField) editedComp);

                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    case "filled":
                    {
                        editedComp = new JCheckBox((((Boolean) entry.getValue().invoke(currAttr))).toString() + "");
                        ((JCheckBox) editedComp).addItemListener(e ->
                        {
                            try
                            {
                                if (e.getStateChange() == ItemEvent.SELECTED)
                                    invokeSetter(setter_map, entry.getKey(), currAttr, Boolean.TRUE);
                                else
                                    invokeSetter(setter_map, entry.getKey(), currAttr, Boolean.FALSE);
                            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ignored)
                            {

                            }

                        });
                        DefaultCellEditor ed = new DefaultCellEditor((JCheckBox) editedComp);
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    case "lineWidth":
                    {
                        editedComp = new JTextField(entry.getValue().invoke(currAttr) + "");
                        editedComp.addKeyListener(panelKeyListener);
                        DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                    case "visible":
                        break;
                    default:
                    {
                        editedComp = new JTextField(entry.getValue().invoke(currAttr).toString());
                        editedComp.addKeyListener(panelKeyListener);
                        DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                        jTableInspector.getRowEditorModel().addEditorForRow(i, ed);
                        break;
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
            {
                //e.printStackTrace();
            }
            i++;
        }
        customTableModel.fireTableDataChanged();
    }

    private class PanelKeyboardListener implements KeyListener
    {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        public void keyTyped(KeyEvent e)
        {
            //System.out.println(e+"KEY TYPED: ");
        }

        public void keyPressed(KeyEvent e)
        {
            if (GObjectManager.inspectedObj == null)
                return;

            MethodMapI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
            if (currAttr == null)
                return;
            Map<String, Method> getter_map = currAttr.validGetterMap();
            Map<String, Method> cur_map = currAttr.validSetterMap();

            String text = "";
            int exists = rowEditorModel.valueExistsAtCell(e.getSource());
            String attr = getAttribute(exists);
            String textBoxText = "";
            if (e.getSource().getClass().equals(JTextField.class))
            {
                textBoxText = ((JTextField) e.getSource()).getText();
            } else if (e.getSource().getClass().equals(JComboBox.class))
            {

            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            {
                if (textBoxText.length() > 0)
                    text = textBoxText.substring(0, textBoxText.length() - 1);
            } else
            {
                text = textBoxText + e.getKeyChar();
            }
            if (exists > -1)
            {
                Set<Map.Entry<String, Method>> getterSet = getter_map.entrySet();
                Iterator<Map.Entry<String, Method>> iter = getterSet.iterator();
                for (Map.Entry<String, Method> entry : cur_map.entrySet())
                {
                    Map.Entry<String, Method> entryGetter = null;
                    if (iter.hasNext())
                        entryGetter = iter.next();
                    if (entryGetter == null)
                        break;
                    if (entry.getKey().equals(attr))
                    {
                        try
                        {
                            Object value = entryGetter.getValue().invoke(currAttr);
                            if (GObjectManager.inspectedObj != null)
                            {
                                if (value instanceof Double)
                                {
                                    if (text.length() == 0)
                                        entry.getValue().invoke(currAttr, 0.0);
                                    else
                                        entry.getValue().invoke(currAttr, Double.parseDouble(text));
                                } else if (value instanceof Integer)
                                {
                                    if (text.length() == 0)
                                        entry.getValue().invoke(attr, 0);
                                    else
                                        entry.getValue().invoke(currAttr, Integer.parseInt(text));
                                } else if (value instanceof Boolean)
                                {
                                    if (text.length() == 0)
                                        entry.getValue().invoke(currAttr, Boolean.FALSE);
                                    else
                                        entry.getValue().invoke(currAttr, Boolean.parseBoolean(text));
                                } else if (value instanceof Color)
                                {
                                    if (text.length() == 0)
                                        entry.getValue().invoke(currAttr, new Color(0, 0, 0));
                                    else
                                        entry.getValue().invoke(currAttr, value);
                                }
                            }

                        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
                        {
                            //System.out.println(ex);
                        }

                    }

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
