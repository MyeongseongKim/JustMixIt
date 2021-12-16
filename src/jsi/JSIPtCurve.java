package jsi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class JSIPtCurve {
    public static final double MIN_DISTANCE_BTWN_PTS = 5.0f;
    
    private ArrayList<Point2D.Double> mPts = null;
    public ArrayList<Point2D.Double> getPts() {
        return this.mPts;
    }

    private Rectangle2D.Double mBoundingBox;
    public Rectangle2D.Double getBoundingBox() {
        return this.mBoundingBox;
    }

    private Stroke mStroke = null;
    public Stroke getStroke() {
        return this.mStroke;
    }
    
    private Color mColor = null;
    public Color getColor() {
        return this.mColor;
    }
    public void setColor(Color c) {
        this.mColor = c;
    }
    
    public JSIPtCurve(Point2D.Double pt, Color c, Stroke s) {
        this.mPts = new ArrayList<Point2D.Double>();
        this.mPts.add(pt);
        this.mBoundingBox = new Rectangle2D.Double(pt.x, pt.y, 0.0, 0.0);
        BasicStroke bs = (BasicStroke)s;
        this.mColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        this.mStroke = new BasicStroke(bs.getLineWidth(), bs.getEndCap(),
                bs.getLineJoin());
    }
    
    public void addPoint(Point2D.Double pt) {
        this.mPts.add(pt);
        this.mBoundingBox.add(pt);
    }
    
    public void increaseStrokeWidth(float f) {
        BasicStroke bs = (BasicStroke)this.mStroke;
        float w = bs.getLineWidth();
        w += f;
        if (w < 1.0f) {
            w = 1.0f;
        }
        this.mStroke = new BasicStroke(w, bs.getEndCap(),
                bs.getLineJoin());
    }
}
