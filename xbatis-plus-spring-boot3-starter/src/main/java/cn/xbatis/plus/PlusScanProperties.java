package cn.xbatis.plus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "plus")
public class PlusScanProperties {

    private boolean logicDeleteSwitch = false;

    public void setLogicDeleteSwitch(boolean logicDeleteSwitch) {
        this.logicDeleteSwitch = logicDeleteSwitch;
    }

    public boolean getLogicDeleteSwitch() {
        return logicDeleteSwitch;
    }

}
