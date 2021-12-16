package jmi;

import jmi.scenario.JMIDefaultScenario;
import jmi.scenario.JMIColorScenario;

import x.XScenarioMgr;

public class JMIScenarioMgr extends XScenarioMgr{
    public JMIScenarioMgr(JMIApp app) {
        super(app);
    }

    @Override
    protected void addScenarios() {
        this.mScenarios.add(JMIDefaultScenario.createSingleton(this.mApp));
        this.mScenarios.add(JMIColorScenario.createSingleton(this.mApp));
    }

    @Override
    protected void setInitCurScene() {
        this.setCurScene(JMIDefaultScenario.StandbyScene.getSingleton());
    }
}
