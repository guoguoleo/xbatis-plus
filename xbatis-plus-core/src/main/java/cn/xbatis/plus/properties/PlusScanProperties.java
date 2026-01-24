package cn.xbatis.plus.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "plus")
public class PlusScanProperties {

    /**
     * 逻辑删除开关
     */
    private Boolean logicDeleteSwitch = false;

    public void setLogicDeleteSwitch(Boolean logicDeleteSwitch) {
        this.logicDeleteSwitch = logicDeleteSwitch;
    }

    public Boolean getLogicDeleteSwitch() {
        return logicDeleteSwitch;
    }
}
