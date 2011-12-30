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

import hudson.model.BuildBadgeAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FigTreeBuildAction implements BuildBadgeAction, Serializable {

	private static final long serialVersionUID = 7387722831707752394L;
	
	public static final String DISPLAY_NAME = "FigTree";
	public static final String ICON_FILE_NAME = "/plugin/figtree/icons/figtree-40.png";
	public static final String URL_NAME = "figTreeResult";
	
	private final List<String> linksToFigTreeGraphics;
	
	public FigTreeBuildAction() {
		linksToFigTreeGraphics = new ArrayList<String>();
	}
	
	protected void addFigTreeGraphicLink(String graphic) {
		this.linksToFigTreeGraphics.add(graphic);
	}
	
	public String getGraphics() {
		final StringBuilder graphics = new StringBuilder();
		for(String link : linksToFigTreeGraphics) {
			graphics.append("<p><a href='../ws/"+link+"' rel='lightbox[figtree]' /><img width='300' height='300' src='../ws/"+link+"' alt='FigTree graphic' title='FigTree graphic' rel='lightbox[figtree]' /></a>\n");
		}
		return graphics.toString();
	}
	
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

}
