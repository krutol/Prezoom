package prezoom.view;

import prezoom.controller.GObjectManager;
import prezoom.model.GAttributesI;
import prezoom.model.GLine;
import prezoom.model.GOval;
import prezoom.model.GRectangle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.table.*;
/**
 * TODO
 * @author Zhijie Lan<p>
 * create date: 2020/11/3<p>
 * Changed(Abhishek Sharma):
 * --> class: InspectorPanel method:rearrangeValues()
 * --code: update the Inspector panel with the selectedObj attributes in centerCanvas
 *
 * --> added class:PanelKeyboardListner, method: keyPressed()
 * --code:update center canvas object with the values in inspector panel attributes
 **/

public class InspectorPanel extends JPanel
{

    private final ArrayList<JTextField> textBoxList = new ArrayList<>();
    private final String[] ovalAttributes = {"Label","X","Y","Width", "Height","LineWidth","IsFilled","Color"};
    private final String[] lineAttributes = {"Label","X","Y","X2","Y2","LineWidth","IsFilled","Color"};
    private final String[] circleAttributes = {"Label","X","Y","Radius","LineWidth","IsFilled","Color"};
    private final String[] rectAttributes = {"Label","X","Y","Width", "Height","LineWidth","IsFilled","Color"};
    private String shape = "Oval";
    private JTextField textBoxLabel;
    private JTextField textBoxX;
    private JTextField textBoxY;
    private JTextField textBoxW;
    private JTextField textBoxH;
    private JTableInspector table;
    private CustomTableModel model;
    private String[] col_names = {"", ""};
    private RowEditorModel rm;
    private Component editedComp;
    InspectorPanel getInspectorRef(){
        return this;
    }
    private class RowEditorModel
    {
        private Hashtable data;
        private JTable table;
        public RowEditorModel(JTable table)
        {
            data = new Hashtable();
            this.table = table;
        }
        public void addEditorForRow(int row, TableCellEditor e )
        {
            data.put(new Integer(row), e);
        }
        public void removeEditorForRow(int row)
        {
            data.remove(new Integer(row));
        }
        public TableCellEditor getEditor(int row)
        {
            return (TableCellEditor)data.get(new Integer(row));
        }
        public void clear()
        {
            this.data.clear();
        }

        public int valueExistsAtCell(Object e)
        {
            Iterator iter = data.keys().asIterator();
            while(iter.hasNext()){
                Integer key = (Integer)iter.next();
                String value = ((TableCellEditor)data.get(key)).getCellEditorValue()+"";
                String prop = ((CustomTableModel)table.getModel()).getPropName(key.intValue());
                Component comp = null;
                if(prop.equals("Label")
                        || prop.equals("X")
                        || prop.equals("Y")
                        || prop.equals("Width")
                        || prop.equals("Height")
                        || prop.equals("X2")
                        || prop.equals("Y2")
                        || prop.equals("LineWidth")){
                    comp = (JTextField) ((TableCellEditor)data.get(key)).getTableCellEditorComponent(table, value, true, key.intValue(), 1);
                }else if(prop.equals("IsFilled")){
                    comp = (JCheckBox) ((TableCellEditor)data.get(key)).getTableCellEditorComponent(table, value, true, key.intValue(), 1);
                }else if(prop.equals("Color")){
                    comp = (JTextField) ((TableCellEditor)data.get(key)).getTableCellEditorComponent(table, value, true, key.intValue(), 1);
                }
                if(comp.equals(e)){
                    return key.intValue();
                }
            }
            return -1;
        }

    }
    private class CustomTableModel extends DefaultTableModel{
        public CustomTableModel(String[] col_names, int i){
            super(col_names, i);
        }
        public String[] prop_names = { "Label", "X", "Y",
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
            if(row>prop_names.length-1)
                return null;
            if (col==0)
                return prop_names[row];
            return table.getRowEditorModel().getEditor(row).getCellEditorValue();
            //return super.getValueAt(row,col);
        }

        public String getPropName(int row)
        {
            if(row>-1)
                return prop_names[row];
            return null;
        }
        public boolean isCellEditable(int row, int col)
        {
            if (col==0)
                return false;
            return true;
        }
    }

