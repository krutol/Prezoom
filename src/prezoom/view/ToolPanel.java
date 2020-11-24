package prezoom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import prezoom.controller.GObjectManager;
import prezoom.controller.StateManager;

/**
 * TODO
 * the panel that contains different function buttons
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/3<p>
 * * Changed(Abhishek Sharma):
 * *  --> class: ToolPanel method: constructor, added circle to the list of drop down shapes
 * *
 * *  --> class:ToolBtnHandler, method: actionPerformed()
 * *      --code: if the shape is one among, add the shape to Main.app.centerCanvas.objects.add(shape)
 * *      --code: refresh the inspector panel Main.app.inspectorPanel.rearrangeValues();
 **/

public class ToolPanel extends JPanel
{
    private final JButton btn_addState;
    private final JButton btn_delState;
    private final JButton btn_PlayStart;
    private final JButton btn_PlayCurrent;
    private final JButton btn_Text;
    private final JComboBox<String> btn_Shape;
    private final JButton btn_Img;

    public ToolPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setMinimumSize(new Dimension(-1, 50));
        setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btn_addState = new JButton("<html><p align=\"center\">Add<br>State</p></html>");
        btn_delState = new JButton("<html><p align=\"center\">Delete<br>State</p></html>");
        btn_PlayStart = new JButton("Play");
        btn_PlayCurrent = new JButton("<html><p align=\"center\">Play<br>Current</p></html>");
        btn_Text = new JButton("Text");
        btn_Shape = new JComboBox<>();
        btn_Shape.addItem("  Line");
        btn_Shape.addItem("  Oval");
        btn_Shape.addItem("  Rectangle");
        btn_Shape.addItem("  Circle");
        //btn_Shape.addItem("  Triangle");

        btn_Img = new JButton("Image");

        ToolBtnHandler itemHandler = new ToolBtnHandler();

        btn_addState.addActionListener(itemHandler);
        btn_delState.addActionListener(itemHandler);
        btn_PlayStart.addActionListener(itemHandler);
        btn_PlayCurrent.addActionListener(itemHandler);
        btn_Text.addActionListener(itemHandler);
        btn_Shape.addActionListener(itemHandler);
        btn_Img.addActionListener(itemHandler);

        btn_addState.setBackground(Color.lightGray);
        btn_delState.setBackground(Color.lightGray);
        btn_PlayStart.setBackground(Color.lightGray);
        btn_PlayCurrent.setBackground(Color.lightGray);
        btn_Text.setBackground(Color.lightGray);
        btn_Shape.setBackground(Color.lightGray);
        btn_Img.setBackground(Color.lightGray);

        btn_addState.setPreferredSize(new Dimension(60, 40));
        btn_delState.setPreferredSize(new Dimension(80, 40));
        btn_PlayStart.setPreferredSize(new Dimension(60, 40));
        btn_PlayCurrent.setPreferredSize(new Dimension(80, 40));
        btn_Text.setPreferredSize(new Dimension(60, 40));
        btn_Shape.setPreferredSize(new Dimension(70, 40));
        btn_Img.setPreferredSize(new Dimension(70, 40));

//                btn_addState
//        btn_delState
//                btn_PlayStart
//        btn_PlayCurrent
//                btn_Text
//        btn_Shape
//                btn_Img
//
//        btn_addState
//                btn_delState
//        btn_PlayStart
//                btn_PlayCurrent
//        btn_Text
//                btn_Shape
//        btn_Img

        JPanel left_panel = new JPanel();
        JPanel center_panel = new JPanel();
        JPanel right_panel = new JPanel();
        left_panel.setBackground(Color.darkGray);
        center_panel.setBackground(Color.darkGray);
        right_panel.setBackground(Color.darkGray);
        center_panel.setPreferredSize(new Dimension(390, 50));


        left_panel.add(btn_addState);
        left_panel.add(btn_delState);

        center_panel.add(btn_PlayStart);
        center_panel.add(btn_PlayCurrent);

        right_panel.add(btn_Text);
        right_panel.add(btn_Shape);
        right_panel.add(btn_Img);

        add(left_panel, "West");
        add(center_panel, "Center");
        add(right_panel, "East");
    }

    private class ToolBtnHandler implements ActionListener
    {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();//TODO
            if (btn_addState.equals(source))
            {
                StateManager.insertState();
            } else if (btn_delState.equals(source))
            {//TODO
                StateManager.deleteState(StateManager.getCurrent_State());
            } else if (btn_PlayStart.equals(source))
            {//TODO
            	new PresentationWindow("Play Mode", false);
            } else if (btn_PlayCurrent.equals(source))
            {//TODO
            	new PresentationWindow("Play Current Mode", true);
            } else if (btn_Text.equals(source))
            {//TODO
//            	TextBox tarea = new TextBox();
//            	CenterCanvas.gObjectManager.addGComponent(tarea);
            } else if (btn_Shape.equals(source))
            {//TODO
                // for test
                String str_selected = Objects.requireNonNull(btn_Shape.getSelectedItem()).toString();

                GObjectManager.drawingType = str_selected.trim();

            } else if (btn_Img.equals(source))
            {//TODO
            }
        }
    }
}
