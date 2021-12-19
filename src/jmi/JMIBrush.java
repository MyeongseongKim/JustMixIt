package jmi;

import java.awt.Color;
import java.awt.Point;


public class JMIBrush {
    // constants
    public static final Color COLOR_DEFAULT = new Color(0, 0, 0, 191);

    // fields  
    private Point mPt = null;
    public Point getPt() {
        return this.mPt;
    }
    public void setPt(Point pt) {
        this.mPt = pt;
    }

    private Point mPrevPt = null;
    public Point getPrevPt() {
        return this.mPrevPt;
    }
    public void setPrevPt(Point pt) {
        this.mPrevPt = pt;
    }
    
    private Color mColor = null;
    public Color getColor() {
        return this.mColor;
    }
    public void setColor(Color c) {
        this.mColor = c;
    }
    
    private double mVolume = Double.NaN;
    public double getVolume() {
        return this.mVolume;
    }

    private double mRadius = Double.NaN;
    public double getRadius() {
        return this.mRadius;
    }
    public void setRadius(double r) {
        this.mRadius = r;
    }
    
    // constructor
    public JMIBrush() {
        this.mPt = new Point(0, 0);
        this.mPrevPt = new Point(0, 0);
        this.mColor = COLOR_DEFAULT;
        this.mVolume = 0.0f;
        this.mRadius = 0.0;
    }

    // methods
    public void init() {
        this.mVolume = 0.0;
        this.mRadius = 0.0;
    }

    public void updateVolume(double d) {
        double c = JMIPaintMixable.COEFFICENT_VALUE;
        mVolume += d;
        
        setRadius(Math.sqrt(c * mVolume));
    }
}
