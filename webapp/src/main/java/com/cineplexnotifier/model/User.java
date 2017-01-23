package com.cineplexnotifier.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.NotImplementedException;

@Entity
@XmlRootElement
public class User extends BaseModel {
	
	@Column(unique = true)
	private String email;
	@ManyToMany private List<Movie> movies;
	
	public User(){}

	public User(String emailAddress) {
		this.email = emailAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Movie> getMovies() {
		if (movies == null) {
			movies = new LinkedList<>();
		}
		return movies;
	}

	@Override
	public void merge(BaseModel m) {
		//TODO: User:merge(BaseModel) 
		throw new NotImplementedException("TODO: User:merge(BaseModel)");
	}

}
