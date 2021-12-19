package jmi.cmd;

import java.util.ArrayList;
import jmi.JMIApp;
import jmi.JMIPaintMixable;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToMixPaint extends XLoggableCmd {
    //field
    JMIPaintMixable mPaintA;
    JMIPaintMixable mPaintB;

    //private constructor
    private JMICmdToMixPaint(XApp app, JMIPaintMixable p1, JMIPaintMixable p2) {
        super(app);
        this.mPaintA = p1;
        this.mPaintB = p2;
    }
    
    public static boolean execute(XApp app, JMIPaintMixable p1, JMIPaintMixable p2) {
        JMICmdToMixPaint cmd = 
            new JMICmdToMixPaint(app, p1, p2);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
        ArrayList<JMIPaintMixable> mPaints = app.getPaintMgr().getPaints();

        mPaints.add(JMIPaintMixable.mix(mPaintA, mPaintB));
        mPaints.remove(this.mPaintA);
        mPaints.remove(this.mPaintB);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mPaintA).append("\t");
        sb.append(this.mPaintB);
        return sb.toString();
    }
}