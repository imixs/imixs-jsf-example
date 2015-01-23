package org.imixs.test.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	public static final String IMIXS_FILEDATA_LIST = "IMIXS_FILEDATA_LIST";

	private static Logger logger = Logger
			.getLogger(MultipartRequestWrapper.class.getName());

	private Hashtable<String, String[]> params = new Hashtable<String, String[]>();

	private List<MultiFileData> fileDataList = null;

	/**
	 * The constructor wrap the http servlet request and puts all params
	 * contained by the request into a hashmap (params).
	 * 
	 * Uploaded files are stored in the fileDataList.
	 * 
	 * 
	 * The method getJson returns the json structure for the uploaded files.
	 * 
	 * 
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public MultipartRequestWrapper(HttpServletRequest request) {
		super(request);
		try {

			fileDataList = (List<MultiFileData>) request.getSession()
					.getAttribute(IMIXS_FILEDATA_LIST);
			if (fileDataList == null) {
				fileDataList = new ArrayList<MultiFileData>();
			}

			// check cancel fileupload
			int iCancel = request.getRequestURI()
					.indexOf("/fileupload/cancel/");
			if (iCancel > -1) {
				String filename = request.getRequestURI().substring(
						iCancel + 19);

				removeFile(filename);

			} else {

				logger.fine("[MultipartRequestWrapper] Looping parts");
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
						fileData = new MultiFileData(fileName,
								p.getContentType(), b);
						if (fileData != null) {
							// remove existing file
							removeFile(fileData.getName());
							// update filedataList...
							fileDataList.add(fileData);
						}

					}
				}
			}
			request.getSession()
					.setAttribute(IMIXS_FILEDATA_LIST, fileDataList);

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

	/**
	 * returns a json structure for uploaded files.
	 * 
	 * @see https://github.com/blueimp/jQuery-File-Upload/wiki/JSON-Response
	 * 
	 *      <code>
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
	 * @return
	 */
	public String getJson() {

		String result = "{ \"files\":[";
		for (int i = 0; i < fileDataList.size(); i++) {

			MultiFileData fileData = fileDataList.get(i);

			result += "{ \"url\": \"" + this.getRequest().getRemoteAddr()
					+ "\",";
			result += "\"thumbnail_url\": \"\",";
			result += "\"name\": \"" + fileData.getName() + "\",";
			result += "\"type\": \"" + fileData.getContentType() + "\",";
			result += "\"size\": " + fileData.getSize() + ",";
			result += "\"delete_url\": \"\",";
			result += "\"delete_type\": \"DELETE\"";

			// last element?
			if (i < fileDataList.size() - 1)
				result += "},";
			else
				result += "}";
		}

		result += "]}";

		return result;
	}

	/**
	 * removes an uploaded file fromo the fileDataList...
	 * 
	 * @param file
	 *            - filename to be removed
	 */
	public void removeFile(String file) {

		int pos = -1;
		if (file == null)
			return;

		for (int i = 0; i < fileDataList.size(); i++) {

			MultiFileData fileData = fileDataList.get(i);
			if (file.equals(fileData.getName())) {
				pos = i;
				break;
			}

		}

		// found?
		if (pos > -1) {
			logger.fine("[MultipartRequestWrapper] remove file '" + file + "'");
			fileDataList.remove(pos);
		}
	}

}
