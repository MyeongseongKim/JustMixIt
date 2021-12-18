package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;

public class JMICmdToDeletedLastPaint extends XLoggableCmd {
    //field
    JMIApp app;

    //private constructor
    private JMICmdToDeletedLastPaint(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToDeletedLastPaint cmd = 
            new JMICmdToDeletedLastPaint(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        int lastIndex = app.getPaintMgr().getPaints().size() - 1;
        app.getPaintMgr().getPaints().remove(lastIndex);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}