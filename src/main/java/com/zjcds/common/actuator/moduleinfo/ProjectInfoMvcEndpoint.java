package com.zjcds.common.actuator.moduleinfo;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;

/**
 * 归集工程信息的的Endpoint
 * created date：2016-12-16
 * @author niezhegang
 */
public class ProjectInfoMvcEndpoint extends EndpointMvcAdapter {

    public ProjectInfoMvcEndpoint(Endpoint<?> delegate) {
        super(delegate);
    }


}
