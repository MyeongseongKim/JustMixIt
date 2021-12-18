package jmi.scenario;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jmi.JMIApp;
import jmi.JMIScene;

import x.XApp;
import x.XScenario;
import x.XCmdToChangeScene;


public class JMIOrganizeScenario extends XScenario {
        // singleton pattern
    private static JMIOrganizeScenario mSingleton = null;
    public static JMIOrganizeScenario getSingleton() {
        assert(JMIOrganizeScenario.mSingleton != null);
        return JMIOrganizeScenario.mSingleton;
    }
    public static JMIOrganizeScenario createSingleton(XApp app) {
        assert(JMIOrganizeScenario.mSingleton == null);
        JMIOrganizeScenario.mSingleton = new JMIOrganizeScenario(app);
        return JMIOrganizeScenario.mSingleton;
    }
    private JMIOrganizeScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(JMIOrganizeScenario.MoveReadyScene.createSingleton(this));
        this.addScene(JMIOrganizeScenario.MoveScene.createSingleton(this));
        this.addScene(JMIOrganizeScenario.CopyReadyScene.createSingleton(this));
        this.addScene(JMIOrganizeScenario.CopyScene.createSingleton(this));
    }


    public static class MoveReadyScene extends JMIScene {
        // singleton pattern
        private static MoveReadyScene mSingleton = null;
        public static MoveReadyScene getSingleton() {
            assert(MoveReadyScene.mSingleton != null);
            return MoveReadyScene.mSingleton;
        }
        public static MoveReadyScene createSingleton(XScenario scenario) {
            assert(MoveReadyScene.mSingleton == null);
            MoveReadyScene.mSingleton = new MoveReadyScene(scenario);
            return MoveReadyScene.mSingleton;
        }
        private MoveReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {}

        @Override
        public void handleMouseRelease(MouseEvent e) {}
       
        @Override
        public void handleKeyDown(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        CopyReadyScene.getSingleton(), this);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        this.mReturnScene, null);
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


    public static class MoveScene extends JMIScene {
        // singleton pattern
        private static MoveScene mSingleton = null;
        public static MoveScene getSingleton() {
            assert(MoveScene.mSingleton != null);
            return MoveScene.mSingleton;
        }
        public static MoveScene createSingleton(XScenario scenario) {
            assert(MoveScene.mSingleton == null);
            MoveScene.mSingleton = new MoveScene(scenario);
            return MoveScene.mSingleton;
        }
        private MoveScene(XScenario scenario) {
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
        public void getReady() {}
        
        @Override
        public void wrapUp() {}
    }


    public static class CopyReadyScene extends JMIScene {
        // singleton pattern
        private static CopyReadyScene mSingleton = null;
        public static CopyReadyScene getSingleton() {
            assert(CopyReadyScene.mSingleton != null);
            return CopyReadyScene.mSingleton;
        }
        public static CopyReadyScene createSingleton(XScenario scenario) {
            assert(CopyReadyScene.mSingleton == null);
            CopyReadyScene.mSingleton = new CopyReadyScene(scenario);
            return CopyReadyScene.mSingleton;
        }
        private CopyReadyScene(XScenario scenario) {
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
        public void handleKeyUp(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        this.mReturnScene.getReturnScene(), null);
                    break;
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        this.mReturnScene, this.mReturnScene.getReturnScene());
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


    public static class CopyScene extends JMIScene {
        // singleton pattern
        private static CopyScene mSingleton = null;
        public static CopyScene getSingleton() {
            assert(CopyScene.mSingleton != null);
            return CopyScene.mSingleton;
        }
        public static CopyScene createSingleton(XScenario scenario) {
            assert(CopyScene.mSingleton == null);
            CopyScene.mSingleton = new CopyScene(scenario);
            return CopyScene.mSingleton;
        }
        private CopyScene(XScenario scenario) {
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
        public void getReady() {}
        
        @Override
        public void wrapUp() {}
    }
}