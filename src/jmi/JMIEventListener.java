package jmi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class JMIEventListener implements MouseListener, MouseMotionListener, KeyListener {
    
    private JMIApp mApp = null;
    public JMIEventListener(JMIApp app) {
        this.mApp = app;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        curScene.handleMousePress(e);
        this.mApp.getPalette2D().repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        curScene.handleMouseDrag(e);
        this.mApp.getPalette2D().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        curScene.handleMouseRelease(e);
        this.mApp.getPalette2D().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mApp.getBrush().setPt(e.getPoint());
        this.mApp.getPalette2D().repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        curScene.handleKeyDown(e);
        this.mApp.getPalette2D().repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        JMIScene curScene = (JMIScene) this.mApp.getScenarioMgr().getCurScene();
        curScene.handleKeyUp(e);
        this.mApp.getPalette2D().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
