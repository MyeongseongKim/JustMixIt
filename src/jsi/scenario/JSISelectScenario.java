package jsi.scenario;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import jmi.JMIApp;
import jmi.scenario.JMIDefaultScenario;

import jsi.JSIApp;
import jsi.JSICanvas2D;
import jsi.JSIPenMark;
import jsi.JSIPtCurve;
import jsi.JSIScene;
import jsi.JSISelectionBox;

import jsi.cmd.JSICmdToCreateSelectionBox;
import jsi.cmd.JSICmdToDeleteSelectedPtCurves;
import jsi.cmd.JSICmdToDeselectSelectedPtCurves;
import jsi.cmd.JSICmdToDestroySelectionBox;
import jsi.cmd.JSICmdToHome;
import jsi.cmd.JSICmdToIncreaseStrokeWidthForSelectedPtCurves;
import jsi.cmd.JSICmdToUpdateSelectedPtCurves;
import jsi.cmd.JSICmdToUpdateSelectionBox;

import x.XApp;
import x.XScenario;
import x.XCmdToChangeScene;


public class JSISelectScenario extends XScenario {
     // singleton pattern
    private static JSISelectScenario mSingleton = null;
    public static JSISelectScenario getSingleton() {
        assert(JSISelectScenario.mSingleton != null);
        return JSISelectScenario.mSingleton;
    }
    public static JSISelectScenario createSingleton(XApp app) {
        assert(JSISelectScenario.mSingleton == null);
        JSISelectScenario.mSingleton = new JSISelectScenario(app);
        return JSISelectScenario.mSingleton;
    }
    private JSISelectScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(JSISelectScenario.SelectReadyScene.createSingleton(this));
        this.addScene(JSISelectScenario.SelectScene.createSingleton(this));
        this.addScene(JSISelectScenario.SelectedReadyScene.createSingleton(this));
        this.addScene(JSISelectScenario.TapGestureScene.createSingleton(this));
    }
    
    public static class SelectReadyScene extends JSIScene {
        // singleton pattern
        private static SelectReadyScene mSingleton = null;
        public static SelectReadyScene getSingleton() {
            assert(SelectReadyScene.mSingleton != null);
            return SelectReadyScene.mSingleton;
        }
        public static SelectReadyScene createSingleton(XScenario scenario) {
            assert(SelectReadyScene.mSingleton == null);
            SelectReadyScene.mSingleton = new SelectReadyScene(scenario);
            return SelectReadyScene.mSingleton;
        }
        
        private SelectReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToCreateSelectionBox.execute(app, pt);
            XCmdToChangeScene.execute(app, 
                JSISelectScenario.SelectScene.getSingleton(), null);
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
                case KeyEvent.VK_SHIFT:
                    if (app.getPtCurveMgr().getSelectedPtCurves().isEmpty()){
                        XCmdToChangeScene.execute(app, 
                            JSIDefaultScenario.ReadyScene.getSingleton(), null);
                    } 
                    else {
                        XCmdToChangeScene.execute(app, 
                            JSISelectScenario.SelectedReadyScene.getSingleton(),
                            null);
                    }   
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
    
    public static class SelectScene extends JSIScene {
        // singleton pattern
        private static SelectScene mSingleton = null;
        public static SelectScene getSingleton() {
            assert(SelectScene.mSingleton != null);
            return SelectScene.mSingleton;
        }
        public static SelectScene createSingleton(XScenario scenario) {
            assert(SelectScene.mSingleton == null);
            SelectScene.mSingleton = new SelectScene(scenario);
            return SelectScene.mSingleton;
        }
        
        private SelectScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}
      
        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            JSICmdToUpdateSelectionBox.execute(app, pt);
            JSICmdToUpdateSelectedPtCurves.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            JSICmdToDestroySelectionBox.execute(app);
            XCmdToChangeScene.execute(app, 
                JSISelectScenario.SelectReadyScene.getSingleton(), 
                null);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {}
   
        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_SHIFT:
                    JSICmdToDestroySelectionBox.execute(app);
                    if (app.getPtCurveMgr().getSelectedPtCurves().isEmpty()){
                        XCmdToChangeScene.execute(app, 
                            JSIDefaultScenario.ReadyScene.getSingleton(), null);
                    } 
                    else {
                        XCmdToChangeScene.execute(app, 
                            JSISelectScenario.SelectedReadyScene.getSingleton(),
                            null);
                    }
                    break;         
            }
        }

        @Override
        public void updateSupportObjects() {}
 
        @Override
        public void renderWorldObjects(Graphics2D g2) {}

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSISelectScenario scenario = (JSISelectScenario) this.mScenario;
            scenario.drawSelectionBox(g2);
        }

        @Override
        public void getReady() {}
 
        @Override
        public void wrapUp() {}  
    }
     
    public static class SelectedReadyScene extends JSIScene {
        // singleton pattern
        private static SelectedReadyScene mSingleton = null;
        public static SelectedReadyScene getSingleton() {
            assert(SelectedReadyScene.mSingleton != null);
            return SelectedReadyScene.mSingleton;
        }
        public static SelectedReadyScene createSingleton(XScenario scenario) {
            assert(SelectedReadyScene.mSingleton == null);
            SelectedReadyScene.mSingleton = new SelectedReadyScene(scenario);
            return SelectedReadyScene.mSingleton;
        }
        
        private SelectedReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            XCmdToChangeScene.execute(app, JSISelectScenario.
                TapGestureScene.getSingleton(), this);
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
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(app, 
                        JSISelectScenario.SelectReadyScene.getSingleton(), null);
                    break;
                case KeyEvent.VK_UP:
                    JSICmdToIncreaseStrokeWidthForSelectedPtCurves.
                        execute(app, 1.0f);
                    break;
                case KeyEvent.VK_DOWN:
                    JSICmdToIncreaseStrokeWidthForSelectedPtCurves.
                        execute(app, -1.0f);
                    break;
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, JSINavigateScenario.
                        ZoomNRotateReadyScene.getSingleton(), this);
                    break;
                // case KeyEvent.VK_C:
                //     XCmdToChangeScene.execute(app, 
                //         JSIColorScenario.ColorReadyScene.getSingleton(), this);
                //     break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_HOME:
                    JSICmdToHome.execute(app);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    JSICmdToDeleteSelectedPtCurves.execute(app);
                    XCmdToChangeScene.execute(app, 
                        JSIDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
                case KeyEvent.VK_DELETE:
                    JSICmdToDeleteSelectedPtCurves.execute(app);
                    XCmdToChangeScene.execute(app, 
                        JSIDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
                case KeyEvent.VK_ESCAPE:
                    JSICmdToDeselectSelectedPtCurves.execute(app);
                    XCmdToChangeScene.execute(app, 
                        JSIDefaultScenario.ReadyScene.getSingleton(), null);
                    break;
                case KeyEvent.VK_C:
                    JMIApp jmi = app.getJMI();
                    if (jmi.getScenarioMgr().getCurScene() == 
                        JMIDefaultScenario.StandbyScene.getSingleton()) {
                        
                        XCmdToChangeScene.execute(jmi, 
                            JMIDefaultScenario.ReadyScene.getSingleton(), 
                            JMIDefaultScenario.StandbyScene.getSingleton());
                    }
                    else if (jmi.getScenarioMgr().getCurScene() == 
                        JMIDefaultScenario.ReadyScene.getSingleton()) {
                        
                        XCmdToChangeScene.execute(jmi, 
                            JMIDefaultScenario.StandbyScene.getSingleton(), null);
                    }
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
    
    private JSISelectionBox mSelectionBox = null;
    public JSISelectionBox getSelectionBox() {
        return this.mSelectionBox;
    }
    public void setSelectionBox(JSISelectionBox selectionBox) {
        this.mSelectionBox = selectionBox;
    }
    
    private void drawSelectionBox(Graphics2D g2) {
        if (this.mSelectionBox != null) {
            g2.setColor(JSICanvas2D.COLOR_SELECTION_BOX);
            g2.setStroke(JSICanvas2D.STROKE_SELECTION_BOX);
            g2.draw(this.mSelectionBox);
        }
    }
   
    public void updateSelectedPtCurves() {
        JSIApp app = (JSIApp) this.mApp;
        AffineTransform at = app.getXform().getCurXformFromScreenToWorld();
        Shape worldSelectionBoxShape = at.createTransformedShape(
                this.mSelectionBox);
        
        ArrayList<JSIPtCurve> newlySelectedPtCurves =
                new ArrayList<JSIPtCurve>();
        for (JSIPtCurve ptCurve : app.getPtCurveMgr().getPtCurves()) {
            if (worldSelectionBoxShape.intersects(ptCurve.getBoundingBox()) ||
                    ptCurve.getBoundingBox().isEmpty()) {
                for (Point2D.Double worldPt : ptCurve.getPts()) {
                    if (worldSelectionBoxShape.contains(worldPt)) {
                        newlySelectedPtCurves.add(ptCurve);
                        break;
                    }
                }
            }
        }
        app.getPtCurveMgr().getPtCurves().removeAll(newlySelectedPtCurves);
        app.getPtCurveMgr().getSelectedPtCurves().addAll(newlySelectedPtCurves);
    }
    
    public static class TapGestureScene extends JSIScene {
        // singleton pattern
        private static TapGestureScene mSingleton = null;
        public static TapGestureScene getSingleton() {
            assert(TapGestureScene.mSingleton != null);
            return TapGestureScene.mSingleton;
        }
        public static TapGestureScene createSingleton(XScenario scenario) {
            assert(TapGestureScene.mSingleton == null);
            TapGestureScene.mSingleton = new TapGestureScene(scenario);
            return TapGestureScene.mSingleton;
        }
        
        private TapGestureScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}

        @Override
        public void handleMouseDrag(MouseEvent e) {}

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            
            JSIPenMark penMark = app.getPenMarkMgr().getLastPenMark();
            if (penMark.getPts().size() == 1) {
                JSICmdToDeselectSelectedPtCurves.execute(app);
                XCmdToChangeScene.execute(app, JSIDefaultScenario.ReadyScene.
                    getSingleton(), null);
            } 
            else {
                XCmdToChangeScene.execute(app, this.mReturnScene, null);
            }
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
