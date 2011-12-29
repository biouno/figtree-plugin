package jenkins.plugins.figtree;

import hudson.model.BuildBadgeAction;

import org.kohsuke.stapler.StaplerProxy;

public class FigTreeBuildAction implements BuildBadgeAction, StaplerProxy {

	public static final String DISPLAY_NAME = "FigTree";
	public static final String ICON_FILE_NAME = "/plugin/figtree/icons/figtree-40.png";
	public static final String URL_NAME = "figTreeResult";
	
	// To where the stapler proxy will redirect
	private FigTreeResult figTreeResult;
	
	public String getTooltip() {
		return "FigTree";
	}

	public String getIcon() {
		return ICON_FILE_NAME;
	}

	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	public String getIconFileName() {
		return null;
	}

	public String getUrlName() {
		return URL_NAME;
	}
	
	public Object getTarget() {
		return this.figTreeResult;
	}

}
