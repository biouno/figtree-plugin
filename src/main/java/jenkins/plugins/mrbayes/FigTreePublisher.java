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
package jenkins.plugins.mrbayes;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.IOException;

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
	private static final FigTreePublisherDescriptor DESCRIPTOR = new FigTreePublisherDescriptor(); 
	
	private final String name;
	
	private final String treeFilePattern;
	
	private final Integer width;
	
	private final Integer height;
	
	@DataBoundConstructor
	public FigTreePublisher(
			String name, 
			String treeFilePattern, 
			Integer width, 
			Integer height) {
		this.name = name;
		this.treeFilePattern = treeFilePattern;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	
	/* (non-Javadoc)
	 * @see hudson.tasks.BuildStep#getRequiredMonitorService()
	 */
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	/* (non-Javadoc)
	 * @see hudson.tasks.BuildStepCompatibilityLayer#perform(hudson.model.AbstractBuild, hudson.Launcher, hudson.model.BuildListener)
	 */
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {
		
		return Boolean.TRUE; 
	}
	
	/* (non-Javadoc)
	 * @see hudson.tasks.BuildStepCompatibilityLayer#getProjectAction(hudson.model.AbstractProject)
	 */
	@Override
	public Action getProjectAction(AbstractProject<?, ?> project) {
		// TODO Auto-generated method stub
		return super.getProjectAction(project);
	}
	
	/* (non-Javadoc)
	 * @see hudson.tasks.Recorder#getDescriptor()
	 */
	@Override
	public BuildStepDescriptor getDescriptor() {
		return (FigTreePublisherDescriptor)super.getDescriptor();
	}

}
