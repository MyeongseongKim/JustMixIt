package jsi;

import javax.swing.JFrame;

import jmi.JMIApp;

import x.XApp;
import x.XScenarioMgr;
import x.XLogMgr;


public class JSIApp extends XApp {
    // fields
    private JFrame mFrame = null;

    private JSICanvas2D mCanvas2D = null;
    public JSICanvas2D getCanvas2D() {
        return this.mCanvas2D;
    }
    
    private JSIXform mXform = null;
    public JSIXform getXform() {
        return this.mXform;
    }
    
    private JSIColorChooser mColorChooser = null;
    public JSIColorChooser getColorChooser() {
        return this.mColorChooser;
    }

    private JMIApp mJMI = null;
    public JMIApp getJMI() {
        return this.mJMI;
    }
    
    private JSIEventListener mEventListener = null;

    private JSIPenMarkMgr mPenMarkMgr = null;
    public JSIPenMarkMgr getPenMarkMgr() {
        return this.mPenMarkMgr;
    }
    
    private JSIPtCurveMgr mPtCurveMgr = null;
    public JSIPtCurveMgr getPtCurveMgr() {
        return this.mPtCurveMgr;
    }

    private XScenarioMgr mScenarioMgr = null;
    @Override
    public XScenarioMgr getScenarioMgr() {
        return this.mScenarioMgr;
    }

    private XLogMgr mLogMgr = null;
    @Override
    public XLogMgr getLogMgr() {
        return this.mLogMgr;
    }
    
    // constructor
    public JSIApp() {
        // step 1: create components.
        this.mFrame = new JFrame("JustSketchIt");
        this.mCanvas2D = new JSICanvas2D(this);
        this.mXform = new JSIXform();
        this.mColorChooser = new JSIColorChooser();
        this.mJMI = new JMIApp(this);
        this.mEventListener = new JSIEventListener(this);
        this.mPenMarkMgr = new JSIPenMarkMgr();
        this.mPtCurveMgr = new JSIPtCurveMgr();
        this.mScenarioMgr = new JSIScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(false);
        
        // step 2 : build and show visual components.
        this.mFrame.add(this.mCanvas2D);
        this.mFrame.setSize(1200, 800);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mFrame.setVisible(true);
        
        // step 3: conenct event listeners.
        this.mCanvas2D.addMouseListener(this.mEventListener);
        this.mCanvas2D.addMouseMotionListener(this.mEventListener);
        this.mCanvas2D.setFocusable(true);
        this.mCanvas2D.addKeyListener(this.mEventListener);
    }

    public static void main(String[] args) {
        new JSIApp();
    }
}
