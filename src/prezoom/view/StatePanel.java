package prezoom.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

import prezoom.controller.StateManager;

/** The panel that shows all the state sequentially
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class StatePanel extends JPanel
{
    private final ArrayList<JToggleButton> states_btn_list = new ArrayList<>();
    private final ButtonGroup btnGroup = new ButtonGroup();

    /**
     * The constructor.
     */
    public StatePanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        //setPreferredSize(new Dimension(100, -1));
        //setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        setLayout(new GridLayout(0,1,10,10));
        TitledBorder title = BorderFactory.createTitledBorder("States");
        title.setTitleColor(Color.white);
        setBorder(title);

        insertStateBtn();
    }

    /**
     * insert a new button that represents a new state at the next of the current state button.
     */
    public void insertStateBtn()
    {
        JToggleButton state_btn = new JToggleButton("state " + StateManager.getCurrent_State());

        state_btn.setBackground(Color.lightGray);
        state_btn.setPreferredSize(new Dimension(120,50));
        state_btn.addActionListener(e ->
        {
            int state = states_btn_list.indexOf(state_btn);
            StateManager.switchState(state);
            updatePressedBtn();
        });

        btnGroup.add(state_btn);
        states_btn_list.add(StateManager.getCurrent_State(), state_btn);

        updatePressedBtn();
        rearrangeBtn();
    }

    /**
     * delete a state btn
     * @param state the state to be deleted
     */
    public void deleteStateBtn(int state)
    {
        states_btn_list.remove(state);
        updatePressedBtn();
        rearrangeBtn();
    }

    /**
     * update the pressed status of state buttons according to the current state
     */
    private void updatePressedBtn()
    {
        states_btn_list.get(StateManager.getCurrent_State()).setSelected(true);
//        for (JToggleButton btn : states_btn_list)
//            btn.getModel().setPressed(false);
//        states_btn_list.get(StateManager.current_State).getModel().setPressed(true);
    }

    /**
     * refresh all buttons
     */
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
