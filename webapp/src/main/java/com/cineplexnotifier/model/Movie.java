package com.cineplexnotifier.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name, cineplexKey, descriptionHtml;
	private byte[] thumbnailImage, posterImage;

	public String getCineplexKey() {
		return cineplexKey;
	}

	public String getDescriptionHtml() {
		return descriptionHtml;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public byte[] getPosterImage() {
		return posterImage;
	}
	public byte[] getThumbnailImage() {
		return thumbnailImage;
	}

	public void setCineplexKey(String cineplexKey) {
		this.cineplexKey = cineplexKey;
	}

	public void setDescriptionHtml(String descriptionHtml) {
		this.descriptionHtml = descriptionHtml;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosterImage(byte[] posterImage) {
		this.posterImage = posterImage;
	}

	public void setThumbnailImage(byte[] thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

}
