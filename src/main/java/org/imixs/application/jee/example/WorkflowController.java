/*******************************************************************************
 *  Imixs Workflow Technology
 *  Copyright (C) 2003, 2008 Imixs Software Solutions GmbH,  
 *  http://www.imixs.com
 *  
 *  This program is free software; you can redistribute it and/or 
 *  modify it under the terms of the GNU General Public License 
 *  as published by the Free Software Foundation; either version 2 
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 *  General Public License for more details.
 *  
 *  You can receive a copy of the GNU General Public
 *  License at http://www.gnu.org/licenses/gpl.html
 *  
 *  Contributors:  
 *  	Imixs Software Solutions GmbH - initial API and implementation
 *  	Ralph Soika
 *  
 *******************************************************************************/
package org.imixs.application.jee.example;

import java.io.Serializable;

import javax.inject.Inject;

import org.imixs.workflow.ItemCollection;
import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.exceptions.PluginException;
import org.imixs.workflow.exceptions.ProcessingErrorException;
import org.imixs.workflow.jee.ejb.EntityService;
import org.imixs.workflow.jee.faces.fileupload.FileUploadController;

/**
 * This WorkflowControler extends the basic WorkflowControler and implements an
 * additional fileUpload feature to handle file uploads. The controller
 * implements a lazy loading mechanism to handle attachments.
 * 
 * 
 * @author rsoika
 */
@javax.inject.Named("workflowController")
@javax.enterprise.context.SessionScoped
public class WorkflowController extends
		org.imixs.workflow.jee.faces.workitem.WorkflowController implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FileUploadController fileUploadController;

	public void setFileUploadController(FileUploadController fleUploadController) {
		this.fileUploadController = fleUploadController;
	}

	@Override
	public String process() throws AccessDeniedException,
			ProcessingErrorException, PluginException {
		
		
		// update team members
		String id=this.getWorkitem().getItemValueString("Team");
		// lookup the team entity...
		if (!"".equals(id)) {
			ItemCollection team=this.getEntityService().load(id);
			if (team!=null)
				this.getWorkitem().replaceItemValue("namTeam", team.getItemValue("Members"));
		}
		
		
/*
	
		if (fileUploadController.isDirty()) {
			// test if workItem has the property '$BlobWorkitem'
			if (!this.getWorkitem().hasItem("$BlobWorkitem")) {
				// create a blob workItem
				ItemCollection blobWorkitem = this.loadBlobWorkitem(this
						.getWorkitem());
				// store the $BlobWorkitem
				getWorkitem()
						.replaceItemValue(
								"$BlobWorkitem",
								blobWorkitem
										.getItemValueString(EntityService.UNIQUEID));
				// save the blob workItem (which is still empty)
				this.saveBlobWorkitem(blobWorkitem, this.getWorkitem());
			}
			// update the file info for the current workitem
			fileUploadController.updateWorkitem(this.getWorkitem(), true);
		}
		
		*/
		String result = super.process();

	/*	
		if (fileUploadController.isDirty()) {
			// ...save the blobWorkitem after processing the parent!!
			ItemCollection blobWorkitem = this.loadBlobWorkitem(getWorkitem());
			if (blobWorkitem != null) {
				fileUploadController.updateWorkitem(blobWorkitem, false);
				this.saveBlobWorkitem(blobWorkitem, getWorkitem());
			}
		}

		// update the fileuploadController
		fileUploadController.doClear(null);
		fileUploadController.setAttachedFiles(getWorkitem().getFileNames());
		*/
		return result;
	}

	@Override
	public void setWorkitem(ItemCollection aworkitem) {

		super.setWorkitem(aworkitem);
		
		/*
		fileUploadController
				.setRestServiceURI("/workflow/rest/workflow/workitem/");

		fileUploadController.doClear(null);

		if (aworkitem != null) {
			fileUploadController.setWorkitemID(aworkitem
					.getItemValueString("$BlobWorkitem"));
			fileUploadController.setAttachedFiles(getWorkitem().getFileNames());
		}
		*/
	}

}
