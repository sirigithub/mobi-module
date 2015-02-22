package com.mobi.cq.function;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.jcr.Node;
import javax.jcr.Property;
import java.io.PrintWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by m17218 on 22/02/15.
 */
public class MobiResponseHelperFunctionTest {
    @Mock
    private Resource resource;

    @Mock
    private ResourceResolver resourceResolver;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testJSONObjectNotNull()  throws Exception
    {
        Node node = mock(Node.class);
        Property property = mock(Property.class);
        PrintWriter writer = mock(PrintWriter.class);
        String requestPath = "/content/anz/homepage.mobi";

        when(resource.getPath()).thenReturn(requestPath);
        when(resourceResolver.getResource(requestPath)).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(node.getProperty(any(String.class))).thenReturn(property);
        MobiResponseHelperFunction helperFunction = new MobiResponseHelperFunction();
        JSONObject returnObject = helperFunction.getResponseObject(resourceResolver, requestPath);
        assertNotNull(returnObject);

    }

    @Test
    public void testJSONObjectContainsPageName()  throws Exception
    {
        Node node = mock(Node.class);
        Property titleProperty = mock(Property.class);
        Property dateProperty = mock(Property.class);

        PrintWriter writer = mock(PrintWriter.class);
        String requestPath = "/content/medibank/retail/health-insurance.mobi";
        String pageTitle ="pageTitle";

        when(resource.getPath()).thenReturn(requestPath);
        when(resourceResolver.getResource(requestPath)).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);

        when(node.getProperty("jcr:title")).thenReturn(titleProperty);
        when(titleProperty.toString()).thenReturn("Health Insurance");

        when(node.getProperty("jcr:title")).thenReturn(dateProperty);
        when(dateProperty.toString()).thenReturn("2015-02-22 00:00:000");

        MobiResponseHelperFunction helperFunction = new MobiResponseHelperFunction();
        JSONObject returnObject = helperFunction.getResponseObject(resourceResolver, requestPath);
        boolean containsTitle = returnObject.toString().contains(pageTitle);
        assertTrue(containsTitle);

    }

}