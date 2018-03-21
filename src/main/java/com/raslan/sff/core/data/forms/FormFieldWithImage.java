package com.raslan.sff.core.data.forms;

import java.awt.image.BufferedImage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"img"})
public class FormFieldWithImage extends FormFieldWithValue{
	private BufferedImage img;

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
