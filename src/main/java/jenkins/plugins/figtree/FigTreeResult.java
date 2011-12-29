package jenkins.plugins.figtree;

import hudson.model.AbstractBuild;

import java.io.Serializable;

public class FigTreeResult implements Serializable {

	private static final long serialVersionUID = -8924469269454046241L;

	private AbstractBuild<?, ?> build;
	
	public FigTreeResult(AbstractBuild<?, ?> build) {
		this.build = build;
	}
	
	public AbstractBuild<?, ?> getOwner() {
		return this.build;
	}
	
}
