package jmi;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class JMIBrushMarkMgr {
    // constants
    public static final int MAX_NUM_BRUSH_MARKS = 1;

    // fields
    private ArrayList<JMIBrushMark> mBrushMarks = null;
    public ArrayList<JMIBrushMark> getBrushMarks() {
        return this.mBrushMarks;
    }
    
    // constructor
    public JMIBrushMarkMgr() {
        this.mBrushMarks = new ArrayList<JMIBrushMark>();
    }
    
    // methods
    public void addBrushMark(JMIBrushMark brushMark) {
        this.mBrushMarks.add(brushMark);
        if (this.mBrushMarks.size() > JMIBrushMarkMgr.MAX_NUM_BRUSH_MARKS) {
            this.mBrushMarks.remove(0);
            assert (this.mBrushMarks.size() <= JMIBrushMarkMgr.MAX_NUM_BRUSH_MARKS);
        }
    }
    
    public JMIBrushMark getLastBrushMark() {
        int size = this.mBrushMarks.size();
        if (size == 0) {
            return null;
        } 
        else {
            return this.mBrushMarks.get(size-1);
        }
    }
    
    public JMIBrushMark getRecentBrushMark(int i) {
        int size = this.mBrushMarks.size();
        int index = size - 1 - i;
        if (index < 0 || index >= size) {
            return null;
        } 
        else {
            return this.mBrushMarks.get(index);
        }
    }
    
    public boolean mousePressed(MouseEvent e) {
        Point pt = e.getPoint();
        JMIBrushMark brushMark = new JMIBrushMark(pt);
        this.addBrushMark(brushMark);
        return true;
    }
    
    public boolean mouseDragged(MouseEvent e) {
        Point pt = e.getPoint();
        JMIBrushMark brushMark = this.getLastBrushMark();
        if (brushMark != null && brushMark.addPt(pt)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean mouseReleased(MouseEvent e) {
        return true;
    }
}
