package jmi.cmd;

import java.awt.Color;
import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToChangeColorForBrush extends XLoggableCmd {
    //field
    private Color mColor = null;
    //private constructor
    private JMICmdToChangeColorForBrush(XApp app, Color c) {
        super(app);
        this.mColor = c;
    }
    
    public static boolean execute(XApp app, Color c) {
        JMICmdToChangeColorForBrush cmd = 
            new JMICmdToChangeColorForBrush(app, c);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        app.getBrush().setColor(this.mColor);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mColor);
        return sb.toString();
    }
}    