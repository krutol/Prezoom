package prezoom.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import prezoom.Main;
import prezoom.controller.StateManager;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 *
 **/
public class StatePanel extends JPanel
{
    private final ArrayList<JToggleButton> states_btn_list = new ArrayList<>();
    private final ButtonGroup btnGroup = new ButtonGroup();

    public StatePanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        //setPreferredSize(new Dimension(100, -1));
        //setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setLayout(new GridLayout(0,1,10,10));
        add(Box.createGlue());
        TitledBorder title = BorderFactory.createTitledBorder("States");
        title.setTitleColor(Color.white);
        setBorder(title);

        insertStateBtn();
    }

    public void insertStateBtn()
    {
        JToggleButton state_btn = new JToggleButton("state " + StateManager.current_State);
        state_btn.setBackground(Color.lightGray);
        state_btn.setPreferredSize(new Dimension(120,50));
        btnGroup.add(state_btn);
        states_btn_list.add(StateManager.current_State, state_btn);
        updatePressedBtn();
        state_btn.addActionListener(e ->
        {
            int state = states_btn_list.indexOf(state_btn);
            StateManager.switchState(state);
            updatePressedBtn();
        });
        rearrangeBtn();
    }

    public void deleteStateBtn(int state)
    {
        states_btn_list.remove(state);
        rearrangeBtn();
        updatePressedBtn();
    }

    private void updatePressedBtn()
    {
        states_btn_list.get(StateManager.current_State).setSelected(true);
//        for (JToggleButton btn : states_btn_list)
//            btn.getModel().setPressed(false);
//        states_btn_list.get(StateManager.current_State).getModel().setPressed(true);
    }

    private void rearrangeBtn()
    {
        removeAll();
        int i = 0;
        for (JToggleButton btn : states_btn_list)
        {
            btn.setText("State " + i++);
            add(btn);
        }
        revalidate();
        repaint();
    }


}
