package com.cineplexnotifier.model;

import java.time.LocalDate;
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
  @Column(unique = true, nullable = false)
  private String cineplexKey;
  @Column(length = 4 * 1024)
  private String description;
  private String name, thumbnailImageUrl;
  private LocalDate releaseDate;

  @XmlTransient
  @ManyToMany(mappedBy = "movies")
  private List<User> users;

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
    this.releaseDate = movie.releaseDate;
    this.thumbnailImageUrl = movie.thumbnailImageUrl;
  }

  public String getCineplexKey() {
    return cineplexKey;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public String getReleaseDate() {
    return releaseDate.toString();
  }

  public String getThumbnailImageUrl() {
    return thumbnailImageUrl;
  }

  public List<User> getUsers() {
    if (users == null) {
      users = new LinkedList<>();
    }
    return users;
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

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = LocalDate.parse(releaseDate);
  }

  public void setThumbnailImageUrl(String thumbnailImageUrl) {
    this.thumbnailImageUrl = thumbnailImageUrl;
  }

}
