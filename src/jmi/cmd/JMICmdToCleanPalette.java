package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToCleanPalette extends XLoggableCmd {
    //field
    JMIApp app;

    //private constructor
    private JMICmdToCleanPalette(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToCleanPalette cmd = 
            new JMICmdToCleanPalette(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        app.getPaintMgr().getPaints().clear();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}