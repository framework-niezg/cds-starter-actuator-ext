package com.zjcds.common.actuator.moduleinfo;

import com.zjcds.common.base.domain.BaseBean;
import lombok.Getter;
import lombok.Setter;

/**
 * 模块信息数据对象
 * created date：2016-12-16
 * @author niezhegang
 */
@Getter
@Setter
public class ModuleInfo extends BaseBean{
    /**模块名称*/
    private String moduleName;
    /**模块版本*/
    private String moduleVersion;
    /**模块定制暴露的信息*/
    private Object info;

    public ModuleInfo() {
    }

    public ModuleInfo(String moduleName,String moduleVersion) {
        this.moduleName = moduleName;
        this.moduleVersion = moduleVersion;
    }
}
