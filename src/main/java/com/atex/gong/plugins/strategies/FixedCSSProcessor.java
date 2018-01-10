package com.atex.gong.plugins.strategies;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.polopoly.cm.client.CMException;
import com.polopoly.cm.policy.PolicyCMServer;
import com.polopoly.cm.servlet.URLBuilder;
import com.polopoly.plugin.PluginWebResource;
import com.polopoly.plugin.strategies.DefaultCSSProcessor;
import com.polopoly.plugin.strategies.ResourceProcessor;

public class FixedCSSProcessor implements ResourceProcessor {
    private PolicyCMServer cmServer;
    private HttpServletRequest req;
    private URLBuilder urlBuilder;
    private Pattern URL_RE = Pattern.compile("url\\((?!\\s*[\\'\\\"]?(?:https?:|data:application)?/)\\s*([\\'\\\"])?", Pattern.CASE_INSENSITIVE);

    private static final Logger LOG = Logger.getLogger(DefaultCSSProcessor.class.getName());

    public FixedCSSProcessor(PolicyCMServer cmServer, URLBuilder urlBuilder, HttpServletRequest req) {
        this.cmServer = cmServer;
        this.urlBuilder = urlBuilder;
        this.req = req;
    }

    @Override
    public List<String> process(List<PluginWebResource> resources) {
        List<String> allCss = new ArrayList<String>();
        for (PluginWebResource resource : resources) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                cmServer.getContent(resource.getContentId()).exportFile(resource.getIdentifier(), outputStream);
                String css = new String(outputStream.toByteArray(), "UTF-8");
                css = process(css, resource);
                allCss.add(css);
            } catch (CMException e) {
                LOG.log(Level.WARNING, "Error getting resource: " + resource.getIdentifier(), e.getMessage());
            } catch (IOException e) {
                LOG.log(Level.WARNING, "Error exporting resource as file: " + resource.getIdentifier(), e.getMessage());
            }
        }
        return allCss;
    }

    private String process(String code, PluginWebResource resource) {
        String baseUrl = "";
        try {
            baseUrl = urlBuilder.createFileUrl(resource.getContentId(), getBasePath(resource.getIdentifier()), req);
        } catch (CMException e) {
            LOG.log(Level.WARNING, "Unable to replace relative URLs in css: " + resource.getIdentifier(), e);
        }
        code = replaceRelativeUrls(code, baseUrl);
        code = prependIdentifier(code, resource);
        return code;
    }

    private String getBasePath(String cssFilePath) {
        int basePathIndex = cssFilePath.lastIndexOf("/");
        if (basePathIndex == -1) {
            return "";
        }
        return cssFilePath.substring(0, basePathIndex);
    }

    String replaceRelativeUrls(String css, String baseUrl) {
        if (baseUrl != null && !baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return URL_RE.matcher(css).replaceAll("url($1" + baseUrl);
    }

    private String prependIdentifier(String css, PluginWebResource resource) {
        css = String.format("%n/*! Resource contributed from: %s %s*/%n", resource.getContentId().getContentIdString(), resource.getIdentifier()) + css;
        return css;
    }
}
