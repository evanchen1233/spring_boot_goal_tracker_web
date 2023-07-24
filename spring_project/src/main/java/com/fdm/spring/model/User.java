package com.fdm.spring.model;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * User is the entity we'll be using to store a user's detail
 * @author Yifeng Chen
 *
 */
@Entity
@Table(name = "USER")
public class User {

	/**
	 * Natural key, FDM company email address
	 */
	@Id
	@Column(length = 50)
	private String username;
	
	/**
	 * User's first name
	 */
	@Column(name = "first_name")
	private String firstName;
	
	/**
	 * User's family name
	 */
	@Column(name = "last_name")
	private String lastName;
	
	/**
	 * User's password
	 */
	private String password;
	
	/**
	 * A list of Goal objects owned by a user
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Goal> goals;
	
	/*
	 * user's role
	 */
	@ManyToOne()
	@JoinColumn(name = "role_id")
	private Role role;
	
	/**
	 * user's stream
	 */
	@ManyToOne()
	@JoinColumn(name = "stream_id")
	private Stream stream;
	
	/**
	 * Constructs a new User
	 */
	public User() {

	}

	/**
	 * Constructs a new User
	 * @param username username in a fdm email format
	 * @param firstName 
	 * @param lastName
	 * @param password
	 */
	public User(String username, String firstName, String lastName, String password) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Stream getStream() {
		return stream;
	}

	public void setStream(Stream stream) {
		this.stream = stream;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + "]";
	}

}
