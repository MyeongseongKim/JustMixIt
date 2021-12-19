package jmi.cmd;

import java.awt.geom.Point2D;
import jmi.JMIApp;
import jmi.JMIBrush;
import jmi.JMIPaintMgr;
import jmi.JMIPaintMixable;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToMixPaintDynamically extends XLoggableCmd {
    // fields
    JMIPaintMixable mPaint;

    // private constructor
    private JMICmdToMixPaintDynamically(XApp app, JMIPaintMixable paint) {
        super(app);
        this.mPaint = paint;
    }
    
    public static boolean execute(XApp app, JMIPaintMixable paint) {
        JMICmdToMixPaintDynamically cmd = 
            new JMICmdToMixPaintDynamically(app, paint);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        JMIPaintMgr paintMgr = app.getPaintMgr();
        JMIBrush brush = app.getBrush();

        if (paintMgr.getPaint(brush.getPrevPt()) == mPaint) {
            double vol = Point2D.distance(brush.getPt().x, brush.getPt().y, 
                brush.getPrevPt().x, brush.getPrevPt().y);
            JMIPaintMixable dp 
                = new JMIPaintMixable(brush.getColor(), brush.getPt(), vol);
            
            paintMgr.getPaints().add(JMIPaintMixable.mix(mPaint, dp));
            paintMgr.getPaints().remove(mPaint);
        }

        brush.setPrevPt(brush.getPt());

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append('\t');
        sb.append(mPaint.getVolume());
        return sb.toString();
    }
}    