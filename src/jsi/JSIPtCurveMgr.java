package jsi;

import java.util.ArrayList;


public class JSIPtCurveMgr {
    private JSIPtCurve mCurPtCurve = null;
    public JSIPtCurve getCurPtCurve() {
        return this.mCurPtCurve;
    }
    public void setCurPtCurve(JSIPtCurve ptCurve) {
        this.mCurPtCurve = ptCurve;
    }
    
    private ArrayList<JSIPtCurve> mPtCurves = null;
    public ArrayList<JSIPtCurve> getPtCurves() {
        return mPtCurves;
    }
    
    private ArrayList<JSIPtCurve> mSelectedPtCurves = null;
    public ArrayList<JSIPtCurve> getSelectedPtCurves() {
        return this.mSelectedPtCurves;
    }
    
    // constructor
    public JSIPtCurveMgr() {
        this.mPtCurves = new ArrayList<JSIPtCurve>();
        this.mSelectedPtCurves = new ArrayList<JSIPtCurve>();
    }  
}
