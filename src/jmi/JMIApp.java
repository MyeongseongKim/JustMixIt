package jmi;

import javax.swing.JFrame;

import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;


public class JMIApp extends XApp {
    // fields
    private XApp mApp = null;
    public XApp getApp() {
        return mApp;
    }

    private JFrame mFrame = null;
	public JFrame getFrame() {
        return this.mFrame;
    }

    private JMIPalette2D mPalette2D = null;
    public JMIPalette2D getPalette2D() {
        return this.mPalette2D;
    }

    private JMIEventListener mEventListener = null;

    private JMIBrush mBrush = null;
    public JMIBrush getBrush() {
        return this.mBrush;
    }

    private JMIBrushMarkMgr mBrushMarkMgr = null;
    public JMIBrushMarkMgr getBrushMarkMgr() {
        return this.mBrushMarkMgr;
    }

    private JMIPaintMgr mPaintMgr = null;
    public JMIPaintMgr getPaintMgr() {
        return this.mPaintMgr;
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
    public JMIApp(XApp app) {
        this.mApp = app;

        // step 1: create components.
        this.mFrame = new JFrame("JustMixIt");
        this.mPalette2D = new JMIPalette2D(this);
        this.mEventListener = new JMIEventListener(this);
        this.mBrush = new JMIBrush();
        this.mBrushMarkMgr = new JMIBrushMarkMgr();
        this.mPaintMgr = new JMIPaintMgr(this);
        this.mScenarioMgr = new JMIScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mLogMgr.setPrintOn(true);

        // step 2 : build and show visual components.
        this.mFrame.add(this.mPalette2D);
        this.mFrame.setSize(900, 600);
        this.mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mFrame.setVisible(false);
        
        // step 3: conenct event listeners.
        this.mPalette2D.addMouseListener(this.mEventListener);
        this.mPalette2D.addMouseMotionListener(this.mEventListener);
        this.mPalette2D.setFocusable(true);
        this.mPalette2D.addKeyListener(this.mEventListener);
    }

    // methods
    public void setVisible(boolean show) {
        this.mFrame.setVisible(show);
    }
}
