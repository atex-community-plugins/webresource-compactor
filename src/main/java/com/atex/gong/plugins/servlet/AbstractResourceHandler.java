package com.atex.gong.plugins.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * AbstractResourceHandler
 *
 * @author mnova
 */
public abstract class AbstractResourceHandler extends GenericServlet {

    private static final Logger LOGGER = Logger.getLogger(AbstractResourceHandler.class.getName());

    private HttpServlet httpServletHandler;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);

        try {
            final String classHandler = config.getInitParameter("classHandler");
            final Class servletClass = getClass().getClassLoader().loadClass(classHandler);

            LOGGER.info("loading " + servletClass);

            httpServletHandler = (HttpServlet) servletClass.newInstance();
            httpServletHandler.init(config);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        httpServletHandler.destroy();
        super.destroy();
    }

    @Override
    public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
        httpServletHandler.service(req, res);
    }

}
