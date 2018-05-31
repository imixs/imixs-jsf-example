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

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.imixs.workflow.ItemCollection;
import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.exceptions.ModelException;
import org.imixs.workflow.exceptions.PluginException;
import org.imixs.workflow.exceptions.ProcessingErrorException;

/**
 * This WorkflowControler extends the basic WorkflowControler and implements an
 * additional fileUpload feature to handle file uploads. The controller
 * implements a lazy loading mechanism to handle attachments.
 * 
 * 
 * @author rsoika
 */
@Named
@SessionScoped
public class WorkflowController extends org.imixs.workflow.faces.workitem.WorkflowController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String process() throws AccessDeniedException, ProcessingErrorException, PluginException, ModelException {
		// update team members
		String id = this.getWorkitem().getItemValueString("Team");
		// lookup the team entity...
		if (!"".equals(id)) {
			ItemCollection team = this.getDocumentService().load(id);
			if (team != null)
				this.getWorkitem().replaceItemValue("namTeam", team.getItemValue("Members"));
		}
		String result = super.process();

		return result;
	}

}
