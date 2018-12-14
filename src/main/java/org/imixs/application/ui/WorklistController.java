package org.imixs.application.ui;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.imixs.workflow.faces.data.ViewController;

@Named
@ViewScoped
public class WorklistController extends ViewController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(WorklistController.class.getName());

	@Override
	@PostConstruct
	public void init() {
		super.init();

		this.setType("workitem");
		this.setQuery("(type:\"workitem\")");
		this.setSortBy("$modified");
		this.setSortReverse(true);
	}

}
