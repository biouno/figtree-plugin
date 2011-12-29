/*
 * The MIT License
 *
 * Copyright (c) <2011> <Bruno P. Kinoshita>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins.plugins.figtree;

import figtree.application.FigTreeApplication;
import hudson.Extension;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.Util;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Publisher of FigTree. It is executed after builders, and can collect the
 * output of mrbayes, for instance, and produce funky graphs.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class FigTreePublisher extends Recorder {

	@Extension
	public static final FigTreePublisherDescriptor DESCRIPTOR = new FigTreePublisherDescriptor();

	private final String treeFilePattern;

	private final Integer width;

	private final Integer height;

	@DataBoundConstructor
	public FigTreePublisher(String treeFilePattern, Integer width,
			Integer height) {
		this.treeFilePattern = treeFilePattern;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the treeFilePattern
	 */
	public String getTreeFilePattern() {
		return treeFilePattern;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.tasks.BuildStep#getRequiredMonitorService()
	 */
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hudson.tasks.BuildStepCompatibilityLayer#perform(hudson.model.AbstractBuild
	 * , hudson.Launcher, hudson.model.BuildListener)
	 */
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			final BuildListener listener) throws InterruptedException, IOException {
		
		listener.getLogger().println("Executing FigTree...");
		
		if(StringUtils.isBlank(getTreeFilePattern())) {
			listener.getLogger().println("No tree files pattern defined. Skipping FigTree execution.");
			return Boolean.TRUE;
		}
		
		build.addAction(new FigTreeBuildAction());
		
		final FilePath workspace = build.getWorkspace();
		
		workspace.act(new FileCallable<Void>() {

			private static final long serialVersionUID = 2488391182467776931L;

			public Void invoke(File file, VirtualChannel channel)
					throws IOException, InterruptedException {
				String[] treeFiles = scan(file, getTreeFilePattern(), listener);
				
				listener.getLogger().println("Found " + (treeFiles!=null?treeFiles.length : 0) + " tree files.");
				
				Integer width = getWidth();
				Integer height = getHeight();
				
				if(width == null || width <= 0) {
					width = 800;
				}
				if(height == null || height <= 0) {
					height = 600;
				}
				
				if(treeFiles != null && treeFiles.length > 0) {
					for(final String treeFile : treeFiles) {
						listener.getLogger().println("Processing tree [" + treeFile + "].");
						final String fullTreeFilePath = new File(file, treeFile).getAbsolutePath();
						String rawName = FilenameUtils.removeExtension(fullTreeFilePath);
						String graphic = rawName + ".gif";
						FigTreeApplication.createGraphic("GIF", width, height, fullTreeFilePath, graphic);
						listener.getLogger().println("Created graphic ["+graphic+"]. width: "+width+", height: " + height);
					}
				}
				return null;
			}
		});
		
		return Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hudson.tasks.BuildStepCompatibilityLayer#getProjectAction(hudson.model
	 * .AbstractProject)
	 */
	@Override
	public Action getProjectAction(AbstractProject<?, ?> project) {
		return new FigTreeProjectAction(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.tasks.Recorder#getDescriptor()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BuildStepDescriptor getDescriptor() {
		return (FigTreePublisherDescriptor) super.getDescriptor();
	}

	protected String[] scan(final File directory, final String includes,
			final BuildListener listener) throws IOException {

		String[] fileNames = new String[0];

		if (StringUtils.isNotBlank(includes)) {
			FileSet fs = null;

			try {
				fs = Util.createFileSet(directory, includes);

				DirectoryScanner ds = fs.getDirectoryScanner();
				fileNames = ds.getIncludedFiles();
			} catch (BuildException e) {
				e.printStackTrace(listener.getLogger());
				throw new IOException(e);
			}
		}

		return fileNames;

	}

}
