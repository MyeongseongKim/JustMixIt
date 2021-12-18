package jmi;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;


public class JMIPalette2D extends JPanel{
    // constants
    private static final Font FONT_INFO = new Font("Monospaced", Font.PLAIN, 24);
    private static final Color COLOR_INFO = new Color(255, 0, 0, 128);
    private static final double BRUSH_SIZE = 10.0;

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
        
        drawPaints(g2);

        drawBasicPaints(g2, mApp.getPalette2D().getWidth(), mApp.getPalette2D().getHeight());
        drawCustomPaints(g2, mApp.getPalette2D().getWidth(), mApp.getPalette2D().getHeight());
        
        drawBrush(g2);
        
        drawInfo(g2);
    }

    private void drawBasicPaints(Graphics2D g2, int w, int h) {
        double delta = (double)w / mApp.getPaintMgr().NUM_BASIC_COLOR;

        Rectangle2D background = new Rectangle2D.Double(0, 0, w, delta);
        g2.setColor(new Color(255, 255, 255, 255));
        g2.fill(background);

        for (int i = 0; i < mApp.getPaintMgr().NUM_BASIC_COLOR; i++) {
            double x = delta * (double)i;
            Rectangle2D rect = new Rectangle2D.Double(x, 0, x + delta, delta);
            g2.setColor(mApp.getPaintMgr().getBasicPaints().get(i).getColor());
            g2.fill(rect);
        }
    }

    private void drawCustomPaints(Graphics2D g2, int w, int h) {
        double delta = (double)w / mApp.getPaintMgr().NUM_CUSTOM_COLOR;

        Rectangle2D background = new Rectangle2D.Double(0, h - delta, w, h);
        g2.setColor(new Color(255, 255, 255, 255));
        g2.fill(background);

        for (int i = 0; i < mApp.getPaintMgr().NUM_CUSTOM_COLOR; i++) {
            double x = delta * (double)i;
            Rectangle2D rect = new Rectangle2D.Double(x, h - delta, x + delta, delta);
            g2.setColor(mApp.getPaintMgr().getCustomPaints().get(i).getColor());
            g2.fill(rect);
        }
    }
    
    private void drawPaints(Graphics2D g2) {
        for (JMILimitedPaint paint : mApp.getPaintMgr().getPaints()) {
            Color c = paint.getColor();
            Point2D ctr = paint.getPt();
            double r = paint.getRadius();
    
            Ellipse2D e = new Ellipse2D.Double(ctr.getX() - r, ctr.getY() - r, 2*r, 2*r);
            g2.setColor(c);
            g2.fill(e);
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
    }

    private void drawInfo(Graphics2D g2) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        String str = curScene.getClass().getSimpleName();
        g2.setColor(JMIPalette2D.COLOR_INFO);
        g2.setFont(JMIPalette2D.FONT_INFO);
        g2.drawString(str, 20, getHeight() - 30);
    }
}
