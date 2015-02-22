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
 * Created by Sirisha on 22/02/15.
 */

@SlingServlet(resourceTypes = {"sling/servlet/default"}, methods = {"GET"}, extensions = {"mobi"},metatype = true)
@Properties({
        @Property(name = "service.pid", value = "com.medibank.digital.cq.servlet.TestServlet", propertyPrivate = false),
        @Property(name = "service.description", value = "Test Servlet", propertyPrivate = false),
        @Property(name = "service.vendor", value = "Medibank", propertyPrivate = false)})

public class MobiRequestProcessingServlet extends  SlingSafeMethodsServlet  {

    private static final Logger log = LoggerFactory.getLogger(TestServlet.class);
    public static final String STATEMENT = "statement";

    /** Query type */
    public static final String QUERY_TYPE = "queryType";

    /** Result set offset */
    public static final String OFFSET = "offset";

    /** Number of rows requested */
    public static final String ROWS = "rows";

    /** property to append to the result */
    public static final String PROPERTY = "property";
    public static final String EXCERPT_PATH = "excerptPath";

    /** rep:exerpt */
    private static final String REP_EXCERPT = "rep:excerpt()";

    public static final String TIDY = "tidy";



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
            response.setContentType("application/json");
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
