package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToEmptyAction extends XLoggableCmd {
    //field

    //private constructor
    private JMICmdToEmptyAction(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToEmptyAction cmd = 
            new JMICmdToEmptyAction(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}