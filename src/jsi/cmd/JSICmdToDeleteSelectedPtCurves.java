package jsi.cmd;

import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;


public class JSICmdToDeleteSelectedPtCurves extends XLoggableCmd {
    private JSICmdToDeleteSelectedPtCurves (XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JSICmdToDeleteSelectedPtCurves cmd = new JSICmdToDeleteSelectedPtCurves(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        app.getPtCurveMgr().getSelectedPtCurves().clear();
        return true;
    }
  
    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
        
    }
}
