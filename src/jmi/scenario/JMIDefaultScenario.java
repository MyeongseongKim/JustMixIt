package jmi.scenario;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jmi.JMIApp;
import jmi.JMIPaintMixable;
import jmi.JMIScene;
import jmi.JMIPaint;
import jmi.JMIPaintMgr;

import jmi.cmd.JMICmdToInitBrush;
import jmi.cmd.JMICmdToMixPaint;
import jmi.cmd.JMICmdToChangeColorForBrush;
import jmi.cmd.JMICmdToCleanPalette;
import jmi.cmd.JMICmdToOpenPalette;
import jmi.cmd.JMICmdToSavePalette;

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
            app.getBrush().setPt(e.getPoint());

            JMIPaint paint = app.getPaintMgr().getPaint(app.getBrush().getPt());
            if (paint != null) {
                if (paint.getColor() != app.getBrush().getColor())
                    JMICmdToInitBrush.execute(app);
                JMICmdToChangeColorForBrush.execute(app, paint.getColor());
                XCmdToChangeScene.execute(app, 
                    JMIColorScenario.PaintSelectScene.getSingleton(), this);
            }
            // else if (app.getBrush().getVolume() != 0) {
            //     XCmdToChangeScene.execute(app, 
            //         JMIColorScenario.PaintGenerateScene.getSingleton(), this);
            // }
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp) this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp) this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
        }
       
        @Override
        public void handleKeyDown(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_O:
                    JMICmdToOpenPalette.execute(app);
                    break;
                case KeyEvent.VK_S:
                    JMICmdToSavePalette.execute(app);
                    break;
                case KeyEvent.VK_DELETE:
                    JMICmdToCleanPalette.execute(app);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        JMIOrganizeScenario.MoveReadyScene.getSingleton(), this);
                    break;
            }
		}

        @Override
        public void handleKeyUp(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_C:
                    XCmdToChangeScene.execute(app, 
                        StandbyScene.getSingleton(), this);
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
        public void getReady() {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            JMIPaintMgr paintMgr = app.getPaintMgr();
            while (paintMgr.checkOverlaps()) {
                for (int i = 0; i < paintMgr.getPaints().size(); i++) {
                    int j = paintMgr.checkOverlap(i);
                    if (j != -1) {
                        JMIPaintMixable p1 = paintMgr.getPaints().get(i);
                        JMIPaintMixable p2 = paintMgr.getPaints().get(j);
                        JMICmdToMixPaint.execute(app, p1, p2);
                        return;
                    }
                }
            }
        }
        
        @Override
        public void wrapUp() {}
    }
    
    
}
