import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class StatePanel extends JPanel
{
    private final ArrayList<JButton> states_btn_list = new ArrayList<>();

    public StatePanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(100, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
        TitledBorder title = BorderFactory.createTitledBorder("States");
        title.setTitleColor(Color.white);
        setBorder(title);

        insertStateBtn();
    }

    public void insertStateBtn()
    {
        JButton state_btn = new JButton("state " + StateManager.current_State);
        //state_btn.setBackground(Color.lightGray);
        states_btn_list.add(StateManager.current_State, state_btn);
        switchPressedBtn();
        state_btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int state = states_btn_list.indexOf(state_btn);
                StateManager.switchState(state);
                switchPressedBtn();
            }
        });
        rearrangeBtn();
    }

    public void insertStateBtn(int state)
    {
        //TODO
    }

    public void deleteStateBtn(int state)
    {
        states_btn_list.remove(state);
        switchPressedBtn();
    }

    private void switchPressedBtn()
    {
        for(JButton btn : states_btn_list)
            btn.getModel().setPressed(false);
        states_btn_list.get(StateManager.current_State).getModel().setPressed(true);
    }

    private void rearrangeBtn()
    {
        removeAll();
        int i = 0;
        for(JButton btn : states_btn_list)
        {
            btn.setText("State "+i++);
            add(btn);
        }

    }


}
