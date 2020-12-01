package prezoom.view;

import prezoom.controller.CameraManager;
import prezoom.view.table.InspectorTable;
import prezoom.view.table.InspectorTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * the panel that show the attributes of the selected camera
 * @author Abhishek Sharma<p>
 * create date: 2020/11/26
 **/
public class CameraPanel extends JPanel
{
    private final InspectorTable cameraTable;

    //construct the camera inspector panel
    public CameraPanel()
    {
        //set background for the panel
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(120, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        //create a titled border on the camera inspector panel
        TitledBorder title = BorderFactory.createTitledBorder("Camera Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        //two column names
        String[] col_names = {"Names", "Values"};

        //create the inspector table
        cameraTable = new InspectorTable(new InspectorTableModel(col_names, 6));

        //add an scroll pane
        JScrollPane scrollPane = new JScrollPane(cameraTable);
        add(scrollPane);
        cameraTable.setPreferredScrollableViewportSize(new Dimension(100, 200));
    }

    /**
     * Set the values in the inspector panel text boxes
     * x, y, width, height
     */
    public void rearrangeValues()
    {
        cameraTable.rearrangeValues(CameraManager.getCorrectCamera());
    }

}

