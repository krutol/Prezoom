package prezoom.view;


import prezoom.controller.GObjectManager;
import prezoom.model.AttributeMapI;
import prezoom.view.table.InspectorTable;
import prezoom.view.table.InspectorTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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
    private final InspectorTable inspectorTable;

    /**
     * construct the inspector panel
     * initialize the custom table model, row editor model and JInspector table
     * and add to the panel
     */
    public InspectorPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        TitledBorder title = BorderFactory.createTitledBorder("Shape Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        String[] col_names = {"Names", "Values"};

        inspectorTable = new InspectorTable(new InspectorTableModel(col_names, 12));

        JScrollPane scrollPane = new JScrollPane(inspectorTable);
        add(scrollPane);
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

        inspectorTable.rearrangeValues(GObjectManager.inspectedObj.getCurrentAttributes());
    }



}
