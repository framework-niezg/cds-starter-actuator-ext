package com.zjcds.common.actuator;

import com.zjcds.common.actuator.moduleinfo.ProjectInfoEndpoint;
import com.zjcds.common.actuator.moduleinfo.ProjectInfoMvcEndpoint;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 扩展EndpointWebMvcManagementContextConfiguration
 * created date：2016-12-16
 * @author niezhegang
 */
@ManagementContextConfiguration
public class ExtEndpointWebMvcManagementContextConfiguration {


    @Bean
    @ConditionalOnBean(ProjectInfoEndpoint.class)
    @ConditionalOnEnabledEndpoint("projectinfo")
    public ProjectInfoMvcEndpoint projectInfoMvcEndpoint(ProjectInfoEndpoint delegate) {
        return new ProjectInfoMvcEndpoint(delegate);
    }

    @Bean
    @ConditionalOnMissingBean(ProjectInfoEndpoint.class)
    public ProjectInfoEndpoint projectInfoEndpoint(){
        return new ProjectInfoEndpoint();
    }

}
