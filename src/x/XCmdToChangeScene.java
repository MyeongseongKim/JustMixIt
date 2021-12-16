package x;

public class XCmdToChangeScene extends XLoggableCmd {
    private XScene mFromScene = null;
    private XScene mToScene = null;
    private XScene mReturnScene = null;
    
    private XCmdToChangeScene(XApp app, XScene toScene, XScene returnScene) {
        super(app);
        this.mFromScene = app.getScenarioMgr().getCurScene();
        this.mToScene = toScene;
        this.mReturnScene = returnScene;
    }
    
    public static boolean execute(XApp app, XScene toScene, XScene returnScene) {
        XCmdToChangeScene cmd = new XCmdToChangeScene(app, toScene, returnScene);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        this.mToScene.setReturnScene(this.mReturnScene);
        this.mApp.getScenarioMgr().setCurScene(this.mToScene);
        return true;
    }
    
    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName() + "\t");
        sb.append(this.mFromScene.getClass().getSimpleName() + "\t");
        XScene curScene = this.mApp.getScenarioMgr().getCurScene();
        sb.append(curScene.getClass().getSimpleName() + "\t");
        if (this.mReturnScene == null) {
            sb.append("null");
        }
        else {
            sb.append(curScene.getReturnScene().getClass().getSimpleName());
        }
        return sb.toString();
    }
}
