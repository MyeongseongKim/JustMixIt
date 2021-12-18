package jmi.scenario;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jmi.JMIApp;
import jmi.JMIScene;

import jmi.cmd.JMICmdToIncreasePaintVolumeForBrush;
import jmi.cmd.JMICmdToGeneratePaint;
import jmi.cmd.JMICmdToInitBrush;

import jsi.JSIApp;
import jsi.scenario.JSIDefaultScenario;
import jsi.scenario.JSISelectScenario;
import jsi.cmd.JSICmdToChangeColorForCurPtCurve;
import jsi.cmd.JSICmdToChangeColorForSelectedPtCurves;

import x.XApp;
import x.XScenario;
import x.XCmdToChangeScene;


public class JMIColorScenario extends XScenario {
    // singleton pattern
    private static JMIColorScenario mSingleton = null;
    public static JMIColorScenario getSingleton() {
        assert(JMIColorScenario.mSingleton != null);
        return JMIColorScenario.mSingleton;
    }
    public static JMIColorScenario createSingleton(XApp app) {
        assert(JMIColorScenario.mSingleton == null);
        JMIColorScenario.mSingleton = new JMIColorScenario(app);
        return JMIColorScenario.mSingleton;
    }
    private JMIColorScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(JMIColorScenario.PaintSelectScene.createSingleton(this));
        this.addScene(JMIColorScenario.PaintGenerateScene.createSingleton(this));
    }


    public static class PaintSelectScene extends JMIScene {
        // singleton pattern
        private static PaintSelectScene mSingleton = null;
        public static PaintSelectScene getSingleton() {
            assert(PaintSelectScene.mSingleton != null);
            return PaintSelectScene.mSingleton;
        }
        public static PaintSelectScene createSingleton(XScenario scenario) {
            assert(PaintSelectScene.mSingleton == null);
            PaintSelectScene.mSingleton = new PaintSelectScene(scenario);
            return PaintSelectScene.mSingleton;
        }
        private PaintSelectScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();

            JMICmdToIncreasePaintVolumeForBrush.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();

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

            XCmdToChangeScene.execute(app, 
                this.mReturnScene, this.mReturnScene.getReturnScene());
        }
       
        @Override
        public void handleKeyDown(KeyEvent e) {}

        @Override
        public void handleKeyUp(KeyEvent e) {}

        @Override
        public void updateSupportObjects() {}
     
        @Override
        public void renderWorldObjects(Graphics2D g2) {}

        @Override
        public void renderScreenObjects(Graphics2D g2) {}
        
        @Override
        public void getReady() {}
        
        @Override
        public void wrapUp() {}
    }


    public static class PaintGenerateScene extends JMIScene {
        // singleton pattern
        private static PaintGenerateScene mSingleton = null;
        public static PaintGenerateScene getSingleton() {
            assert(PaintGenerateScene.mSingleton != null);
            return PaintGenerateScene.mSingleton;
        }
        public static PaintGenerateScene createSingleton(XScenario scenario) {
            assert(PaintGenerateScene.mSingleton == null);
            PaintGenerateScene.mSingleton = new PaintGenerateScene(scenario);
            return PaintGenerateScene.mSingleton;
        }
        private PaintGenerateScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}

        @Override
        public void handleMouseDrag(MouseEvent e) {}

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            JMICmdToGeneratePaint.execute(app, app.getBrush());
            JMICmdToInitBrush.execute(app);

            XCmdToChangeScene.execute(app, 
                this.mReturnScene, this.mReturnScene.getReturnScene());
        }
       
        @Override
        public void handleKeyDown(KeyEvent e) {}

        @Override
        public void handleKeyUp(KeyEvent e) {}

        @Override
        public void updateSupportObjects() {}
     
        @Override
        public void renderWorldObjects(Graphics2D g2) {}

        @Override
        public void renderScreenObjects(Graphics2D g2) {}
        
        @Override
        public void getReady() {}
        
        @Override
        public void wrapUp() {}
    }
}
