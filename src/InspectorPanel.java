import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
public class InspectorPanel extends JPanel
{

    private final ArrayList<JTextField> textBoxList = new ArrayList<>();
    private JTextField textBoxLabel;
    private JTextField textBoxX;
    private JTextField textBoxY;
    private JTextField textBoxW;
    private JTextField textBoxH;

    public InspectorPanel()
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(100, -1));
        setLayout(new FlowLayout(FlowLayout.CENTER,5,10));

        TitledBorder title = BorderFactory.createTitledBorder("Inspector");
        title.setTitleColor(Color.white);
        setBorder(title);

        JLabel label = new JLabel("Label:");
        label.setForeground(Color.white);
        add(label);

        textBoxLabel = new JTextField(8);
        add(textBoxLabel);
        textBoxList.add(textBoxLabel);
        PanelKeyboardListner panelKeyListener = new PanelKeyboardListner();
        label = new JLabel("X:");
        label.setForeground(Color.white);
        add(label);
        textBoxLabel.addKeyListener(panelKeyListener);

        textBoxX = new JTextField(7);
        add(textBoxX);
        textBoxList.add(textBoxX);
        textBoxX.addKeyListener(panelKeyListener);

        label = new JLabel("Y:");
        label.setForeground(Color.white);
        add(label);

        textBoxY = new JTextField(7);
        add(textBoxY);
        textBoxY.addKeyListener(panelKeyListener);
        textBoxList.add(textBoxY);

        label = new JLabel("Width:");
        label.setForeground(Color.white);
        add(label);

        textBoxW = new JTextField(5);
        add(textBoxW);
        textBoxW.addKeyListener(panelKeyListener);
        textBoxList.add(textBoxW);

        label = new JLabel("Height:");
        label.setForeground(Color.white);
        add(label);

        textBoxH = new JTextField(5);
        add(textBoxH);
        textBoxH.addKeyListener(panelKeyListener);
        textBoxList.add(textBoxH);

    }
    public void update(Graphics g){
        //System.out.println("here");
        //super.update(g);
        //GAttributes gatt = Main.app.centerCanvas.selectedObj.cur_Attributes;
        //rearrangeValues("label", gatt.x, gatt.y, gatt.width, gatt.height);
        rearrangeValues();
    }
    //public void rearrangeValues(String label, double x, double y, double w, double h)

    public void rearrangeValues()
    {
        //removeAll();
        //System.out.println("here"+textBoxList.getClass());
        GObject gatt = Main.app.centerCanvas.selectedObj;
        if(gatt!=null){
            //System.out.println("here.. shape"+gatt.getX()+"::"+gatt.getY());
        }
        //System.out.println("here.. inspectorPanel"+Main.app.centerCanvas.selectedObj);
        int i = 0;
        for(JTextField text : textBoxList)
        {
            if(!textBoxList.isEmpty()){
                if(i==0){
                    if(gatt!=null)
                        text.setText(""+gatt.cur_Attributes.label);//+gatt.getClass());
                }else if(i==1){
                    if(gatt!=null)
                        text.setText(""+gatt.cur_Attributes.x);//+gatt.x);
                }else if(i==2){
                    if(gatt!=null)
                        text.setText(""+gatt.cur_Attributes.y);//+gatt.y);
                }else if(i==3){
                    if(gatt!=null)
                        text.setText(""+gatt.cur_Attributes.width);//+gatt.width);
                }else if(i==4){
                    if(gatt!=null)
                        text.setText(""+gatt.cur_Attributes.height);//+gatt.height);
                }
                //add(text);
            }
            i++;
        }
    }

    private class PanelKeyboardListner implements KeyListener
    {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        String keyCode = "0";
        public void keyTyped(KeyEvent e) {
            //System.out.println(e+"KEY TYPED: ");
        }
        public void keyPressed(KeyEvent e) {
            //System.out.println("here******");
            if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                keyCode += e.getKeyChar();
            }else{

            }
                //keyCode = keyCode.substring(0, keyCode.length()-1);
            //System.out.println(e.getSource()+"KEY RELEASED: ");
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                //System.out.println("here123"+e.getSource());
                if(e.getSource().equals(textBoxLabel)){
                    Main.app.centerCanvas.selectedObj.setLabel(textBoxLabel.getText());
                    Main.app.centerCanvas.repaint();
                    textBoxLabel.requestFocusInWindow();
                }
                else if(e.getSource().equals(textBoxX)){
                    Main.app.centerCanvas.selectedObj.setX(Double.parseDouble(textBoxX.getText()));
                    Main.app.centerCanvas.repaint();
                    textBoxX.requestFocusInWindow();
                }
                else if(e.getSource().equals(textBoxY)){
                    Main.app.centerCanvas.selectedObj.setY(Double.parseDouble(textBoxY.getText()));
                    Main.app.centerCanvas.repaint();
                    textBoxY.requestFocusInWindow();
                }
                else if(e.getSource().equals(textBoxW)){
                    Main.app.centerCanvas.selectedObj.setW(Integer.parseInt(textBoxW.getText()));
                    Main.app.centerCanvas.repaint();
                    textBoxW.requestFocusInWindow();
                }
                else if(e.getSource().equals(textBoxH)){
                    Main.app.centerCanvas.selectedObj.setH(Integer.parseInt(textBoxH.getText()));
                    Main.app.centerCanvas.repaint();
                    textBoxH.requestFocus();
                }
                //keyCode = "0";
            }
            //Main.app.centerCanvas.selectedObj.setX(e.getKeyCode());
            //Main.app.centerCanvas.repaint();
        }

        /** Handle the key-released event from the text field. */
        public void keyReleased(KeyEvent e) {
            //System.out.println(e+"KEY RELEASED: ");
        }
    }

}