    private class JTableInspector extends JTable
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
            super(tm,cm);
            rm = null;
        }
        public JTableInspector(TableModel tm, TableColumnModel cm,
                               ListSelectionModel sm)
        {
            super(tm,cm,sm);
            rm = null;
        }
        public JTableInspector(int rows, int cols)
        {
            super(rows,cols);
            rm = null;
        }
        /*
        public JTableX(final Vector rowData, final Vector columnNames)
        {
            super(rowData, columnNames);
            rm = null;
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
            super(tm,null,null);
            this.rm = rm;
        }
        public void setRowEditorModel(RowEditorModel rm) {
            this.rm = rm;
        }
        public RowEditorModel getRowEditorModel()
        {
            return rm;
        }
        public TableCellEditor getCellEditor(int row, int col)
        {
            TableCellEditor tmpEditor = null;
            if (rm!=null)
                tmpEditor = rm.getEditor(row);
            if (tmpEditor!=null)
                return tmpEditor;
            return super.getCellEditor(row,col);
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

        model = new CustomTableModel(col_names,12);

        table = new JTableInspector(model);
        rm = new RowEditorModel(table);
        table.setRowEditorModel(rm);
        //GAttributesI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();

        JTextField tf = new JTextField("label");
        DefaultCellEditor ed = new DefaultCellEditor(tf);
        // tell the RowEditorModel to use ed for row 1
        rm.addEditorForRow(0,ed);

        tf = new JTextField("text");
        ed = new DefaultCellEditor(tf);
        // tell the RowEditorModel to use ed for row 1
        rm.addEditorForRow(1,ed);

        tf = new JTextField("");
        ed = new DefaultCellEditor(tf);
        rm.addEditorForRow(2,ed);

        tf = new JTextField("");
        ed = new DefaultCellEditor(tf);
        rm.addEditorForRow(3,ed);

        tf = new JTextField("");
        ed = new DefaultCellEditor(tf);
        rm.addEditorForRow(4,ed);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        table.setPreferredScrollableViewportSize(new Dimension(100,300));
    }

    public String getAttribute(int i){
        String[] propNames = ((CustomTableModel)model).getPropNames();
        if(i>propNames.length-1)
            return null;
        return ((CustomTableModel)model).getPropNames()[i];
    }

    public DefaultTableModel getModel(){
        return model;
    }
    public RowEditorModel getRowModelEditor(){
        return rm;
    }

    class CustomTableCellRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table,
                                                        Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(
                    table, obj, isSelected, hasFocus, row, column);
            if (GObjectManager.inspectedObj != null){
                GAttributesI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
                if(currAttr != null) {
                    String attr = getAttribute(row);
                    if (attr != null && attr.equals("Color")) {
                        cell.setBackground(currAttr.getCol());
                    }else{
                        cell.setBackground(Color.white);
                    }
                }
            }
            return cell;
        }
    }

    private class CustomCellEditor extends DefaultCellEditor {

        public CustomCellEditor(JTextField comp){
            super(comp);
        }
        public boolean isCellEditable(EventObject evt) {
            if (evt instanceof MouseEvent) {
                int clickCount;

                // For single-click activation
                clickCount = 1;

                // For double-click activation
                //clickCount = 2;

                // For triple-click activation
                //clickCount = 3;

                return ((MouseEvent)evt).getClickCount() >= clickCount;
            }
            return true;
        }
    }

    /**
     * Set the values in the inspector panel textboxes
     * x, y, width, height
     */
    public void rearrangeValues()
    {
        if (GObjectManager.inspectedObj == null)
            return;

        GAttributesI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
        if(currAttr == null)
            return;
        TableColumn tcol = table.getColumnModel().getColumn(1);
        tcol.setCellRenderer(new CustomTableCellRenderer());
        table.getRowEditorModel().clear();
        int numAttr = 0;
        if(GObjectManager.inspectedObj.getClass().equals(GOval.class)){
            numAttr = this.ovalAttributes.length;
            ((CustomTableModel)model).setPropNames(this.ovalAttributes);
        }else if(GObjectManager.inspectedObj.getClass().equals(GLine.class)){
            numAttr = this.lineAttributes.length;
            ((CustomTableModel)model).setPropNames(this.lineAttributes);
        }else if(GObjectManager.inspectedObj.getClass().equals(GRectangle.class)){
            numAttr = this.rectAttributes.length;
            ((CustomTableModel)model).setPropNames(this.rectAttributes);
        }
        int i = 0;
        PanelKeyboardListener panelKeyListener = new PanelKeyboardListener();
        editedComp = null;
        while(i<numAttr){
            String attr = getAttribute(i);
            if (attr.equals("Label"))
            {
                editedComp = new JTextField(currAttr.getLabel());
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("X"))
            {
                editedComp = new JTextField(currAttr.getX()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("Y"))
            {
                editedComp = new JTextField(currAttr.getY()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("Width"))
            {
                editedComp = new JTextField(currAttr.getWidth()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("Height"))
            {
                editedComp = new JTextField(currAttr.getHeight()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("X2"))
            {
                editedComp = new JTextField(currAttr.getX2()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("Y2"))
            {
                editedComp = new JTextField(currAttr.getY2()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);

                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("Color"))
            {
                editedComp = new JTextField("");
                editedComp.setBackground(currAttr.getCol());
                editedComp.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Color color=JColorChooser.showDialog(getInspectorRef(),"Select a color",currAttr.getCol());
                        editedComp.setBackground(color);
                        currAttr.setCol(color);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                DefaultCellEditor ed = new CustomCellEditor((JTextField) editedComp);

                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("IsFilled"))
            {
                editedComp = new JCheckBox();
                ((JCheckBox)editedComp).addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange()==1)
                            currAttr.setFilled(true);
                        else
                            currAttr.setFilled(false);
                    }
                });
                DefaultCellEditor ed = new DefaultCellEditor((JCheckBox) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            } else if (attr.equals("LineWidth"))
            {
                editedComp = new JTextField(currAttr.getLineWidth().intValue()+"");
                editedComp.addKeyListener(panelKeyListener);
                DefaultCellEditor ed = new DefaultCellEditor((JTextField) editedComp);
                table.getRowEditorModel().addEditorForRow(i, ed);
            }
            i++;
        }
        ((AbstractTableModel) model).fireTableDataChanged();
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

            GAttributesI currAttr = GObjectManager.inspectedObj.getCurrentAttributes();
            if (currAttr == null)
                return;
            String text = "";
            int exists = getRowModelEditor().valueExistsAtCell((JTextField)e.getSource());
            if(exists>-1) {
                String textBoxText = "";
                if(e.getSource().getClass().equals(JTextField.class)) {
                    textBoxText = ((JTextField) e.getSource()).getText();
                }else if(e.getSource().getClass().equals(JComboBox.class)){

                }
                if (getAttribute(exists).equals("Label")) {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    currAttr.setLabel(text);
                }else if (getAttribute(exists).equals("X"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setX(0.0);
                        else
                            currAttr.setX(Double.parseDouble(text));
                    }
                }else if (getAttribute(exists).equals("Y"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setY(0.0);
                        else
                            currAttr.setY(Double.parseDouble(text));
                    }
                } else if (getAttribute(exists).equals("Width"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setWidth(0.0);
                        else
                            currAttr.setWidth(Double.parseDouble(text));
                    }
                } else if (getAttribute(exists).equals("Height"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setHeight(0.0);
                        else
                            currAttr.setHeight(Double.parseDouble(text));
                    }
                }else if (getAttribute(exists).equals("X2"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setX2(0.0);
                        else
                            currAttr.setX2(Double.parseDouble(text));
                    }
                } else if (getAttribute(exists).equals("Y2"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setY2(0.0);
                        else
                            currAttr.setY2(Double.parseDouble(text));
                    }
                } else if (getAttribute(exists).equals("LineWidth"))
                {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (textBoxText.length() > 0)
                            text = textBoxText.substring(0, textBoxText.length() - 1);
                    } else
                    {
                        text = textBoxText + e.getKeyChar();
                    }
                    if (GObjectManager.inspectedObj != null)
                    {
                        if (text.length() == 0)
                            currAttr.setLineWidth(0);
                        else
                            currAttr.setLineWidth(Integer.parseInt(text));
                    }
                }
            }
        }

        /** Handle the key-released event from the text field. */
        public void keyReleased(KeyEvent e)
        {
            //System.out.println(e+"KEY RELEASED: ");
        }
    }

}
