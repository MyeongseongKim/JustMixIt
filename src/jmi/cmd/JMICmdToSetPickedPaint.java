package jmi.cmd;

import jmi.JMIApp;
import jmi.JMIPaintMgr;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToSetPickedPaint extends XLoggableCmd {
    //field

    //private constructor
    private JMICmdToSetPickedPaint(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToSetPickedPaint cmd = 
            new JMICmdToSetPickedPaint(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        JMIPaintMgr paintMgr = app.getPaintMgr();

        paintMgr.getPaints().add(paintMgr.getPickedPaint());
        paintMgr.setPickedPaint(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }
}