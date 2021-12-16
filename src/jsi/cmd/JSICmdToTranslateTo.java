package jsi.cmd;

import java.awt.Point;
import jsi.JSIApp;
import x.XApp;
import x.XLoggableCmd;


public class JSICmdToTranslateTo extends XLoggableCmd {
    private Point mScreenPt = null;
      
    private JSICmdToTranslateTo(XApp app, Point pt) {
        super(app);
        this.mScreenPt = pt;  
    }
    
    public static boolean execute(XApp app, Point pt) {
        JSICmdToTranslateTo cmd = new JSICmdToTranslateTo(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        JSIApp app = (JSIApp) this.mApp;
        app.getXform().translateTo(this.mScreenPt);
        return true;   
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");        
        sb.append(this.mScreenPt);
        return sb.toString();
    } 
}
