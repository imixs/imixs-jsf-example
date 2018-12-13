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
package org.imixs.application.ui;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.imixs.workflow.faces.data.ViewController;

/**
 * This backing bean is an example how to define a custom view
 * 
 * @see sub_tasklist.xhtml
 * 
 * @author rsoika
 * 
 */
@Named
@ConversationScoped
public class TasklistController extends ViewController {

	private static final long serialVersionUID = 1L;

	@Override
	@PostConstruct
	public void init() {
		super.init();
		this.setQuery("(type:workitem)");
		this.setSortBy("$modified");
		this.setSortReverse(true);
	}
}
