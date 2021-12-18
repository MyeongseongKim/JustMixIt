package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;

public class JMICmdToIncreasePaintVolumeForBrush extends XLoggableCmd {
    //private constructor
    private JMICmdToIncreasePaintVolumeForBrush(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToIncreasePaintVolumeForBrush cmd = 
            new JMICmdToIncreasePaintVolumeForBrush(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        app.getBrush().updateVolume();
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }
}    