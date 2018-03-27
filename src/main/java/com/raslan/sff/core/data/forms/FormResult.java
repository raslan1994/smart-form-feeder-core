package com.raslan.sff.core.data.forms;

import java.util.List;

public class FormResult {
	private List<FormFieldWithImage> pages;
	private String requestURL;
	private String requestMethod;
	
	public List<FormFieldWithImage> getPages() {
		return pages;
	}
	public void setPages(List<FormFieldWithImage> pages) {
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
	public void fill(FormLayout layout){
		this.requestURL = layout.getRequestURL();
		this.requestMethod = layout.getRequestMethod();
	}
}
