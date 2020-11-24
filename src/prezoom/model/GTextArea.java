package prezoom.model;

/** This main GUI window which holds all the child GUI windows
 * Team Charlie
 * @author Ajanthan Paramasamy >
 * create date: 2020/11/20
 **/

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class GTextArea extends GObject {
   private static final int PREF_WIDTH = 700;
   private static final int PREF_HEIGHT = 500;
   private static final int ROWS = 60;
   private static final int COLS = 80;
   private static final Color RECT_COLOR = new Color(180, 180, 255);

   private JTextArea textArea = new JTextArea(ROWS, COLS);
   private JScrollPane scrollPane = new JScrollPane(textArea);
   private int x, y, width, height;
   private boolean drawRect = false;

   
   public GTextArea(double x, double y, int w, int h, Color col, Boolean filled, int lineWidth)
   {
       super(x, y, col, filled, lineWidth,w,h,0,0,true);
   }

   @Override
   public void draw(Graphics2D g)
   {
	   double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
       int w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();
         g.setColor(RECT_COLOR);
         g.setStroke(new BasicStroke(getCurrentAttributes().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
         g.draw(new Rectangle2D.Double(x, y, w, h));
   }
}