package com.raslan.sff.core.data.forms;

import java.util.List;

public class FormLayout {
	private List<List<FormField>> pages;
	private String requestURL;
	private String requestMethod;
	
	public List<List<FormField>> getPages() {
		return pages;
	}
	public void setPages(List<List<FormField>> pages) {
		this.pages = pages;
	}
	public String getRequestURL() {
		return requestURL;
	}
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
}
