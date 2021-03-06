package jmi.scenario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import jmi.JMIApp;
import jmi.JMIPaint;
import jmi.JMIPaintMgr;
import jmi.JMIPaintMixable;
import jmi.JMIScene;

import jmi.cmd.JMICmdToUpdatePaintVolumeOfBrush;
import jmi.cmd.JMICmdToGeneratePaint;
import jmi.cmd.JMICmdToInitBrush;
import jmi.cmd.JMICmdToMixPaint;
import jmi.cmd.JMICmdToMixPaintDynamically;
import jmi.cmd.JMICmdToChangeColorForBrush;
import jmi.cmd.JMICmdToChooseColorForJSI;

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
            app.getBrush().setPt(e.getPoint());

            JMICmdToUpdatePaintVolumeOfBrush.execute(app);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());

            JMICmdToChooseColorForJSI.execute(app);

            if (app.getBrush().getVolume() > 0) {
                XCmdToChangeScene.execute(app, 
                    JMIColorScenario.PaintGenerateScene.getSingleton(), this.mReturnScene);
            }
            else {
                XCmdToChangeScene.execute(app, 
                    this.mReturnScene, null);
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
        public void getReady() {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            Point pt = app.getBrush().getPt();
            Color c = app.getPaintMgr().getPaint(pt).getColor();

            JMICmdToChangeColorForBrush.execute(app, c);
        }
        
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
        public void handleMousePress(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
            Point pt = app.getBrush().getPt();

            ArrayList<JMIPaintMixable> mPaints = app.getPaintMgr().getPaints();
            ArrayList<JMIPaint> mBasicPaints = app.getPaintMgr().getBasicPaints();
            ArrayList<JMIPaint> mCustomPaints = app.getPaintMgr().getCustomPaints();
            JMIPaint paint = app.getPaintMgr().getPaint(pt);

            if (paint == null) {
                JMICmdToGeneratePaint.execute(app, app.getBrush());
                JMICmdToInitBrush.execute(app);
            }
            else {
                if (mPaints.contains(paint)) {
                    JMICmdToGeneratePaint.execute(app, app.getBrush());
                    JMICmdToInitBrush.execute(app);
                }
                else if (mCustomPaints.contains(paint)) {
                    
                }
                else {
                    if (paint.getColor() != app.getBrush().getColor())
                        JMICmdToInitBrush.execute(app);
                    XCmdToChangeScene.execute(app, 
                        JMIColorScenario.PaintSelectScene.getSingleton(), this);
                }
            }

            // Check overlaps.
            // If paints are overlaped, mix them.
            JMIPaintMgr paintMgr = app.getPaintMgr();
            JMIPaintMixable lastPaint = paintMgr.getLastPaint();
            JMIPaintMixable overlap = paintMgr.getOverlap(lastPaint);
            while (overlap != null) {
                JMICmdToMixPaint.execute(app, lastPaint, overlap);

                lastPaint = paintMgr.getLastPaint();
                overlap = paintMgr.getOverlap(lastPaint);
            }
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
            Point pt = app.getBrush().getPt();

            // Dynamic Paint mixing.
            JMIPaint paint = app.getPaintMgr().getPaint(pt);
            if (app.getPaintMgr().getPaints().contains(paint)) {
                JMICmdToMixPaintDynamically.execute(app, (JMIPaintMixable) paint);
            }

            // Check overlaps.
            // If paints are overlaped, mix them.
            JMIPaintMgr paintMgr = app.getPaintMgr();
            JMIPaintMixable lastPaint = paintMgr.getLastPaint();
            JMIPaintMixable overlap = paintMgr.getOverlap(lastPaint);
            while (overlap != null) {
                JMICmdToMixPaint.execute(app, lastPaint, overlap);

                lastPaint = paintMgr.getLastPaint();
                overlap = paintMgr.getOverlap(lastPaint);
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
            Point pt = app.getBrush().getPt();

            JMIPaint paint = app.getPaintMgr().getPaint(pt);
            if (paint != null) {
                Color c = paint.getColor();
                JMICmdToChangeColorForBrush.execute(app, c);
            }
            JMICmdToChooseColorForJSI.execute(app);

            if (app.getBrush().getVolume() != 0) {
                JMICmdToInitBrush.execute(app);   
            }

            XCmdToChangeScene.execute(app, 
                JMIDefaultScenario.ReadyScene.getSingleton(), null);
        }
       
        @Override
        public void handleKeyDown(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();

            switch(code) {
                case KeyEvent.VK_ESCAPE:
                    JMICmdToInitBrush.execute(app);
                    XCmdToChangeScene.execute(app, 
                        this.mReturnScene, null);
                    break;
            }
        }

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
