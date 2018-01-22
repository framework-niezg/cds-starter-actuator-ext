package com.zjcds.common.actuator.moduleinfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;


import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 工程信息加载类
 * created date：2016-12-18
 * @author niezhegang
 */
public abstract class ProjectInfoLoader {

    private static Logger logger = LoggerFactory.getLogger(ProjectInfoLoader.class);

    /**工程信息文件所在位置*/
    public static final String Project_Info_Resource = "META-INF/projectinfo.properties";

    public static final String Module_Class_Key = "moduleInfoClass";

    public static final String Project_Class_Key = "projectInfoClass";

    /**
     * Gets all module info class.
     * @return the all module info class
     */
    public static List<Class<?>> getAllModuleInfoClass() {
        return getClassNameByKey(Module_Class_Key);
    }

    /**
     * Gets project info class.
     * @return the project info class
     */
    public static Class<?> getProjectInfoClass() {
        List<Class<?>> projectInfos = getClassNameByKey(Project_Class_Key);
        //返回第一个
        if(CollectionUtils.isNotEmpty(projectInfos))
            return projectInfos.get(0);
        else
            return null;
    }

    private static List<Class<?>> getClassNameByKey(String key) {
        ClassLoader classLoader = ProjectInfoEndpoint.class.getClassLoader();
        List<Class<?>> result = new ArrayList<>();
        try {
            Enumeration<URL> urls = (classLoader != null ? classLoader.getResources(Project_Info_Resource) :
                    ClassLoader.getSystemResources(Project_Info_Resource));
            if(urls != null) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
                    String className = properties.getProperty(key);
                    try {
                        if(StringUtils.isNotBlank(className)) {
                            result.add(ClassUtils.forName(className, classLoader));
                        }
                    } catch (ClassNotFoundException e) {
                        logger.warn("工程版本信息的类{}没有找到，将不会获取该类所在工程的信息！",className);
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("获取工程信息出现问题，不会影响系统正常运行！",e);
        }
        return result;
    }
}
