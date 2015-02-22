package com.mobi.cq.servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.osgi.service.component.ComponentContext;

import javax.jcr.Node;
import javax.jcr.Property;
import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by m17218 on 22/02/15.
 */
public class MobiRequestProcessingServletTest <T extends SlingSafeMethodsServlet> {

    protected TestServlet servlet;

    @Mock
    protected SlingHttpServletRequest request;

    @Mock
    protected RequestParameter requestParameter;

    @Mock
    protected SlingHttpServletResponse response;

    @Mock
    protected Resource resource;

    @Mock
    protected ResourceResolver resourceResolver;

    @Mock
    protected PrintWriter printWriter;


    @Mock
    protected ComponentContext ctx;

    @Mock
    protected Dictionary<String, String> props;

    @Before
    public final void configureDependencies() throws Exception {
        initMocks(this);

        when(response.getWriter()).thenReturn(printWriter);

        when(request.getRequestParameter(anyString())).thenReturn(requestParameter);

        when(request.getResource()).thenReturn(resource);

        when(request.getResourceResolver()).thenReturn(resourceResolver);

        TestServlet servlet = createServlet();

        this.servlet = spy(servlet);

        servletSetup();
    }

    @After
    public final void tearDown() throws Exception {
        servletTearDown();
    }

    /**
     * Create and configure servlet.
     *
     * @return
     */
    protected  TestServlet createServlet() throws Exception
    {
        return new TestServlet();

    }

    protected  void servletTearDown(){

    }

    protected  void servletSetup() throws Exception
    {
        initMocks(this);
        when(ctx.getProperties()).thenReturn(props);
        when(request.getResource()).thenReturn(resource);
    }
    @Test
    public void testServletContentType() throws Exception
    {
        Node node = mock(Node.class);
        Property property = mock(Property.class);
        PrintWriter writer = mock(PrintWriter.class);
        String requestPath = "/content/medibank/retail.mobi";
        when(resource.getPath()).thenReturn(requestPath);
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(resourceResolver.getResource(requestPath)).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(node.getProperty(any(String.class))).thenReturn(property);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();

    }
}