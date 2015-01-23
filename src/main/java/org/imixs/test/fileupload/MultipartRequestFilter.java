package org.imixs.test.fileupload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * This is a simple request filter which wrapps the http request with the
 * MultipartRequestWrapper. The filter can be used to store uploaded files from
 * the jQuery FileUpload Plugin
 * 
 *
 * The servlet returns a json object containing the uploaded file information.
 * The returned json structure is described here:
 * https://github.com/blueimp/jQuery-File-Upload/wiki/JSON-Response
 *
 * JSON Response object
 * 
 * <code>
 *  
	 {
	    "files": [
	        {
	            "url": "0:0:0:0:0:0:0:1",
	            "thumbnail_url": "",
	            "name": "start.gif",
	            "type": "image/gif",
	            "size": 128,
	            "delete_url": "",
	            "delete_type": "DELETE"
	        }
	    ]
	}
 *  </code>
 * 
 * @author ralph.soika@imixs.com
 * @see https://blueimp.github.io/jQuery-File-Upload/
 * 
 */
@WebFilter(urlPatterns = "/fileupload/*")
public class MultipartRequestFilter implements Filter {
	private static final String REQUEST_METHOD_POST = "POST";
	private static final String CONTENT_TYPE_MULTIPART = "multipart/";

	private static Logger logger = Logger
			.getLogger(MultipartRequestFilter.class.getName());

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * test if content type is mulitpart and the request is a post request with
	 * a file content. Then wrap the request...
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		
		// check fileupload
		if (isNewFileUpload(httpRequest) || isCancelFileUpload(httpRequest)) {
			logger.fine("[MultipartRequestFilter] wrapping request...");
			request = new MultipartRequestWrapper(httpRequest);

			// now return json string of uploaded files....
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(((MultipartRequestWrapper) request).getJson());
			out.close();
			logger.fine("[MulitpartRequestFilter] request successfull");
			return;

		}
		
	
		
		
		// default doFilter...
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
	
	/**
	 * checks if the httpRequest is a fileupload 
	 * @param httpRequest
	 * @return
	 */
	private boolean isNewFileUpload(HttpServletRequest httpRequest ) {
		String sContentType = httpRequest.getContentType();
		logger.fine("[MulitpartRequestFilter]  contentType=" + sContentType);

		
		return (REQUEST_METHOD_POST.equalsIgnoreCase(httpRequest.getMethod())
		&& httpRequest.getContentType() != null
		&& sContentType.toLowerCase()
				.startsWith(CONTENT_TYPE_MULTIPART));
	}

	
	/**
	 * checks if the httpRequest is a fileupload cancel request... 
	 * @param httpRequest
	 * @return
	 */
	private boolean isCancelFileUpload(HttpServletRequest httpRequest ) {
		return ( httpRequest.getRequestURI()
				.indexOf("/fileupload/cancel/")>-1);
			
	}
}