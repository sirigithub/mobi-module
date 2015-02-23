package com.mobi.cq.function;

import com.day.cq.replication.PathNotFoundException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.HashMap;

public  class MobiResponseHelperFunction {

    private static String JCR_TITLE="jcr:title";
    private static String JCR_MODIFIED_DATE ="cq:lastModified";
    private static String PAGE_TITLE="pageTitle";
    private static String MODIFIED_DATE="lastModifiedDate";

    /**
     * Method returns a JSON Object which has the following response structure
     * Response{
     * data{
     *  pageTitle : Title
     *  lastModified: <date></date>
     *  pageContents {
     *      node1 : path,
     *      node2: path,
     *      node3: path
     *  }
     * }
     * }
     * @param resourceResolver
     * @param requestPath
     * @return
     */
    public  JSONObject getResponseObject(ResourceResolver resourceResolver, String requestPath) throws PathNotFoundException,RepositoryException,JSONException
    {
        if(resourceResolver.getResource(requestPath) ==null)
        {
            throw new ResourceNotFoundException(" The resource specified in path"+requestPath+" does not exist");
        }
        //get primary node
        Node node = resourceResolver.getResource(requestPath).adaptTo(Node.class);
        //Obtain page title and modified date
        String pageTitle = (node.hasProperty(JCR_TITLE)== false)?"":node.getProperty(JCR_TITLE).getString();
        String lastModifiedDate = (node.hasProperty(JCR_MODIFIED_DATE)== false)?"":node.getProperty(JCR_MODIFIED_DATE).getString();

        JSONObject pageProperties = new JSONObject();
        pageProperties.put(PAGE_TITLE,pageTitle);
        pageProperties.put(MODIFIED_DATE,lastModifiedDate);

        // get information on Child nodes :  name and path
        NodeIterator iterator = node.getNodes();
        if(iterator!=null)
        {
            HashMap childNodesInfo = new HashMap();
            while(iterator.hasNext())
            {
                Node childNode = iterator.nextNode();
                if(childNode!=null)
                {
                    childNodesInfo.put(childNode.getName(), childNode.getPath());
                }
            }
            JSONArray array = new JSONArray( );
            array.put(childNodesInfo);
            pageProperties.put("pageContents",array);
        }
        JSONObject responseJson = new JSONObject().put("Response",new JSONObject().put("data",pageProperties));

        return responseJson;


    }
}

