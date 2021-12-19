package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToInitBrush extends XLoggableCmd {

    //private constructor
    private JMICmdToInitBrush(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToInitBrush cmd = 
            new JMICmdToInitBrush(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        app.getBrush().init();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        return sb.toString();
    }
}    