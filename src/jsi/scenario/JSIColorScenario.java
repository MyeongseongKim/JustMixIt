package jsi.scenario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jsi.JSIApp;
import jsi.JSIColorChooser;
import jsi.JSIScene;

import jsi.cmd.JSICmdToChangeColorForCurPtCurve;
import jsi.cmd.JSICmdToChangeColorForSelectedPtCurves;
import jsi.cmd.JSICmdToRecalcOpaquenessOfColorChooser;
import jsi.cmd.JSICmdToRecalcSaturationOfColorChooser;
import jsi.cmd.JSICmdToSetAnchorOpaqueness;
import jsi.cmd.JSICmdToSetAnchorSaturation;

import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;


public class JSIColorScenario extends XScenario {
     // singleton pattern
    private static JSIColorScenario mSingleton = null;
    public static JSIColorScenario getSingleton() {
        assert(JSIColorScenario.mSingleton != null);
        return JSIColorScenario.mSingleton;
    }
    public static JSIColorScenario createSingleton(XApp app) {
        assert(JSIColorScenario.mSingleton == null);
        JSIColorScenario.mSingleton = new JSIColorScenario(app);
        return JSIColorScenario.mSingleton;
    }
    private JSIColorScenario(XApp app) {
        super(app);
        this.mColorChooser = new JSIColorChooser();
    }

    @Override
    protected void addScenes() {
        this.addScene(JSIColorScenario.ColorReadyScene.createSingleton(this));
        this.addScene(JSIColorScenario.ColorScene.createSingleton(this));
        this.addScene(JSIColorScenario.ChangeSaturationScene.createSingleton(this));
        this.addScene(JSIColorScenario.ChangeOpaquenessScene.createSingleton(this));
    }
    
    
    public static class ColorReadyScene extends JSIScene {
        // singleton pattern
        private static ColorReadyScene mSingleton = null;
        public static ColorReadyScene getSingleton() {
            assert(ColorReadyScene.mSingleton != null);
            return ColorReadyScene.mSingleton;
        }
        public static ColorReadyScene createSingleton(XScenario scenario) {
            assert(ColorReadyScene.mSingleton == null);
            ColorReadyScene.mSingleton = new ColorReadyScene(scenario);
            return ColorReadyScene.mSingleton;
        }
        
        private ColorReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            Point pt = e.getPoint();
            JSIColorChooser.Area area = scenario.getColorChooser().
                decideArea(pt, app.getCanvas2D().getWidth(), 
                app.getCanvas2D().getHeight());
         
            switch(area) {
                case HUE:
                    XCmdToChangeScene.execute(app, 
                        JSIColorScenario.ColorScene.getSingleton(), 
                        this.mReturnScene);
                    break;
                case SATURATION:
                    JSICmdToSetAnchorSaturation.execute(app);
                    XCmdToChangeScene.execute(app, JSIColorScenario.
                        ChangeSaturationScene.getSingleton(), this.mReturnScene);
                    break;
                case OPAQUENESS:
                    JSICmdToSetAnchorOpaqueness.execute(app);
                    XCmdToChangeScene.execute(app, JSIColorScenario.
                        ChangeOpaquenessScene.getSingleton(), this.mReturnScene);
                    break;
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
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_C:
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
            JSIColorScenario.getSingleton().drawColorChooser(g2);
        }

        @Override
        public void getReady() {}

        @Override
        public void wrapUp() {}
    
    }
    
    public static class ColorScene extends JSIScene {
        // singleton pattern
        private static ColorScene mSingleton = null;
        public static ColorScene getSingleton() {
            assert(ColorScene.mSingleton != null);
            return ColorScene.mSingleton;
        }
        public static ColorScene createSingleton(XScenario scenario) {
            assert(ColorScene.mSingleton == null);
            ColorScene.mSingleton = new ColorScene(scenario);
            return ColorScene.mSingleton;
        }
        
        private ColorScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}
    
        @Override
        public void handleMouseDrag(MouseEvent e) {}

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            Point pt = e.getPoint();
            
            Color c = JSIColorScenario.getSingleton().getColorChooser()
                .getColor(pt, app.getCanvas2D().getWidth(), 
                app.getCanvas2D().getHeight());
          
            if (app.getPtCurveMgr().getSelectedPtCurves().isEmpty()) {
                JSICmdToChangeColorForCurPtCurve.execute(app, c);
            }
            else {
                JSICmdToChangeColorForSelectedPtCurves.execute(app, c);
            }
            XCmdToChangeScene.execute(app, this.mReturnScene, null);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {}

        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch(code) {
                case KeyEvent.VK_C:
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
            JSIColorScenario.getSingleton().drawColorChooser(g2);
        }

        @Override
        public void getReady() {}

        @Override
        public void wrapUp() {}
    
    }
    
    public static class ChangeSaturationScene extends JSIScene {
        // singleton pattern
        private static ChangeSaturationScene mSingleton = null;
        public static ChangeSaturationScene getSingleton() {
            assert(ChangeSaturationScene.mSingleton != null);
            return ChangeSaturationScene.mSingleton;
        }
        public static ChangeSaturationScene createSingleton(XScenario scenario) {
            assert(ChangeSaturationScene.mSingleton == null);
            ChangeSaturationScene.mSingleton = new ChangeSaturationScene(scenario);
            return ChangeSaturationScene.mSingleton;
        }
        
        private ChangeSaturationScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}
       
        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSICmdToRecalcSaturationOfColorChooser.execute(app);   
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            XCmdToChangeScene.execute(app,JSIColorScenario.
                ColorReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {}
         
        @Override
        public void handleKeyUp(KeyEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_C:
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
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            scenario.drawColorChooser(g2);  
        }
        @Override
        public void getReady() {}
         
        @Override
        public void wrapUp() {}     
    }
    
    public static class ChangeOpaquenessScene extends JSIScene {
        // singleton pattern
        private static ChangeOpaquenessScene mSingleton = null;
        public static ChangeOpaquenessScene getSingleton() {
            assert(ChangeOpaquenessScene.mSingleton != null);
            return ChangeOpaquenessScene.mSingleton;
        }
        public static ChangeOpaquenessScene createSingleton(XScenario scenario) {
            assert(ChangeOpaquenessScene.mSingleton == null);
            ChangeOpaquenessScene.mSingleton = new ChangeOpaquenessScene(scenario);
            return ChangeOpaquenessScene.mSingleton;
        }
        
        private ChangeOpaquenessScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {}
       
        @Override
        public void handleMouseDrag(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            JSICmdToRecalcOpaquenessOfColorChooser.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JSIApp app = (JSIApp) this.mScenario.getApp();
            XCmdToChangeScene.execute(app,JSIColorScenario.
                ColorReadyScene.getSingleton(), this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {}
        
        @Override
        public void handleKeyUp(KeyEvent e) {
             JSIApp app = (JSIApp) this.mScenario.getApp();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_C:
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
            JSIColorScenario scenario = (JSIColorScenario) this.mScenario;
            scenario.drawColorChooser(g2);  
        }
        
        @Override
        public void getReady() {}
        
        @Override
        public void wrapUp() {}
    }
    
    private JSIColorChooser mColorChooser = null;
    public JSIColorChooser getColorChooser() {
        return this.mColorChooser;
    }
    
    public void drawColorChooser(Graphics2D g2) {
        JSIApp app = (JSIApp) this.mApp;
        this.mColorChooser.drawCells(g2, app.getCanvas2D().getWidth(), 
            app.getCanvas2D().getHeight());
    } 
}
