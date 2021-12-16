package jsi.cmd;

import java.awt.Color;
import jsi.JSIApp;
import jsi.JSIPtCurve;
import x.XApp;
import x.XLoggableCmd;

   
public class JSICmdToChangeColorForSelectedPtCurves extends XLoggableCmd {
    //field
    private Color mColor = null;
    //private constructor
    private JSICmdToChangeColorForSelectedPtCurves(XApp app, Color c) {
        super(app);
        this.mColor = c;
    }
    
    public static boolean execute(XApp app, Color c) {
        JSICmdToChangeColorForSelectedPtCurves cmd = 
            new JSICmdToChangeColorForSelectedPtCurves(app, c);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;  
        for (JSIPtCurve ptCurve : app.getPtCurveMgr().getSelectedPtCurves()) {
            ptCurve.setColor(this.mColor);
        }
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
    