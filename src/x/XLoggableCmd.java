package x;

public abstract class XLoggableCmd implements XExecutable {
    protected XApp mApp = null;
    
    protected XLoggableCmd(XApp app) {
        this.mApp = app;
    }
    
    @Override
    public boolean execute() {
        if (this.defineCmd()) {
            this.mApp.getLogMgr().addLog(this.createLog());
            return true;
        }
        else {
            return false;
        }
    }
    
    protected abstract boolean defineCmd();
    protected abstract String createLog();
}
