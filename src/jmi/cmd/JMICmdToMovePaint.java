package jmi.cmd;

import jmi.JMIApp;
import jmi.JMIPaintMixable;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToMovePaint extends XLoggableCmd {
    //field
    JMIApp app;
    JMIPaintMixable mPaint;

    //private constructor
    private JMICmdToMovePaint(XApp app, JMIPaintMixable paint) {
        super(app);
        this.mPaint = paint;
    }
    
    public static boolean execute(XApp app, JMIPaintMixable paint) {
        JMICmdToMovePaint cmd = 
            new JMICmdToMovePaint(app, paint);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;

        mPaint.setPt(app.getBrush().getPt());

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}