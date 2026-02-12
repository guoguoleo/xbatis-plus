package cn.xbatis.plus;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "plus")
public class GlobalPlusProperties {

    private boolean logicDeleteSwitch = false;

    public void setLogicDeleteSwitch(boolean logicDeleteSwitch) {
        this.logicDeleteSwitch = logicDeleteSwitch;
    }

    public boolean getLogicDeleteSwitch() {
        return logicDeleteSwitch;
    }

}
