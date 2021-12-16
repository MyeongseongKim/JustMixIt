package jmi;

import java.awt.Color;
import java.awt.Point;

public class JMILimitedPaint extends JMIPaint{
    // fields
    private Point mPt = null;
    public Point getPt() {
        return this.mPt;
    }
    public void setPt(Point pt) {
        this.mPt = pt;
    }

    private double mVolume = Float.NaN;
    public double getVolume() {
        return this.mVolume;
    }
    public void setVolume(double a) {
        this.mVolume = a;
    }

    private double mRadius = Float.NaN;
    public double getRadius() {
        return this.mRadius;
    }

    // constructor
    public JMILimitedPaint(Color c, Point pt, double m, double r) {
        super(c);
        this.mPt = pt;
        this.mVolume = m;
        this.mRadius = r;
    }
}
