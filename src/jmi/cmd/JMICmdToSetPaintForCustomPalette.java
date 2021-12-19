package jmi.cmd;

import java.awt.Color;
import jmi.JMIApp;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToSetPaintForCustomPalette extends XLoggableCmd {
    //field
    JMIApp app;
    int mIndex;
    Color mColor;

    //private constructor
    private JMICmdToSetPaintForCustomPalette(XApp app, int index, Color c) {
        super(app);
        this.mIndex = index;
        this.mColor = c;
    }
    
    public static boolean execute(XApp app, int index, Color c) {
        JMICmdToSetPaintForCustomPalette cmd = 
            new JMICmdToSetPaintForCustomPalette(app, index, c);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        app.getPaintMgr().getCustomPaints().get(mIndex).setColor(mColor);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(mIndex).append("\t");
        sb.append(mColor);
        return sb.toString();
    }
}