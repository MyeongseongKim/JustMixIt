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
    
    private long mCurMillis = 0;
    private long mPrevMillis = 0;

    // constructor
    public JMIBrush() {
        this.mPt = new Point(0, 0);
        this.mColor = COLOR_DEFAULT;
        this.mVolume = 0.0f;
        this.mRadius = 0.0;
    }

    // methods
    public void init() {
        this.mVolume = 0.0;
        this.mRadius = 0.0;
    }

    public void timerSet() {
        this.mCurMillis = System.currentTimeMillis();
        this.mPrevMillis = mCurMillis;
    }

    public void updateVolume() {
        double c = JMIPaintMixable.COEFFICENT_VALUE;
        this.mCurMillis = System.currentTimeMillis();
        if (c > 0)
            this.mVolume += mCurMillis - mPrevMillis;
        else if (c < 0)
            this.mVolume -= mCurMillis - mPrevMillis;
        
        setRadius(Math.sqrt((Math.abs(c) * mVolume)));
        mPrevMillis = mCurMillis;
    }
}
