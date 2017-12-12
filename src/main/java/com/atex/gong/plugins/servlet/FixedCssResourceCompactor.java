package com.atex.gong.plugins.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.atex.gong.plugins.strategies.FixedCSSProcessor;
import com.polopoly.cm.policy.PolicyCMServer;
import com.polopoly.cm.servlet.URLBuilder;
import com.polopoly.plugin.servlet.AbstractCssResourceCompactor;
import com.polopoly.plugin.strategies.ResourceProcessor;

/**
 * FixedCssResourceCompactor
 *
 * @author mnova
 */
public class FixedCssResourceCompactor extends AbstractCssResourceCompactor {

    @Override
    protected ResourceProcessor newInstanceProcessor(final PolicyCMServer cmServer,
                                                     final URLBuilder urlBuilder,
                                                     final HttpServletRequest req,
                                                     final long identifier,
                                                     final String cacheKey)
            throws IOException {

        return new FixedCSSProcessor(cmServer, urlBuilder, req);

    }
}
