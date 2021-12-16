package jmi;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;


public class JMIPaintMgr {
    // constants
    public static final int NUM_BASIC_COLOR = 20;
    public static final int NUM_CUSTOM_COLOR = 20;

    // fields
    JMIApp mApp = null;

    private ArrayList<JMIPaint> mPaints = null;
    public ArrayList<JMIPaint> getPaints() {
        return mPaints;
    }

    private ArrayList<JMIPaint> mBasicPaints = null;
    public ArrayList<JMIPaint> getBasicPaints() {
        return mBasicPaints;
    }
    private void setBasicPaints() {
        int NUM_H = NUM_BASIC_COLOR - 2;
        float dh = 1.0f / (NUM_H - 1);

        for (int i = 0; i < NUM_H; i++) {
            float h = dh * (float) i;
            Color hsb = Color.getHSBColor(h, 0.95f, 0.95f);
            Color rgba = new Color(hsb.getRed(), hsb.getGreen(), hsb.getBlue(), 255);
            mBasicPaints.add(new JMIUnlimitedPaint(rgba));
        }
        mBasicPaints.add(new JMIUnlimitedPaint(new Color(255, 255, 255, 255)));
        mBasicPaints.add(new JMIUnlimitedPaint(new Color(0, 0, 0, 255)));
    }

    private ArrayList<JMIPaint> mCustomPaints = null;
    public ArrayList<JMIPaint> getCustomPaints() {
        return mCustomPaints;
    }
    private void setCustomPaints() {
        for (int i = 0; i < NUM_CUSTOM_COLOR; i++) {
            mCustomPaints.add(new JMIUnlimitedPaint(null));
        }
    }

    // constructor
    public JMIPaintMgr(JMIApp app) {
        this.mApp = app;
        this.mPaints = new ArrayList<JMIPaint>();
        this.mBasicPaints = new ArrayList<JMIPaint>();
        this.mCustomPaints = new ArrayList<JMIPaint>();
        setBasicPaints();
        setCustomPaints();
    }

    // methods
    public JMIPaint getPaint(Point pt) {
        int width = this.mApp.getPalette2D().getWidth();
        int height = this.mApp.getPalette2D().getHeight();
        double deltaBasic = (double)width / NUM_BASIC_COLOR;
        double deltaCustom = (double)width / NUM_CUSTOM_COLOR;

        if (pt.y < deltaBasic) {
            int index = (int) Math.floor(pt.x / deltaBasic);
            return mBasicPaints.get(index);
        }
        else if (pt.y > height - deltaCustom) {
            int index = (int) Math.floor(pt.x / deltaCustom);
            return mCustomPaints.get(index);
        }
        else {

        }
        return null;
    }
}
