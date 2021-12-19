package jmi.cmd;

import java.awt.geom.Point2D;
import jmi.JMIApp;
import jmi.JMIBrush;
import jmi.JMIPaint;
import jmi.JMIPaintMgr;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToUpdatePaintVolumeOfBrush extends XLoggableCmd {
    // fields
    JMIBrush mBrush = null;

    // private constructor
    private JMICmdToUpdatePaintVolumeOfBrush(XApp app) {
        super(app);
        this.mBrush = ((JMIApp) app).getBrush();
    }
    
    public static boolean execute(XApp app) {
        JMICmdToUpdatePaintVolumeOfBrush cmd = 
            new JMICmdToUpdatePaintVolumeOfBrush(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        JMIPaintMgr paintMgr = app.getPaintMgr();

        JMIPaint curPaint = paintMgr.getPaint(mBrush.getPt());
        JMIPaint prevPaint = paintMgr.getPaint(mBrush.getPrevPt());
        
        if (curPaint != null && prevPaint != null) {
            if (mBrush.getColor() == curPaint.getColor() && mBrush.getColor() == prevPaint.getColor()) {
                double vol = Point2D.distance(mBrush.getPt().x, mBrush.getPt().y, 
                    mBrush.getPrevPt().x, mBrush.getPrevPt().y);
                
                mBrush.updateVolume(vol);
            }
        }

        mBrush.setPrevPt(mBrush.getPt());

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append('\t');
        sb.append(mBrush.getVolume());
        return sb.toString();
    }
}    