package jmi.cmd;

import java.util.ArrayList;
import jmi.JMIApp;
import jmi.JMIPaint;
import jmi.JMIPaintMixable;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToMixWithPaint extends XLoggableCmd {
    //field
    JMIPaintMixable mPaint;

    //private constructor
    private JMICmdToMixWithPaint(XApp app, JMIPaintMixable p) {
        super(app);
        this.mPaint = p;
    }
    
    public static boolean execute(XApp app, JMIPaintMixable paint) {
        JMICmdToMixWithPaint cmd = 
            new JMICmdToMixWithPaint(app, paint);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        ArrayList<JMIPaintMixable> mPaints = app.getPaintMgr().getPaints();
        JMIPaintMixable p = app.getPaintMgr().getPickedPaint();

        mPaints.add(JMIPaintMixable.mix(mPaint, p));
        mPaints.remove(this.mPaint);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
}