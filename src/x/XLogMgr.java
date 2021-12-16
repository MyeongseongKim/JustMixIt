package x;

import java.util.Vector;

public class XLogMgr {
    private Vector<String> mLogs = null;
    public Vector<String> getLogs() {
        return this.mLogs;
    }
    
    private boolean mPrintOn = false;
    public boolean IsPrintOn() {
        return this.mPrintOn;
    }
    public void setPrintOn(boolean isPrintOn) {
        this.mPrintOn = isPrintOn;
    }
    
    public XLogMgr() {
        this.mLogs = new Vector<String>();
    }
    
    public void addLog(String log) {
        this.mLogs.add(log);
        if (this.mPrintOn) {
            System.out.println(log);
        }
    }
}
