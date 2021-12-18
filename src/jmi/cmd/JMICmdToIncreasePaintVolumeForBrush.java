package jmi.cmd;

import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;

public class JMICmdToIncreasePaintVolumeForBrush extends XLoggableCmd {
    //field
    private double mCoefficient = Double.NaN;
    //private constructor
    private JMICmdToIncreasePaintVolumeForBrush(XApp app, double c) {
        super(app);
        this.mCoefficient = c;
    }
    
    public static boolean execute(XApp app, double c) {
        JMICmdToIncreasePaintVolumeForBrush cmd = 
            new JMICmdToIncreasePaintVolumeForBrush(app, c);
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