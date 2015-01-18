package org.imixs.test.fileupload;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.imixs.workflow.ItemCollection;


/**
 * 
 * @author rsoika
 * 
 */
@Named
@SessionScoped
public class MultiFileController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(MultiFileController.class
			.getName());

	public MultiFileController() {
		super();
	}
	
	public void addFile (MultiFileData filedata) {
		
		if (filedata!=null) {
			
		}
	}
	

	public void updateWorkitem(ItemCollection workitem) {
		
		
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		
		List<MultiFileData> files=	(List<MultiFileData>) req.getSession().getAttribute("imixsFileDataList");
		
			for (MultiFileData fileData: files) {
		
			
				logger.fine("FileName: " + fileData.getName());
				logger.fine("ContentType: " + fileData.getContentType());
				logger.fine("Size: " + fileData.getSize());

			
				
			}
		}
	
}
