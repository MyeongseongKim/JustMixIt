package jmi;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class JMIPaintMgr {
    // constants
    public final static int NUM_BASIC_COLOR = 20;
    public final static int NUM_CUSTOM_COLOR = 20;
    public final static double RATIO_X = 1.5;
    public final static double RATIO_Y = 2.5;
    public final static double RATIO_R = 1.0;

    // fields
    JMIApp mApp = null;

    private ArrayList<JMIPaintMixable> mPaints = null;
    public ArrayList<JMIPaintMixable> getPaints() {
        return mPaints;
    }

    private JMIPaintMixable mPickedPaint = null;
    public JMIPaintMixable getPickedPaint() {
        return mPickedPaint;
    }
    public void setPickedPaint(JMIPaintMixable p) {
        this.mPickedPaint = p;
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
            mBasicPaints.add(new JMIPaint(rgba));
        }
        mBasicPaints.add(new JMIPaint(new Color(255, 255, 255, 255)));
        mBasicPaints.add(new JMIPaint(new Color(0, 0, 0, 255)));
    }

    private ArrayList<JMIPaint> mCustomPaints = null;
    public ArrayList<JMIPaint> getCustomPaints() {
        return mCustomPaints;
    }
    public void setCustomPaints(ArrayList<JMIPaint> customPaints) {
        mCustomPaints = customPaints;
    }
    private void initCustomPaints() {
        for (int i = 0; i < NUM_CUSTOM_COLOR; i++) {
            mCustomPaints.add(new JMIPaint(null));
        }
    }

    private JMIPaint mWater = null;
    public JMIPaint getWater() {
        return mWater;
    }

    // constructor
    public JMIPaintMgr(JMIApp app) {
        this.mApp = app;
        this.mPaints = new ArrayList<JMIPaintMixable>();
        this.mBasicPaints = new ArrayList<JMIPaint>();
        this.mCustomPaints = new ArrayList<JMIPaint>();
        this.mWater = new JMIPaint(new Color(255, 255, 255, 0));
        setBasicPaints();
        initCustomPaints();
    }

    // methods
    public JMIPaint getPaint(Point pt) {
        int width = this.mApp.getPalette2D().getWidth();
        int height = this.mApp.getPalette2D().getHeight();
        double deltaBasic = (double)width / NUM_BASIC_COLOR;
        double deltaCustom = (double)width / NUM_CUSTOM_COLOR;

        double wx = width - RATIO_X * deltaCustom;
        double wy = height - RATIO_Y * deltaCustom;
        double wr = RATIO_R * deltaCustom;

        // On basic paints
        if (pt.y < deltaBasic) {
            int index = (int) Math.floor(pt.x / deltaBasic);
            return mBasicPaints.get(index);
        }
        // On custom paints
        else if (pt.y > height - deltaCustom) {
            int index = (int) Math.floor(pt.x / deltaCustom);
            return mCustomPaints.get(index);
        }
        // On water
        else if (Point2D.distance(pt.x, pt.y, wx, wy) < wr) {
            return mWater;
        }
        // On other paints
        else {
            for(JMIPaintMixable paint : mPaints) {
                if (paint.isUnder(pt))  return paint;
            }
        }
        return null;
    }

    public JMIPaintMixable getLastPaint() {
        int index = mPaints.size() - 1;
        if (index < 0)  return null;
        else    return mPaints.get(index);
    }

    public JMIPaintMixable getOverlap(JMIPaintMixable p1) {
        for (int i = 0; i < mPaints.size(); i++) {
            JMIPaintMixable p2 = mPaints.get(i);
            if (p1 != p2) {
                double r1 = p1.getRadius();
                double r2 = p2.getRadius();
                double d = p1.getPt().distance(p2.getPt());

                // When overlaped
                if (r1 + r2 > d) {
                    return mPaints.get(i);
                }
            }
        }
        return null;
    }
}
