package jmi.cmd;

import jmi.JMIApp;
import jsi.JSIApp;
import jsi.cmd.JSICmdToChangeColorForCurPtCurve;
import jsi.cmd.JSICmdToChangeColorForSelectedPtCurves;
import jsi.scenario.JSIDefaultScenario;
import jsi.scenario.JSISelectScenario;
import x.XApp;
import x.XLoggableCmd;

public class JMICmdToChooseColorForJSI extends XLoggableCmd {
    //field
    JMIApp app;
    JSIApp jsi;

    //private constructor
    private JMICmdToChooseColorForJSI(XApp app) {
        super(app);
        this.app = (JMIApp) app;
        this.jsi = (JSIApp)this.app.getApp();
    }
    
    public static boolean execute(XApp app) {
        JMICmdToChooseColorForJSI cmd = 
            new JMICmdToChooseColorForJSI(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;

        JSIApp jsi = (JSIApp)app.getApp();
            if (jsi.getScenarioMgr().getCurScene() == 
                JSIDefaultScenario.ReadyScene.getSingleton()) {

                JSICmdToChangeColorForCurPtCurve.execute(jsi, 
                    app.getBrush().getColor());
            }
            else if (jsi.getScenarioMgr().getCurScene() == 
                JSISelectScenario.SelectedReadyScene.getSingleton()) {
                
                JSICmdToChangeColorForSelectedPtCurves.execute(jsi, 
                    app.getBrush().getColor());
            }
            jsi.getCanvas2D().repaint();

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(app.getBrush().getColor());
        return sb.toString();
    }
}