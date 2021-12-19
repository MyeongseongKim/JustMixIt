package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToRemovePickedPaint extends XLoggableCmd {
    //field

    //private constructor
    private JMICmdToRemovePickedPaint(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToRemovePickedPaint cmd = 
            new JMICmdToRemovePickedPaint(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        app.getPaintMgr().setPickedPaint(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }
}