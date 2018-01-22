package com.zjcds.common.actuator.moduleinfo;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ModuleInfoEndpoint的代理类
 * created date：2016-12-16
 * @author niezhegang
 */
public class ProjectInfoEndpoint extends AbstractEndpoint<ProjectInfo> {
    /**logger*/
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**工程信息*/
    private volatile ProjectInfo projectInfo;

    private final static String Info_Object_Method_Name = "getInfo";

    public ProjectInfoEndpoint(){
        super("projectinfo");
    }

    @Override
    public ProjectInfo invoke() {
        ProjectInfo ret;
        if(projectInfo != null){
            ret =  projectInfo;
        }
        else {
            synchronized (this) {
               if(projectInfo != null)  {
                   ret = projectInfo;
               }
               else {
                   ret = collectProjectInfo();
                   projectInfo = ret;
               }
            }
        }
        return ret;
    }

    private ProjectInfo collectProjectInfo(){
        ProjectInfo projectInfo = new ProjectInfo();
        Class<?> projectInfoClass = ProjectInfoLoader.getProjectInfoClass();
        Package pkg = getPackage(projectInfoClass);
        //填充工程信息
        if(pkg != null){
            projectInfo.setProjectName(pkg.getImplementationTitle());
            projectInfo.setProjectVersion(pkg.getImplementationVersion());
            projectInfo.setInfo(getInfoObject(projectInfoClass));
        }
        //填充模块信息
        List<Class<?>> moduleInfoClasses = ProjectInfoLoader.getAllModuleInfoClass();
        ModuleInfo moduleInfo = null;
        if(CollectionUtils.isNotEmpty(moduleInfoClasses)) {
            for(Class<?> moduleInfoClass : moduleInfoClasses) {
                pkg = getPackage(moduleInfoClass);
                if(pkg != null) {
                    moduleInfo = projectInfo.addModuleInfo(pkg.getImplementationTitle(),pkg.getImplementationVersion());
                    if(moduleInfo != null){
                        moduleInfo.setInfo(getInfoObject(moduleInfoClass));
                    }
                }
            }
        }
        return projectInfo;
    }

    /**
     * 从类对象中获取工程或模块提供的额外信息对象
     * 1、类有一个默认构造方法
     * 2、类有一个无参getInfo()方法
     * @param clazz
     */
    private Object getInfoObject(Class<?> clazz){
        Object info = null;
        try {
            if(clazz != null ) {
                Method method = ReflectionUtils.findMethod(clazz,Info_Object_Method_Name);
                if(method != null) {
                    Object obj = clazz.newInstance();
                    info = ReflectionUtils.invokeMethod(method,obj);
                }
                else {
                    logger.info("未定义{}模块getInfo方法，将不能获取额外信息",clazz.getName());
                }
            }
        } catch (Exception e) {
            logger.warn("获取模块{}的额外信息失败！",clazz.getName());
        }
        return info;
    }

    private Package getPackage(Class clazz) {
        Package pkg = null;
        if(clazz != null)
            pkg = clazz.getPackage();
        return pkg;
    }
}
