package prezoom.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        JToggleButton state_btn = new JToggleButton("" + StateManager.getCurrent_State());

        state_btn.setBackground(Color.gray);
        state_btn.setPreferredSize(new Dimension(130,80));
        state_btn.setHorizontalTextPosition(SwingConstants.CENTER);
        //state_btn.setMargin(new Insets(0, 13, 3, 0));
        state_btn.addActionListener(e ->
        {
            int state = states_btn_list.indexOf(state_btn);
            StateManager.switchState(state);
            updatePressedBtn();
        });

        btnGroup.add(state_btn);
        states_btn_list.add(StateManager.getCurrent_State(), state_btn);

        updatePressedBtn();
        updateBtnImage(MainWindow.centerCanvas.getScreenShot());
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
    public void updatePressedBtn()
    {
        if(!states_btn_list.isEmpty())
            states_btn_list.get(StateManager.getCurrent_State()).setSelected(true);
//        for (JToggleButton btn : states_btn_list)
//            btn.getModel().setPressed(false);
//        states_btn_list.get(StateManager.current_State).getModel().setPressed(true);
    }

    /**
     * update btn image
     * @param img the screen shot
     */
    public void updateBtnImage(BufferedImage img)
    {
        if (img == null)
            return;

        JToggleButton button = states_btn_list.get(StateManager.getCurrent_State());

        Image image = img.getScaledInstance((int)button.getPreferredSize().getWidth(),
                (int) button.getPreferredSize().getHeight(), Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);


        Graphics2D img_g = (Graphics2D) img.getGraphics();
        img_g.setColor(new Color(250,100,100));
        img_g.setStroke(new BasicStroke(50, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        img_g.drawRect(0,0,img.getWidth(),img.getHeight());
        Image image_selected = img.getScaledInstance((int)button.getPreferredSize().getWidth(),
                (int) button.getPreferredSize().getHeight(), Image.SCALE_SMOOTH);
        ImageIcon icon_selected = new ImageIcon(image_selected);

        button.setIcon(icon);
        button.setSelectedIcon(icon_selected);
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
            btn.setText("" + i++);
            add(btn);
        }
        revalidate();
        repaint();
    }


}
