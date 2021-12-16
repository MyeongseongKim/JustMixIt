package jsi.cmd;

import java.awt.BasicStroke;
import jsi.JSIApp;
import jsi.JSIPtCurve;
import x.XApp;
import x.XLoggableCmd;


public class JSICmdToIncreaseStrokeWidthForSelectedPtCurves extends XLoggableCmd {
    private float mWBefore = Float.NaN;
    private float mWDelta = Float.NaN;
    private float mWAfter = Float.NaN;
    
    private JSICmdToIncreaseStrokeWidthForSelectedPtCurves(XApp app, float dw){
        super(app);
        this.mWDelta = dw;
    }
    
    public static boolean execute(XApp app, float dw) {
        JSICmdToIncreaseStrokeWidthForSelectedPtCurves cmd = 
            new JSICmdToIncreaseStrokeWidthForSelectedPtCurves(app, dw);
        return cmd.execute();
    } 
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        for (JSIPtCurve ptCurve : app.getPtCurveMgr().getSelectedPtCurves()) {
            BasicStroke beforeBs = (BasicStroke) ptCurve.getStroke();
            this.mWBefore = beforeBs.getLineWidth();
            ptCurve.increaseStrokeWidth(this.mWDelta);
            BasicStroke afterBs = (BasicStroke) ptCurve.getStroke();
            this.mWAfter = afterBs.getLineWidth();
        }
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mWBefore).append("\t");
        sb.append(this.mWDelta).append("\t");
        sb.append(this.mWAfter).append("\t");
        return sb.toString();
    }
}
