package com.mobi.cq.servlet;

import com.day.cq.commons.Filter;
import com.day.cq.replication.PathNotFoundException;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.medibank.cq.commons.Constants;
import com.medibank.digital.cq.dao.DAOException;
import com.medibank.digital.cq.el.functions.MobiResponseHelperFunction;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;

import javax.jcr.*;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.String.format;

/**
 * SlingServlet to handle requests to url's with a mobi extension.It calls a helper function to parse the request path and generate a response.
 * The MIME type of the response is set to mobi to cater to the custom rendering.
 */

@SlingServlet(resourceTypes = {"sling/servlet/default"}, methods = {"GET"}, extensions = {"mobi"},metatype = true)
@Properties({
        @Property(name = "service.pid", value = "com.medibank.digital.cq.servlet.MobiRequestProcessingServlet", propertyPrivate = false),
        @Property(name = "service.description", value = "Servlet to process requests with a .mobi extension", propertyPrivate = false),
        @Property(name = "service.vendor", value = "MobiServlet", propertyPrivate = false)})

public class MobiRequestProcessingServlet extends  SlingSafeMethodsServlet  {

    private static final Logger log = LoggerFactory.getLogger(MobiRequestProcessingServlet.class);

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) throws ServletException ,IOException {
        try{
            ResourceResolver resourceResolver = request.getResourceResolver();
            // instantiate a helper function to retreive details of the path from JCR
            MobiResponseHelperFunction helperFunction = new  MobiResponseHelperFunction();
            JSONObject jcrDataFromURL = helperFunction.getResponseObject(resourceResolver,request.getResource().getPath());

            response.setHeader("Header","Mobi Response");
            response.setContentType("application/mobi");
            PrintWriter out = response.getWriter();
            out.write(jcrDataFromURL.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new ServletException(
                    "[Exception fetching path information]",
                    e);
        }

    }
}
