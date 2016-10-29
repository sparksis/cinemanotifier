package com.cineplexnotifier.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class User extends BaseModel {
	
	private String email;
	@OneToMany private List<Movie> movies;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Movie> getMovies() {
		return movies;
	}

}
