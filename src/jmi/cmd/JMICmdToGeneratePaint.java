package jmi.cmd;

import java.awt.Color;
import java.awt.Point;
import jmi.JMIApp;
import jmi.JMIBrush;
import jmi.JMILimitedPaint;
import jmi.JMIPaint;
import x.XApp;
import x.XLoggableCmd;

public class JMICmdToGeneratePaint extends XLoggableCmd {
    //field
    private JMIBrush mBrush = null;
    private JMIPaint mPaint = null;
    //private constructor
    private JMICmdToGeneratePaint(XApp app, JMIBrush brush) {
        super(app);
        this.mBrush = brush;
    }
    
    public static boolean execute(XApp app, JMIBrush brush) {
        JMICmdToGeneratePaint cmd = 
            new JMICmdToGeneratePaint(app, brush);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;

        Color c = mBrush.getColor();
        Point pt = mBrush.getPt();
        double m = mBrush.getVolume();
        double r = mBrush.getRadius();
        mPaint = new JMILimitedPaint(c, pt, m, r);
        
        app.getPaintMgr().getPaints().add(mPaint);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mPaint);
        return sb.toString();
    }
}
