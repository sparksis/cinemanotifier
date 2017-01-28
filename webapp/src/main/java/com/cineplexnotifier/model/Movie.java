package com.cineplexnotifier.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Movie extends BaseModel {

  private boolean available;
  @Column(length = 4 * 1024)
  private String description;
  private String name, thumbnailImageUrl;
  @Column(unique = true, nullable = false)
  private String cineplexKey;
  private byte[] thumbnailImage, posterImage;

  @XmlTransient
  @ManyToMany(mappedBy = "movies")
  private List<User> users;

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

  public List<User> getUsers() {
    if (users == null) {
      users = new LinkedList<>();
    }
    return users;
  }

  /**
   * Merges the following fields
   * <ul>
   * <li>name</li>
   * <li>available</li>
   * <li>description</li>
   * <li>thumbnailImageUrl</li>
   * </ul>
   */
  @Override
  public void merge(BaseModel m) {
    if (!(m instanceof Movie)) {
      throw new UnsupportedOperationException("Unable to merge non-movie type");
    }
    Movie movie = (Movie) m;

    this.name = movie.name;
    this.available = movie.available;
    this.description = movie.description;
    this.thumbnailImageUrl = movie.thumbnailImageUrl;
  }

}
