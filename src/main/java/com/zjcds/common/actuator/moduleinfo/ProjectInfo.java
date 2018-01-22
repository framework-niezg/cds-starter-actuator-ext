package com.zjcds.common.actuator.moduleinfo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * created date：2016-12-16
 * @author niezhegang
 */
@Getter
@Setter
public class ProjectInfo {
    /**工程名称*/
    private String projectName;
    /**工程版本*/
    private String projectVersion;
    /**工程额外的信息*/
    private Object info;
    /**工程模块信息*/
    private List<ModuleInfo> moduleInfos = new ArrayList<>();

    /**
     * 增加模块信息
     * @param moduleInfo
     */
    public ModuleInfo addModuleInfo(ModuleInfo moduleInfo) {
        if(moduleInfo != null)
            moduleInfos.add(moduleInfo);
        return moduleInfo;
    }

    public ModuleInfo addModuleInfo(String moduleName,String moduleVersion) {
        ModuleInfo moduleInfo = null;
        if(StringUtils.isNotBlank(moduleName)){
            moduleInfo = new ModuleInfo(moduleName,moduleVersion);
            moduleInfos.add(moduleInfo);
        }
        return moduleInfo;
    }

}
