package jsi.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIScene;
import jsi.JSIXform;

import jsi.cmd.JSICmdToSetStartingScreenPtForXform;
import jsi.cmd.JSICmdToTranslateTo;
import jsi.cmd.JSICmdToZoomNRotate;

import x.XApp;
import x.XScenario;
import x.XCmdToChangeScene;


public class JSINavigateScenario extends XScenario {
     // singleton pattern
    private static JSINavigateScenario mSingleton = null;
    public static JSINavigateScenario getSingleton() {
        assert(JSINavigateScenario.mSingleton != null);
        return JSINavigateScenario.mSingleton;
    }
    public static JSINavigateScenario createSingleton(XApp app) {
        assert(JSINavigateScenario.mSingleton == null);
        JSINavigateScenario.mSingleton = new JSINavigateScenario(app);
        return JSINavigateScenario.mSingleton;
    }
    private JSINavigateScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(JSINavigateScenario.ZoomNRotateReadyScene.createSingleton(this));
        this.addScene(JSINavigateScenario.ZoomNRotateScene.createSingleton(this));
        this.addScene(JSINavigateScenario.PanReadyScene.createSingleton(this));
        this.addScene(JSINavigateScenario.PanScene.createSingleton(this));
    }
    
    
    public static class ZoomNRotateReadyScene extends JSIScene {
        // singleton pattern
        private static ZoomNRotateReadyScene mSingleton = null;
        public static ZoomNRotateReadyScene getSingleton() {
            assert(ZoomNRotateReadyScene.mSingleton != null);
            return ZoomNRotateReadyScene.mSingleton;
        }
        public static ZoomNRotateReadyScene createSingleton(XScenario scenario) {
            assert(ZoomNRotateReadyScene.mSingleton == null);
            ZoomNRotateReadyScene.mSingleton = new ZoomNRotateReadyScene(scenario);
            return ZoomNRotateReadyScene.mSingleton;
        }
        
        private ZoomNRotateReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToSetStartingScreenPtForXform.execute(app, pt);
            XCmdToChangeScene.execute(app, JSINavigateScenario.
                ZoomNRotateScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {}
  
        @Override
        public void handleMouseRelease(MouseEvent e) {}
    
        @Override
        public void handleKeyDown(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, JSINavigateScenario.
                        PanReadyScene.getSingleton(), this.mReturnScene);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, this.mReturnScene, null);
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
    
     public static class ZoomNRotateScene extends JSIScene {
        // singleton pattern
        private static ZoomNRotateScene mSingleton = null;
        public static ZoomNRotateScene getSingleton() {
            assert(ZoomNRotateScene.mSingleton != null);
            return ZoomNRotateScene.mSingleton;
        }
        public static ZoomNRotateScene createSingleton(XScenario scenario) {
            assert(ZoomNRotateScene.mSingleton == null);
            ZoomNRotateScene.mSingleton = new ZoomNRotateScene(scenario);
            return ZoomNRotateScene.mSingleton;
        }
        
        private ZoomNRotateScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}
  
        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToZoomNRotate.execute(app, pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToSetStartingScreenPtForXform.execute(app, null);
            XCmdToChangeScene.execute(app, JSINavigateScenario.
                ZoomNRotateReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            Point pt = app.getPenMarkMgr().getLastPenMark().getLastPt();
            
            switch (code) {
                case KeyEvent.VK_ALT:
                    JSICmdToSetStartingScreenPtForXform.execute(app, pt);
                    XCmdToChangeScene.execute(app, JSINavigateScenario.
                        PanScene.getSingleton(), this.mReturnScene);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_CONTROL:
                    JSICmdToSetStartingScreenPtForXform.execute(app, null);
                    XCmdToChangeScene.execute(app, this.mReturnScene, null);
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {}

        @Override
        public void renderWorldObjects(Graphics2D g2) {}
  
        @Override
        public void renderScreenObjects(Graphics2D g2) {
            JSINavigateScenario.getSingleton().drawZoomNRotateCrossHair(g2);
        }

        @Override
        public void getReady() {}
       
        @Override
        public void wrapUp() {}
    }
     
     public static class PanReadyScene extends JSIScene {
        // singleton pattern
        private static PanReadyScene mSingleton = null;
        public static PanReadyScene getSingleton() {
            assert(PanReadyScene.mSingleton != null);
            return PanReadyScene.mSingleton;
        }
        public static PanReadyScene createSingleton(XScenario scenario) {
            assert(PanReadyScene.mSingleton == null);
            PanReadyScene.mSingleton = new PanReadyScene(scenario);
            return PanReadyScene.mSingleton;
        }
        
        private PanReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToSetStartingScreenPtForXform.execute(app, pt);
            XCmdToChangeScene.execute(app, JSINavigateScenario.
                PanScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {}
     
        @Override
        public void handleMouseRelease(MouseEvent e) {}
     
        @Override
        public void handleKeyDown(KeyEvent e) {}
   
        @Override
        public void handleKeyUp(KeyEvent e) {
           JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, JSINavigateScenario.
                        ZoomNRotateReadyScene.getSingleton(), this.mReturnScene);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, this.mReturnScene, null);
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
     
     public static class PanScene extends JSIScene {
        // singleton pattern
        private static PanScene mSingleton = null;
        public static PanScene getSingleton() {
            assert(PanScene.mSingleton != null);
            return PanScene.mSingleton;
        }
        public static PanScene createSingleton(XScenario scenario) {
            assert(PanScene.mSingleton == null);
            PanScene.mSingleton = new PanScene(scenario);
            return PanScene.mSingleton;
        }
        
        private PanScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}
   
        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToTranslateTo.execute(app, pt);       
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToSetStartingScreenPtForXform.execute(app, null);
            XCmdToChangeScene.execute(app, JSINavigateScenario.
                PanReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {}
      
        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            Point pt = app.getPenMarkMgr().getLastPenMark().getLastPt();
            switch(code) {
                case KeyEvent.VK_ALT:
                    JSICmdToSetStartingScreenPtForXform.execute(app, pt);
                    XCmdToChangeScene.execute(app, JSINavigateScenario.
                        ZoomNRotateScene.getSingleton(), this.mReturnScene);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, this.mReturnScene, null);
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {}
      
        @Override
        public void renderWorldObjects(Graphics2D g2) {}

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            JSINavigateScenario scenario = (JSINavigateScenario) this.mScenario;
            scenario.drawPanCrossHair(g2);
        }

        @Override
        public void getReady() {}
       
        @Override
        public void wrapUp() {}    
    }
     
    public void drawZoomNRotateCrossHair(Graphics2D g2) {
        double r = JSICanvas2D.ZOOM_N_ROTATE_CROSSHAIR_RADIUS;
        Point ctr = JSIXform.PIVOT_PT;

        Line2D.Double hline = new Line2D.Double(ctr.x-r, ctr.y, ctr.x+r, ctr.y);
        Line2D.Double vline = new Line2D.Double(ctr.x, ctr.y-r, ctr.x, ctr.y+r);

        g2.setColor(JSICanvas2D.COLOR_CROSSHAIR);
        g2.setStroke(JSICanvas2D.STROKE_CROSSHAIR);
        g2.draw(hline);
        g2.draw(vline);
    }
    
    private void drawPanCrossHair (Graphics2D g2) {
        JSIApp app = (JSIApp) this.mApp;
        Point penPt = app.getPenMarkMgr().getLastPenMark().getLastPt();
        Line2D.Double hline = new Line2D.Double(0,penPt.y,app.getCanvas2D().getWidth(), penPt.y);
        Line2D.Double vline = new Line2D.Double(penPt.x, 0, penPt.x, app.getCanvas2D().getHeight());
        
        g2.setColor(JSICanvas2D.COLOR_CROSSHAIR);
        g2.setStroke(JSICanvas2D.STROKE_CROSSHAIR);
        g2.draw(hline);
        g2.draw(vline);
    }
}
