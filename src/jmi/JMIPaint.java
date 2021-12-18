package jmi;

import java.awt.Color;

public abstract class JMIPaint {
    // constants
    
    // fields
    protected Color mColor = null;
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
}
