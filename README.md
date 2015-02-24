
Solution Description
----------------------

This project creates a module containing a sling servlet to handle custom url extensions, in this case .mobi.

com.mobi.cq.servlet.MobiRequestProcessingServlet
-----------------------------------------------

The servlet extends SlingSafeMethodsServlet and handles GET requests. As the servlet is configured to listen to requests
on resourceType sling/servlet/default, the servlet will be invoked for any requests with a .mobi extension.
The servlet also sets a MIME type in the response to mobi to cater to a scenario where we are handling a custom response type.

com.mobi.cq.function.MobiResponseHelperFunction
-----------------------------------------------
This class is a helper function which queries the JCR with the given request path, retrives page information and 
formats the reponse as Response{
                                 pageContent{
                                   title:
                                   modifiedDate:
                                   data{
                                        //child node information
                                   }
                                   
                                   
                                 }
                              }
                              
 The code can be bundled as an osgi module and installed in a CQ5 environment. Once the servlet is registered , it will be invoked for any url such as localhost:4502/conntent/*.mobi
 
 
                              
                              


