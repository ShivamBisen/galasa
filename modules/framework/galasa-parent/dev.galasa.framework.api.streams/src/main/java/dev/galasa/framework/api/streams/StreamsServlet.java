/*
 * Copyright contributors to the Galasa project
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.framework.api.streams;

import dev.galasa.framework.api.common.BaseServlet;
import dev.galasa.framework.api.common.Environment;
import dev.galasa.framework.api.common.SystemEnvironment;
import dev.galasa.framework.api.streams.internal.routes.StreamsRoute;
import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.framework.spi.IConfigurationPropertyStoreService;
import dev.galasa.framework.spi.IFramework;
import dev.galasa.framework.spi.rbac.RBACException;
import dev.galasa.framework.spi.rbac.RBACService;
import dev.galasa.framework.spi.streams.IStreamsService;
import dev.galasa.framework.spi.streams.StreamsException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;


@Component(service = Servlet.class, scope = ServiceScope.PROTOTYPE, property = {
    "osgi.http.whiteboard.servlet.pattern=/streams/*" }, name = "Galasa Streams microservice")
public class StreamsServlet extends BaseServlet {

    @Reference
    protected IFramework framework;

    @Reference
    protected IConfigurationPropertyStoreService configurationPropertyStoreService;

    public StreamsServlet() {
        this(new SystemEnvironment());
    }

    private static final long serialVersionUID = 1L;

    private Log logger = LogFactory.getLog(getClass());

    public StreamsServlet(Environment env) {
        super(env);
    }

    @Override
    public void init() throws ServletException {
        logger.info("Galasa Streams API initialising");

        RBACService rbacService;
        IStreamsService streamsService;

        try {
            rbacService = framework.getRBACService();
            streamsService = framework.getStreamsService();
            configurationPropertyStoreService = framework.getConfigurationPropertyService(getServletInfo());
        } catch ( RBACException | ConfigurationPropertyStoreException ex) {
            throw new ServletException(ex);
        }
        
        try {
            addRoute(new StreamsRoute(getResponseBuilder(), env, streamsService, rbacService, configurationPropertyStoreService));
        } catch (StreamsException e) {
            throw new ServletException(e);
        }

        logger.info("Galasa Streams API initialised");
    }
    
}
