package com.raslan.sff.core.data.forms;

import java.util.List;

public class FormLayout {
	private String name;
	private List<List<FormField>> pages;
	private String requestURL;
	private String requestMethod;
	private int imageHeight;
	private int imageWidth;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
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
