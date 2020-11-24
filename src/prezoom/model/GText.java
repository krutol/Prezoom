package prezoom.model;

import prezoom.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/22<p>
 **/
public class GText extends GObject
{
    public JTextArea textArea;

    public GText(String textString, Double x, Double y, Color col, Double width, Double height, Boolean visible,
                 String fontName, Integer fontStyle, Integer fontSize)
    {
        super(x, y, col, null, null, width, height,
                null, null, visible, fontName, fontStyle, fontSize, textString);
        this.drawShape = new Rectangle2D.Double(x, y, width, height);

        this.textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.textArea.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {

            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                getCurrentAttributes().setTextString(textArea.getText());
            }
        });
        updateTextArea();
        updateTextString();

        //MainWindow.centerCanvas.add(textArea);
    }

    @Override
    public void draw(Graphics2D g)
    {
        updateTextArea();

        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        double w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Rectangle2D.Double(x, y, w, h);
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                10, new float[]{5}, 0));
        g.draw(drawShape);
    }

    @Override
    public boolean inShape(double mx, double my)
    {
        Rectangle2D rec = this.drawShape.getBounds2D();
        double EDGE = 20;

        rec.setFrame(rec.getX()-EDGE/2, rec.getY()-EDGE/2, rec.getWidth()+EDGE, rec.getHeight()+EDGE);
        return rec.contains(mx, my);
    }

    private void updateTextArea()
    {
        GAttributesI cur_Att = getCurrentAttributes();
        Double x = cur_Att.getX();
        Double y = cur_Att.getY();
        Color col = cur_Att.getCol();
        Double width = cur_Att.getWidth();
        Double height = cur_Att.getHeight();
        Boolean visible = cur_Att.getVisible();
        String fontName = cur_Att.getFontName();
        Integer fontStyle = cur_Att.getFontStyle();
        Integer fontSize = cur_Att.getFontSize();

//        if (stateChanged)
//        {
//            textArea.setText(textString);
//            stateChanged = false;
//        }
        textArea.setFont(new Font(fontName, fontStyle, fontSize));
        textArea.setVisible(visible);
        textArea.setForeground(col);
        textArea.setBounds(x.intValue(),y.intValue(),width.intValue(),height.intValue());

    }

    public void updateTextString()
    {
        textArea.setText(getCurrentAttributes().getTextString());
    }
}
