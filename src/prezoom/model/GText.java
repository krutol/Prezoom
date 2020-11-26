package prezoom.model;

import prezoom.controller.CameraManager;
import prezoom.controller.GObjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/22<p>
 **/
public class GText extends GObject
{
    public JTextArea textArea;
    private boolean inShape;

    public GText(String textString, Double x, Double y, Color col, Double width, Double height,
                 String fontName, Integer fontStyle, Double fontSize)
    {
        super(x, y, col, null, null, width, height,
                null, null, true, fontName, fontStyle, fontSize, textString);
        this.drawShape = new Rectangle2D.Double(x, y, width, height);

        this.textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.textArea.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                getCurrentAttributes().setTextString(textArea.getText());
            }
        });
        updateTextArea();
        updateTextString();

        textArea.requestFocus();

    }

    @Override
    public void draw(Graphics2D g)
    {
        updateTextArea();

        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        double w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Rectangle2D.Double(x, y, w, h);

        if (textArea.isFocusOwner() || inShape)
        {
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    10, new float[]{5}, 0));
            g.draw(drawShape);
            if (textArea.isFocusOwner())
                GObjectManager.resizePointObj = this;
        }

        if (!inShape && GObjectManager.resizePointObj == this)
            GObjectManager.resizePointObj = null;
    }

    @Override
    public boolean inShape(double mx, double my)
    {
        if (!getCurrentAttributes().getVisible())
            return false;

        Rectangle2D rec = this.drawShape.getBounds2D();
        double EDGE = 20;

        rec.setFrame(rec.getX()-EDGE, rec.getY()-EDGE, rec.getWidth()+EDGE*2, rec.getHeight()+EDGE*2);

        if (rec.contains(mx, my))
        {
            inShape = true;
            return true;
        }
        inShape = false;
        return false;
    }

    private void updateTextArea()
    {
        GAttributesI cur_Att = getCurrentAttributes();
        Double x = cur_Att.getX();
        Double y = cur_Att.getY();
        Color col = cur_Att.getColor();
        Double width = cur_Att.getWidth();
        Double height = cur_Att.getHeight();
        Boolean visible = cur_Att.getVisible();
        String fontName = cur_Att.getFontName();
        Integer fontStyle = cur_Att.getFontStyle();
        Double fontSize = cur_Att.getFontSize();

//        if (stateChanged)
//        {
//            textArea.setText(textString);
//            stateChanged = false;
//        }
        textArea.setVisible(visible);
        textArea.setForeground(col);

        String s = textArea.getFont().getFontName();

        if (fontName.isEmpty()
            ||textArea.getFont().getFontName().equals("Default")
            || textArea.getFont().getFontName().equals(fontName))
        {
            fontSize = fontSize*CameraManager.getCorrectCamera().getZoomFactor();
            textArea.setFont(textArea.getFont().deriveFont(fontSize.floatValue()));
        }
        else
        {
            Font font = new Font(fontName, fontStyle, fontSize.intValue());
            textArea.setFont(font);
        }


        Point2D viewLocation = CameraManager.toViewCoordinates(x,y);
        Rectangle rec = new Rectangle((int)viewLocation.getX(),(int)viewLocation.getY(),
                (int)(width*CameraManager.getCorrectCamera().getZoomFactor()),
                (int)(height*CameraManager.getCorrectCamera().getZoomFactor()));

        if (!textArea.getBounds().equals(rec))
            textArea.setBounds(rec);

    }

    public void updateTextString()
    {
        textArea.setText(getCurrentAttributes().getTextString());
    }
}
