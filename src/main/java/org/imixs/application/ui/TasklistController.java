package org.imixs.application.ui;

import java.io.Serializable;
import java.util.logging.Logger;

import org.imixs.workflow.faces.data.ViewController;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Select the current list of tasks sorted by modification timestamp
 * 
 * @author rsoika
 */
@Named
@ViewScoped
public class TasklistController extends ViewController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TasklistController.class.getName());

	@Override
	@PostConstruct
	public void init() {
		super.init();
		this.setQuery("(type:\"workitem\")");
		this.setSortBy("$modified");
		this.setSortReverse(true);
		this.setLoadStubs(false);
	}

}
