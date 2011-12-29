package jenkins.plugins.figtree;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class FigTreeProjectAction implements Action {

	public static final String ICON_FILE_NAME = "/plugin/figtree/icons/figtree-40.png";
	public static final String URL_NAME = "figTreeResult";
	private final AbstractProject<?, ?> project;
	
	public FigTreeProjectAction(AbstractProject<?, ?> project) {
		this.project = project;
	}
	
	public AbstractProject<?, ?> getProject() {
		return project;
	}
	
	public String getDisplayName() {
		return "FigTree";
	}

	public String getIconFileName() {
		return ICON_FILE_NAME;
	}

	public String getUrlName() {
		return URL_NAME;
	}
	
	/**
	 * 
	 * Show CCM html report f the latest build. If no builds are associated 
	 * with CCM , returns info page.
	 * 
	 * @param req
	 *            Stapler request
	 * @param res
	 *            Stapler response
	 * @throws IOException
	 *             in case of an error
	 */
	public void doIndex( final StaplerRequest req, final StaplerResponse res ) 
	throws IOException
	{
		AbstractBuild<?, ?> lastBuild = getLastBuildWithFigTree();
		if (lastBuild == null)
		{
			res.sendRedirect2("nodata");
		}
		else 
		{
			int buildNumber = lastBuild.getNumber();
			res.sendRedirect2( String.format("../%d/%s", buildNumber,
					"target") );
		}
	}
	
	/**
	 * Retrieves the last build with FigTree in the project.
	 * 
	 * @return Last build with FigTree in the project or <code>null</code>.
	 */
	private AbstractBuild<?, ?> getLastBuildWithFigTree()
	{
		AbstractBuild<?, ?> lastBuild = (AbstractBuild<?, ?>) project.getLastBuild();
		while (lastBuild != null
				&& lastBuild.getAction( getBuildActionClass() ) == null)
		{
			lastBuild = lastBuild.getPreviousBuild();
		}
		return lastBuild;
	}
	
	protected Class<FigTreeBuildAction> getBuildActionClass()
	{
		return FigTreeBuildAction.class;
	}
	
}
