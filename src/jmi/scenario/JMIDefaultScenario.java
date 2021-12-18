package jmi.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jmi.JMIApp;
import jmi.JMIScene;
import jmi.JMIPaint;

import jsi.JSIApp;
import jsi.scenario.JSIDefaultScenario;
import jsi.scenario.JSISelectScenario;

import jmi.cmd.JMICmdToInitBrush;
import jmi.cmd.JMICmdToChangeColorForBrush;
import jsi.cmd.JSICmdToChangeColorForCurPtCurve;
import jsi.cmd.JSICmdToChangeColorForSelectedPtCurves;

import x.XApp;
import x.XScenario;
import x.XCmdToChangeScene;


public class JMIDefaultScenario extends XScenario {
    // singleton pattern
    private static JMIDefaultScenario mSingleton = null;
    public static JMIDefaultScenario getSingleton() {
        assert(JMIDefaultScenario.mSingleton != null);
        return JMIDefaultScenario.mSingleton;
    }
    public static JMIDefaultScenario createSingleton(XApp app) {
        assert(JMIDefaultScenario.mSingleton == null);
        JMIDefaultScenario.mSingleton = new JMIDefaultScenario(app);
        return JMIDefaultScenario.mSingleton;
    }
    private JMIDefaultScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(JMIDefaultScenario.StandbyScene.createSingleton(this));
        this.addScene(JMIDefaultScenario.ReadyScene.createSingleton(this));
    }


    public static class StandbyScene extends JMIScene {
        // singleton pattern
        private static StandbyScene mSingleton = null;
        public static StandbyScene getSingleton() {
            assert(StandbyScene.mSingleton != null);
            return StandbyScene.mSingleton;
        }
        public static StandbyScene createSingleton(XScenario scenario) {
            assert(StandbyScene.mSingleton == null);
            StandbyScene.mSingleton = new StandbyScene(scenario);
            return StandbyScene.mSingleton;
        }
        private StandbyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}

        @Override
        public void handleMouseDrag(MouseEvent e) {}

        @Override
        public void handleMouseRelease(MouseEvent e) {}
       
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
        public void getReady() {
            JMIApp app = (JMIApp) this.mScenario.getApp();
            app.setVisible(false);
        }
        
        @Override
        public void wrapUp() {
            JMIApp app = (JMIApp) this.mScenario.getApp();
            app.setVisible(true);
        }
    }


    public static class ReadyScene extends JMIScene {
        // singleton pattern
        private static ReadyScene mSingleton = null;
        public static ReadyScene getSingleton() {
            assert(ReadyScene.mSingleton != null);
            return ReadyScene.mSingleton;
        }
        public static ReadyScene createSingleton(XScenario scenario) {
            assert(ReadyScene.mSingleton == null);
            ReadyScene.mSingleton = new ReadyScene(scenario);
            return ReadyScene.mSingleton;
        }
        private ReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JMIApp app = (JMIApp) this.mScenario.getApp();
            Point pt = e.getPoint();

            JMIPaint paint = app.getPaintMgr().getPaint(app.getBrush().getPt());
            if (paint != null && paint.getColor() != null) {
                if (paint.getColor() != app.getBrush().getColor())
                    JMICmdToInitBrush.execute(app);
                JMICmdToChangeColorForBrush.execute(app, paint.getColor());
                XCmdToChangeScene.execute(app, 
                    JMIColorScenario.PaintSelectScene.getSingleton(), this);
            }
            else {
                XCmdToChangeScene.execute(app, 
                    JMIColorScenario.PaintGenerateScene.getSingleton(), this);
            }
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {}

        @Override
        public void handleMouseRelease(MouseEvent e) {}
       
        @Override
        public void handleKeyDown(KeyEvent e) {}

        @Override
        public void handleKeyUp(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, this.mReturnScene, null);
                    break;
                case KeyEvent.VK_ENTER:
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
                    break;
            }
        }

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