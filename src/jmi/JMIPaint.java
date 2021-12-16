package jmi;

import java.awt.Color;
import java.awt.Point;


public abstract class JMIPaint {
    // constants
    
    // fields
    private Color mColor = null;
    public Color getColor() {
        return this.mColor;
    }
    public void setColor(Color c) {
        this.mColor = c;
    }

    // constructor
    public JMIPaint(Color c) {
        this.mColor = c;
    }
    public Point getPt() {
        return null;
    }
    public double getRadius() {
        return 0;
    }

    // methods
    // public JMIPaint mixWith(JMIPaint p) {
    //     JMIPaint paint = new JMIPaint();
    //     return paint;
    // }

    // public JMIPaint mix(JMIPaint p1, JMIPaint p2) {
    //     JMIPaint paint = new JMIPaint();
    //     return paint;
    // }
}
