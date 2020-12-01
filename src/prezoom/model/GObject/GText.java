package prezoom.model.GObject;

import prezoom.controller.CameraManager;
import prezoom.controller.GObjectManager;
import prezoom.controller.PresentManager;
import prezoom.model.GAttributesI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/22<p>
 **/
public class GText extends GObject
{
    /**
     * the JTextArea to type in text
     */
    public JTextArea textArea;
    /**
     *  whether the objects is selected
     */
    private boolean isSelected;

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

        updateTextArea();
        updateTextString();

        textArea.requestFocus();

    }

    /**
     * draw a rectangle with dash lines to represent the actual area of the JTextArea
     * @param g the Graphics to paint
     */
    @Override
    public void draw(Graphics2D g)
    {
        updateTextArea();

        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        double w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Rectangle2D.Double(x, y, w, h);

        if (!PresentManager.isPresenting &&
                (textArea.isFocusOwner() || isSelected || !getCurrentAttributes().getVisible()))
        {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    10, new float[]{5}, 0));
            g.draw(drawShape);
            if (!getCurrentAttributes().getVisible())
                g.drawString("Invisible Text", (float) (x+w/2-50), (float) (y+h/2));
            if (textArea.isFocusOwner())
                GObjectManager.resizePointObj = this;
        }
        else if (GObjectManager.resizePointObj == this)
            GObjectManager.resizePointObj = null;


    }

    /**
     * whether selected by the mouse
     * @param mx mouse x
     * @param my mouse y
     * @return true if the mouse is at the peripheral area of the actual JTextArea
     */
    @Override
    public boolean inShape(double mx, double my)
    {
        Rectangle2D rec = this.drawShape.getBounds2D();
        double EDGE = 20;

        if (rec.getWidth() != 0
                && rec.getHeight() != 0
                && rec.getX() != 0
                && rec.getY() != 0)
        rec.setFrame(rec.getX()-EDGE, rec.getY()-EDGE, rec.getWidth()+EDGE*2, rec.getHeight()+EDGE*2);

        if (rec.contains(mx, my))
        {
            isSelected = true;
            return true;
        }
        isSelected = false;
        return false;
    }

    /**
     * update information for JTextArea component
     */
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

        textArea.setVisible(visible);
        textArea.setForeground(col);

        if (fontName.isEmpty()
            || textArea.getFont().getFontName().equals("Default")
            || textArea.getFont().getFontName().equals(fontName))
        {
            fontSize = fontSize*CameraManager.getCorrectCamera().getZoomFactor();
            Font font = textArea.getFont().deriveFont(fontStyle,fontSize.floatValue());
            if (!textArea.getFont().equals(font))
                textArea.setFont(font);
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

    /**
     * update the text content of the JTextArea
     */
    public void updateTextString()
    {
        textArea.setText(getCurrentAttributes().getTextString());
    }
}
