package com.cineplexnotifier.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class User extends BaseModel {
	
	@Column(unique = true)
	private String email;
	@OneToMany private List<Movie> movies;
	
	public User(){}
	public User(String emailAddress){
		this.email = emailAddress;
	}
	
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
