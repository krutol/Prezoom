package prezoom.view;


import prezoom.controller.GObjectManager;
import prezoom.view.table.InspectorTable;
import prezoom.view.table.InspectorTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * the panel that show the attributes of the selected object
 * @author Zhijie Lan<p>
 * create date: 2020/11/3<p>
 * Changed(Abhishek Sharma):
 * --> class: InspectorPanel method:rearrangeValues()
 * --code: update the Inspector panel with the selectedObj attributes in centerCanvas
 * <p>
 * --> added class:PanelKeyboardListner, method: keyPressed()
 * --code:update center canvas object with the values in inspector panel attributes
 **/

public class AttributePanel extends JPanel
{
    /**
     * the table to display
     */
    private final InspectorTable attributeTable;

    /**
     * constructor.
     * initialize the custom table model
     * and add to the panel
     */
    public AttributePanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        TitledBorder title = BorderFactory.createTitledBorder("Shape Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        String[] col_names = {"Names", "Values"};

        attributeTable = new InspectorTable(new InspectorTableModel(col_names, 12));

        JScrollPane scrollPane = new JScrollPane(attributeTable);
        add(scrollPane);
    }


    /**
     * update the table
     */
    public void rearrangeValues()
    {
        //if inspected object is not available return null
        if (GObjectManager.inspectedObj == null)
            return;

        attributeTable.rearrangeValues(GObjectManager.inspectedObj.getCurrentAttributes());
    }



}
