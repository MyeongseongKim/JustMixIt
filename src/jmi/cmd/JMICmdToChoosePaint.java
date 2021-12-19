package jmi.cmd;

import java.awt.Point;
import jmi.JMIApp;
import jmi.JMIPaintMixable;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToChoosePaint extends XLoggableCmd {
    //field
    JMIApp app;

    //private constructor
    private JMICmdToChoosePaint(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToChoosePaint cmd = 
            new JMICmdToChoosePaint(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        Point pt = app.getBrush().getPt();

        JMIPaintMixable paint = (JMIPaintMixable) app.getPaintMgr().getPaint(pt);
        JMIPaintMixable copy = 
            new JMIPaintMixable(paint.getColor(), paint.getPt(), paint.getVolume());
        app.getPaintMgr().getPaints().remove(paint);
        app.getPaintMgr().getPaints().add(copy);

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");

        return sb.toString();
    }
}