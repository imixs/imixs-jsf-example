package org.imixs.test.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

/**
 * This Class wrapps a HTTP Servlet request. This is used to extract an uploaded
 * file from the parts of a multipart/form-data.
 * 
 * The class was developed initially by theironicprogrammer@gmail.com
 * 
 * @author ralph.soika@imixs.com
 */
public class MultipartRequestWrapper extends HttpServletRequestWrapper {
	private static final String CONTENT_DISPOSITION = "content-disposition";
	private static final String CONTENT_DISPOSITION_FILENAME = "filename";

	private static Logger logger = Logger
			.getLogger(MultipartRequestWrapper.class.getName());

	private Hashtable<String, String[]> params = new Hashtable<String, String[]>();

	private List<MultiFileData> fileDataList = new ArrayList<MultiFileData>();

	// @Inject
	// MultiFileController multiFileController;

	/**
	 * The constructor wrap the http servlet request and puts all params
	 * contained by the request into a hashmap (params)
	 * 
	 * @param request
	 */
	public MultipartRequestWrapper(HttpServletRequest request) {
		super(request);
		logger.fine("Created multipart wrapper....");
		try {
			logger.fine("Looping parts");
			for (Part p : request.getParts()) {
				byte[] b = new byte[(int) p.getSize()];
				p.getInputStream().read(b);
				p.getInputStream().close();
				params.put(p.getName(), new String[] { new String(b) });

				// test if part contains a file
				String fileName = getFilename(p);
				if (fileName != null) {
					// extract the file content...
					MultiFileData fileData = null;
					logger.fine("Filename : " + fileName + ", contentType "
							+ p.getContentType());
					fileData = new MultiFileData(fileName, p.getContentType(),
							b);
					if (fileData != null) {
						fileDataList.add(fileData);

						// MultiFileController multiFileController =
						// (MultiFileController) request
						// .getSession().getAttribute("multiFileController");
						
						

					}

				}
			}
			
			request.getSession().setAttribute("imixsFileDataList", fileDataList);
			
			
		} catch (IOException ex) {
			Logger.getLogger(MultipartRequestWrapper.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ServletException ex) {
			Logger.getLogger(MultipartRequestWrapper.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	public List<MultiFileData> getFileDataList() {
		return fileDataList;
	}

	/**
	 * test and extracts the filename of a http request part. The method returns
	 * null if the part dose not contain a file
	 * 
	 * @param part
	 * @return - filename or null if not a file content
	 */
	private String getFilename(Part part) {
		for (String cd : part.getHeader(CONTENT_DISPOSITION).split(";")) {
			if (cd.trim().startsWith(CONTENT_DISPOSITION_FILENAME)) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	@Override
	public String getParameter(String name) {

		String[] values = getParameterValues(name);
		if (values == null || values.length == 0) {
			return null;
		}

		return values[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return params;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return params.keys();
	}

	@Override
	public String[] getParameterValues(String name) {
		return params.get(name);
	}

}
