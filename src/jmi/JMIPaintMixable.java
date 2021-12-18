package jmi;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class JMIPaintMixable extends JMIPaint{
    // constants
    public static final double COEFFICENT_VALUE = 0.5;

    // fields
    protected Point2D mPt = null;
    public Point2D getPt() {
        return this.mPt;
    }
    public void setPt(Point2D pt) {
        this.mPt = pt;
    }

    protected double mRadius = Float.NaN;
    public double getRadius() {
        return this.mRadius;
    }

    private double mVolume = Float.NaN;
    public double getVolume() {
        return this.mVolume;
    }
    public void setVolume(double a) {
        this.mVolume = a;
    }

    // constructor
    public JMIPaintMixable(Color c, Point2D pt, double m) {
        super(c);
        this.mPt = pt;
        this.mVolume = m;
        this.mRadius = Math.sqrt((Math.abs(COEFFICENT_VALUE) * mVolume));
    }

    // methods
    public boolean isUnder(Point pt) {
        Point2D pt2D = new Point2D.Double(pt.x, pt.y);
        if (pt2D.distance(mPt) < mRadius)   return true;
        else    return false;
    }

    // static methods
    public static JMIPaintMixable mix(JMIPaintMixable p1, JMIPaintMixable p2) {
        float[] c1 = p1.getColor().getRGBComponents(null);
        float[] c2 = p2.getColor().getRGBComponents(null);

        double v1 = p1.getVolume();
        double v2 = p2.getVolume();
        double v = v1 + v2;
        double t = v1 / v;

        double x = p1.getPt().getX() * t + p2.getPt().getX() * (1-t);
        double y = p1.getPt().getY() * t + p2.getPt().getY() * (1-t);
        Point2D pt = new Point2D.Double(x, y);
        
        double i1 = p1.getVolume() * c1[3];
        double i2 = p2.getVolume() * c2[3];
        double i = i1 + i2;
        double s = i1 / i;

        float r = (float) (c1[0] * s + c2[0] * (1-s));
        float g = (float) (c1[1] * s + c2[1] * (1-s));
        float b = (float) (c1[2] * s + c2[2] * (1-s));
        float a = (float) (i / v);
        Color c = new Color(r, g, b, a);

        JMIPaintMixable paint = new JMIPaintMixable(c, pt, v);
        return paint;
    }
}
