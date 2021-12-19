package jmi.scenario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;

import jmi.JMIApp;
import jmi.JMIPaint;
import jmi.JMIPaintMgr;
import jmi.JMIPaintMixable;
import jmi.JMIScene;

import jmi.cmd.JMICmdToChangeColorForBrush;
import jmi.cmd.JMICmdToPickPaint;
import jmi.cmd.JMICmdToRemovePickedPaint;
import jmi.cmd.JMICmdToCopyPaint;
import jmi.cmd.JMICmdToMixPaint;
import jmi.cmd.JMICmdToMixWithPaint;
import jmi.cmd.JMICmdToMovePaint;
import jmi.cmd.JMICmdToSetPaintForCustomPalette;
import jmi.cmd.JMICmdToSetPickedPaint;

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
            Point pt = app.getBrush().getPt();

            JMIPaint paint = app.getPaintMgr().getPaint(pt);
            if (app.getPaintMgr().getPaints().contains(paint)) {
                XCmdToChangeScene.execute(app, 
                    JMIOrganizeScenario.MoveScene.getSingleton(), this.mReturnScene);
            }
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {}
       
        @Override
        public void handleKeyDown(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        JMIOrganizeScenario.CopyReadyScene.getSingleton(), this.mReturnScene);
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
        public void getReady() {
            JMIApp app = (JMIApp)this.mScenario.getApp();

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
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());

            JMIPaint paint = app.getPaintMgr().getPickedPaint();
            JMICmdToMovePaint.execute(app, (JMIPaintMixable) paint);           
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
            Point pt = app.getBrush().getPt();

            ArrayList<JMIPaintMixable> paints = app.getPaintMgr().getPaints();
            ArrayList<JMIPaint> customPaints = app.getPaintMgr().getCustomPaints();
            JMIPaint paint = app.getPaintMgr().getPaint(pt);

            if (paint == null) {
                JMICmdToSetPickedPaint.execute(app);
            }
            else if (paints.contains(paint)) {
                JMICmdToMixWithPaint.execute(app, (JMIPaintMixable) paint);
                JMICmdToRemovePickedPaint.execute(app);
            }
            else if (customPaints.contains(paint)) {
                int index = customPaints.indexOf(paint);
                Color c = app.getBrush().getColor();
                JMICmdToSetPaintForCustomPalette.execute(app, index, c);
                JMICmdToRemovePickedPaint.execute(app);
            }
            else {
                JMICmdToRemovePickedPaint.execute(app);
            }

            XCmdToChangeScene.execute(app, 
                JMIOrganizeScenario.MoveReadyScene.getSingleton(), this.mReturnScene);
        }
       
        @Override
        public void handleKeyDown(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        JMIOrganizeScenario.CopyReadyScene.getSingleton(), this.mReturnScene);
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
        public void renderScreenObjects(Graphics2D g2) {
            JMIOrganizeScenario.getSingleton().drawPickedPaint(g2);
        }
        
        @Override
        public void getReady() {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            Point pt = app.getBrush().getPt();
            Color c = app.getPaintMgr().getPaint(pt).getColor();

            JMICmdToPickPaint.execute(app);
            JMICmdToChangeColorForBrush.execute(app, c);
        }
        
        @Override
        public void wrapUp() {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            if (app.getPaintMgr().getPickedPaint() != null) {
                JMICmdToSetPickedPaint.execute(app);
            }
        }
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
        public void handleMousePress(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
            Point pt = app.getBrush().getPt();

            JMIPaint paint = app.getPaintMgr().getPaint(pt);
            if (app.getPaintMgr().getPaints().contains(paint)) {
                XCmdToChangeScene.execute(app, 
                    JMIOrganizeScenario.CopyScene.getSingleton(), this.mReturnScene);
            }
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
        }

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
                        this.mReturnScene, null);
                    break;
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        JMIOrganizeScenario.MoveReadyScene.getSingleton(), this.mReturnScene);
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
        public void handleMouseDrag(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());

            JMIPaint paint = app.getPaintMgr().getPickedPaint();
            JMICmdToMovePaint.execute(app, (JMIPaintMixable) paint);      
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            app.getBrush().setPt(e.getPoint());
            Point pt = app.getBrush().getPt();

            ArrayList<JMIPaintMixable> paints = app.getPaintMgr().getPaints();
            ArrayList<JMIPaint> customPaints = app.getPaintMgr().getCustomPaints();
            JMIPaint paint = app.getPaintMgr().getPaint(pt);

            if (paint == null) {
                JMICmdToSetPickedPaint.execute(app);
            }
            else if (paints.contains(paint)) {
                JMICmdToMixWithPaint.execute(app, (JMIPaintMixable) paint);
                JMICmdToRemovePickedPaint.execute(app);
            }
            else if (customPaints.contains(paint)) {
                int index = customPaints.indexOf(paint);
                Color c = app.getBrush().getColor();
                JMICmdToSetPaintForCustomPalette.execute(app, index, c);
                JMICmdToRemovePickedPaint.execute(app);
            }
            else {
                JMICmdToRemovePickedPaint.execute(app);
            }

            XCmdToChangeScene.execute(app, 
                JMIOrganizeScenario.CopyReadyScene.getSingleton(), this.mReturnScene);
        }
       
        @Override
        public void handleKeyDown(KeyEvent e) {}

        @Override
        public void handleKeyUp(KeyEvent e) {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch(code) {
                case KeyEvent.VK_CONTROL:
                    XCmdToChangeScene.execute(app, 
                        this.mReturnScene, null);
                    break;
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(app, 
                        JMIOrganizeScenario.MoveReadyScene.getSingleton(), this.mReturnScene);
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {}
     
        @Override
        public void renderWorldObjects(Graphics2D g2) {}

        @Override
        public void renderScreenObjects(Graphics2D g2) {
            JMIOrganizeScenario.getSingleton().drawPickedPaint(g2);
        }
        
        @Override
        public void getReady() {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            Point pt = app.getBrush().getPt();
            Color c = app.getPaintMgr().getPaint(pt).getColor();

            JMICmdToCopyPaint.execute(app);
            JMICmdToChangeColorForBrush.execute(app, c);
        }
        
        @Override
        public void wrapUp() {
            JMIApp app = (JMIApp)this.mScenario.getApp();
            if (app.getPaintMgr().getPickedPaint() != null) {
                JMICmdToSetPickedPaint.execute(app);
            }
        }
    }


    public void drawPickedPaint(Graphics2D g2) {
        JMIApp app = (JMIApp) this.mApp;
        Point pt = app.getBrush().getPt();

        JMIPaintMixable paint = app.getPaintMgr().getPickedPaint();

        Color c = paint.getColor();
        Point2D ctr = paint.getPt();
        double r = paint.getRadius();

        Ellipse2D e = new Ellipse2D.Double(ctr.getX() - r, ctr.getY() - r, 2*r, 2*r);
        g2.setColor(c);
        g2.fill(e);

        if (c.getAlpha() == 0) {
            // final Color COLOR_WATERMARK = new Color(0, 63, 127, 63);
            float[] dist = {0.0f, 0.5f, 1.0f};
            // Color[] colors = {Color.WHITE, Color.WHITE, COLOR_WATERMARK};
            Color[] colors = {Color.WHITE, Color.WHITE, app.getPalette2D().COLOR_WATERMARK};
            RadialGradientPaint p =
                new RadialGradientPaint(ctr, (float) r, dist, colors);

            g2.setPaint(p);
            g2.fill(e);
        }
    }
}