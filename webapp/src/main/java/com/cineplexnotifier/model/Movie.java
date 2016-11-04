package com.cineplexnotifier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Movie extends BaseModel {

	private boolean available;
	@Column(length = 4 * 1024) private String description;
	private String name, cineplexKey, thumbnailImageUrl;
	private byte[] thumbnailImage, posterImage;

	public String getCineplexKey() {
		return cineplexKey;
	}

	public String getDescription() {
		return description;
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

	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setCineplexKey(String cineplexKey) {
		this.cineplexKey = cineplexKey;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

}
