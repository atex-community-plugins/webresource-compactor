package com.polopoly.plugin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.polopoly.cm.policy.PolicyCMServer;
import com.polopoly.cm.servlet.RequestPreparator;
import com.polopoly.cm.servlet.URLBuilder;
import com.polopoly.plugin.PluginWebResource;
import com.polopoly.plugin.strategies.DefaultResourceConcatenator;
import com.polopoly.plugin.strategies.ResourceProcessor;

/**
 * CustomCssResourceCompactor
 *
 * @author mnova
 */
public abstract class AbstractCssResourceCompactor extends CssResourceCompactor {

    @Override
    protected synchronized String getCompiledSource(HttpServletRequest req, long identifier, String cacheKey) throws
                                                                                                              IOException {
        SourceContainer cachedResult = this.cache.get(cacheKey);
        if (cachedResult != null && cachedResult.getVersion() == identifier) {
            return cachedResult.getCode();
        } else {
            List<PluginWebResource> resources = this.getPluginResources(req);
            PolicyCMServer cmServer = this.getCMServer(req);
            URLBuilder urlBuilder = RequestPreparator.getURLBuilder(req);
            ResourceProcessor processor = newInstanceProcessor(cmServer, urlBuilder, req, identifier, cacheKey);
            List<String> processedResources = processor.process(resources);
            return (new DefaultResourceConcatenator()).concatenate(processedResources);
        }
    }

    protected abstract ResourceProcessor newInstanceProcessor(
            final PolicyCMServer cmServer,
            final URLBuilder urlBuilder,
            final HttpServletRequest req,
            final long identifier,
            final String cacheKey) throws IOException;

}
