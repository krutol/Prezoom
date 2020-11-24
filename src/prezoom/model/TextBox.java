package prezoom.model;


/** This main GUI window which holds all the child GUI windows
 * Team Charlie
 * @author Ajanthan Paramasamy >
 * create date: 2020/11/22
 **/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class TextBox extends JPanel {
   private static final int PREF_WIDTH = 700;
   private static final int PREF_HEIGHT = 500;
   private static final int ROWS = 60;
   private static final int COLS = 80;
   private static final Color RECT_COLOR = new Color(180, 180, 255);

   private JTextArea textArea = new JTextArea(ROWS, COLS);

   private JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   private int x, y, width, height;
   private boolean drawRect = false;

   public TextBox() {
      setLayout(null);
      textArea.setLineWrap(true);
      add(scrollPane);
      MyMouseAdapter myMouseAdapter = new MyMouseAdapter(); 
      addMouseListener(myMouseAdapter);
      addMouseMotionListener(myMouseAdapter);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (drawRect) {
         g.setColor(RECT_COLOR);
         g.drawRect(x, y, width, height);         
      }
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(PREF_WIDTH, PREF_HEIGHT);
   }

   private class MyMouseAdapter extends MouseAdapter {
      private int innerX, innerY;

      @Override
      public void mousePressed(MouseEvent e) {
         x = e.getX();
         y = e.getY();
         innerX = x;
         innerY = y;
         width = 0;
         height = 0;
         drawRect = true;
      }

      @Override
      public void mouseDragged(MouseEvent e) {
         calcBounds(e);

         drawRect = true;
         TextBox.this.repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
         calcBounds(e);

         drawRect = false;
         scrollPane.setBounds(x, y, width, height);
         scrollPane.getViewport().revalidate();
         TextBox.this.repaint();

      }

      private void calcBounds(MouseEvent e) {
         width = Math.abs(innerX - e.getX());
         height = Math.abs(innerY - e.getY());
         x = Math.min(innerX, e.getX());
         y = Math.min(innerY, e.getY());
      }

   }
}