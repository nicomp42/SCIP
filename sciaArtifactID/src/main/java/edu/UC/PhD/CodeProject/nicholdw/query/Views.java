/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.Config;

public class Views {
	private ArrayList<View> views;
	
	public Views() {
		setViews(new ArrayList<View>());
	}
	public void addView(View view) {
		views.add(view);
	}
	public ArrayList<View> getViews() {
		return views;
	}
	public void setViews(ArrayList<View> views) {
		this.views = views;
	}
	public View findView(View targetView) {
		View foundView = null;
		for (View view: views) {
			if (Config.getConfig().compareSchemaNames(view.getSchemaName(), targetView.getSchemaName()) &&
				Config.getConfig().compareQueryNames(view.getViewName(), targetView.getViewName())) {
				foundView = view;
			}
		}
		return foundView;
	}
	public View findViewBySchemaNameAndViewName(String schemaName, String viewName) {
		View foundView = null;
		for (View view: views) {
			if (Config.getConfig().compareSchemaNames(view.getSchemaName(), schemaName) &&
				Config.getConfig().compareQueryNames(view.getViewName(), viewName)) {
				foundView = view;
			}
		}
		return foundView;
	}
}
