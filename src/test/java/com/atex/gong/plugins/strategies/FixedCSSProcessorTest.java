package com.atex.gong.plugins.strategies;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.polopoly.cm.policy.PolicyCMServer;
import com.polopoly.cm.servlet.URLBuilder;

/**
 * FixedCSSProcessorTest
 *
 * @author mnova
 */
@RunWith(MockitoJUnitRunner.class)
public class FixedCSSProcessorTest {

    @Mock
    PolicyCMServer policyCMServer;

    @Mock
    URLBuilder urlBuilder;

    @Mock
    HttpServletRequest request;

    private FixedCSSProcessor processor;

    @Before
    public void before() {
        processor = new FixedCSSProcessor(policyCMServer, urlBuilder, request);
    }

    @Test
    public void testReplaceRelativeUrls() {
        Assert.assertEquals("url(/pippo/pluto)", replace("url(pluto)"));
        Assert.assertEquals("url('/pippo/pluto')", replace("url('pluto')"));
        Assert.assertEquals("url(\"/pippo/pluto\")", replace("url(\"pluto\")"));
        Assert.assertEquals("url(/pippo/pluto/paperino )", replace("url( pluto/paperino )"));
        Assert.assertEquals("url(http://pluto)", replace("url(http://pluto)"));
        Assert.assertEquals("url(https://pluto)", replace("url(https://pluto)"));
        Assert.assertEquals("url('http://pluto')", replace("url('http://pluto')"));
        Assert.assertEquals("url(\"https://pluto\")", replace("url(\"https://pluto\")"));
        Assert.assertEquals("url(/pluto)", replace("url(/pluto)"));
        Assert.assertEquals("url('/pluto')", replace("url('/pluto')"));
        Assert.assertEquals("url(data:application/font-woff;charset=utf-8;base64,)", replace("url(data:application/font-woff;charset=utf-8;base64,)"));
        Assert.assertEquals("url('/pippo/../fonts/fontawesome-webfont.eot?v=4.1.0')", replace("url('../fonts/fontawesome-webfont.eot?v=4.1.0')"));
    }

    @Test
    public void testReplaceRelativeUrlsWithoutFinalSlash() {
        Assert.assertEquals("url(/pippo/pluto)", replaceN("url(pluto)"));
        Assert.assertEquals("url('/pippo/pluto')", replaceN("url('pluto')"));
        Assert.assertEquals("url(\"/pippo/pluto\")", replaceN("url(\"pluto\")"));
        Assert.assertEquals("url(/pippo/pluto/paperino )", replaceN("url( pluto/paperino )"));
        Assert.assertEquals("url(http://pluto)", replaceN("url(http://pluto)"));
        Assert.assertEquals("url(https://pluto)", replaceN("url(https://pluto)"));
        Assert.assertEquals("url('http://pluto')", replaceN("url('http://pluto')"));
        Assert.assertEquals("url(\"https://pluto\")", replaceN("url(\"https://pluto\")"));
        Assert.assertEquals("url(/pluto)", replaceN("url(/pluto)"));
        Assert.assertEquals("url('/pluto')", replaceN("url('/pluto')"));
        Assert.assertEquals("url(data:application/font-woff;charset=utf-8;base64,)", replaceN("url(data:application/font-woff;charset=utf-8;base64,)"));
        Assert.assertEquals("url('/pippo/../fonts/fontawesome-webfont.eot?v=4.1.0')", replaceN("url('../fonts/fontawesome-webfont.eot?v=4.1.0')"));
    }

    private String replace(final String url) {
        return processor.replaceRelativeUrls(url, "/pippo/");
    }

    private String replaceN(final String url) {
        return processor.replaceRelativeUrls(url, "/pippo");
    }

}