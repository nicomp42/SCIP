/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;

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
}
