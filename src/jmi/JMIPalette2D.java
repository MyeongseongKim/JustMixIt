package jmi;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.RadialGradientPaint;


public class JMIPalette2D extends JPanel{
    // constants
    private static final Font FONT_INFO = new Font("Monospaced", Font.PLAIN, 24);
    private static final Color COLOR_INFO = new Color(255, 0, 0, 128);
    private static final double BRUSH_SIZE = 10.0;

    private static final Color COLOR_PALETTE = new Color(223, 223, 223, 255);
    private static final BasicStroke STROKE_PALETTE = new BasicStroke(2.5f);
    private static final Color COLOR_WATER = new Color(0, 127, 255, 31);
    private static final Color COLOR_WATERMARK = new Color(0, 63, 127, 63);
    private static final BasicStroke STROKE_WATERMARK = new BasicStroke(1.0f);

    // fields
    private JMIApp mApp = null;
    
    
    // constructor
    public JMIPalette2D(JMIApp jmi) {
        this.mApp = jmi;
        this.setCursor(this.getToolkit().createCustomCursor(
            new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
    }

    // methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;

        drawBasicPaints(g2, mApp.getPalette2D().getWidth(), mApp.getPalette2D().getHeight());
        drawCustomPaints(g2, mApp.getPalette2D().getWidth(), mApp.getPalette2D().getHeight());
        drawWater(g2, mApp.getPalette2D().getWidth(), mApp.getPalette2D().getHeight());

        drawPaints(g2);
        
        drawBrush(g2);
        
        drawInfo(g2);
    }

    private void drawBasicPaints(Graphics2D g2, int w, int h) {
        double delta = (double)w / JMIPaintMgr.NUM_BASIC_COLOR;

        Rectangle2D background = new Rectangle2D.Double(0, 0, w, delta);
        g2.setColor(new Color(255, 255, 255, 255));
        g2.fill(background);

        for (int i = 0; i < JMIPaintMgr.NUM_BASIC_COLOR; i++) {
            double x = delta * (double)i;
            Rectangle2D rect = new Rectangle2D.Double(x, 0, x + delta, delta);
            g2.setColor(mApp.getPaintMgr().getBasicPaints().get(i).getColor());
            g2.fill(rect);

            g2.setColor(COLOR_PALETTE);
            g2.setStroke(STROKE_PALETTE);
            g2.draw(rect);
        }
    }

    private void drawCustomPaints(Graphics2D g2, int w, int h) {
        double delta = (double)w / JMIPaintMgr.NUM_CUSTOM_COLOR;

        Rectangle2D background = new Rectangle2D.Double(0, h - delta, w, h);
        g2.setColor(new Color(255, 255, 255, 255));
        g2.fill(background);

        for (int i = 0; i < JMIPaintMgr.NUM_CUSTOM_COLOR; i++) {
            double x = delta * (double)i;
            Rectangle2D rect = new Rectangle2D.Double(x, h - delta, x + delta, h);
            JMIPaint paint = mApp.getPaintMgr().getCustomPaints().get(i);
            if (paint.getColor() == null)   g2.setColor(new Color(255, 255, 255, 255));
            else    g2.setColor(paint.getColor());
            g2.fill(rect);

            g2.setColor(COLOR_PALETTE);
            g2.setStroke(STROKE_PALETTE);
            g2.draw(rect);
        }
    }

    private void drawWater(Graphics2D g2, int w, int h) {
        double delta = (double)w / JMIPaintMgr.NUM_CUSTOM_COLOR;
        Point2D ctr = 
            new Point2D.Double(w - JMIPaintMgr.RATIO_X * delta, h - JMIPaintMgr.RATIO_Y * delta);
        double r = JMIPaintMgr.RATIO_R * delta;

        Ellipse2D e = new Ellipse2D.Double(ctr.getX() - r, ctr.getY() - r, 2*r, 2*r);
        g2.setColor(COLOR_WATER);
        g2.fill(e);

        g2.setColor(COLOR_PALETTE);
        g2.setStroke(STROKE_PALETTE);
        g2.draw(e);
    }
    
    private void drawPaints(Graphics2D g2) {
        for (JMIPaintMixable paint : mApp.getPaintMgr().getPaints()) {
            Color c = paint.getColor();
            Point2D ctr = paint.getPt();
            double r = paint.getRadius();
    
            Ellipse2D e = new Ellipse2D.Double(ctr.getX() - r, ctr.getY() - r, 2*r, 2*r);
            g2.setColor(c);
            g2.fill(e);

            if (c.getAlpha() == 0) {
                float[] dist = {0.0f, 0.5f, 1.0f};
                Color[] colors = {Color.WHITE, Color.WHITE, COLOR_WATERMARK};
                RadialGradientPaint p =
                    new RadialGradientPaint(ctr, (float) r, dist, colors);

                g2.setPaint(p);
                g2.fill(e);
            } 
        }
    }

    private void drawBrush(Graphics2D g2) {
        Color c = mApp.getBrush().getColor();
        Point ctr = mApp.getBrush().getPt();

        double R = mApp.getBrush().getRadius();
        Ellipse2D E = new Ellipse2D.Double(ctr.x - R, ctr.y - R, 2*R, 2*R);
        g2.setColor(new Color(255, 255, 255, 127));
        g2.fill(E);

        double r = BRUSH_SIZE / 2.0;
        Ellipse2D e = new Ellipse2D.Double(ctr.x - r, ctr.y - r, 2*r, 2*r);
        g2.setColor(c);
        g2.fill(e);

        if (c.getAlpha() == 0) {
            // g2.setColor(COLOR_WATERMARK);
            // g2.setStroke(STROKE_WATERMARK);
            // g2.draw(e);

            float[] dist = {0.0f, 0.5f, 1.0f};
            Color[] colors = {Color.WHITE, Color.WHITE, COLOR_WATERMARK};
            RadialGradientPaint p =
                new RadialGradientPaint(ctr, (float) r, dist, colors);

            g2.setPaint(p);
            g2.fill(e);
        } 
    }

    private void drawInfo(Graphics2D g2) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        String str = curScene.getClass().getSimpleName();
        g2.setColor(JMIPalette2D.COLOR_INFO);
        g2.setFont(JMIPalette2D.FONT_INFO);
        g2.drawString(str, 20, getHeight() - 30);
    }
}
