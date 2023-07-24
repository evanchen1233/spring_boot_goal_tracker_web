package com.fdm.spring.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Role is the entity we'll be using to store a user's role 
 * @author Yifeng Chen
 *
 */
@Entity
@Table(name = "ROLE")
public class Role {

	/**
	 * Surrogate key 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	/**
	 * A user's role in text
	 */
	@Column(name = "ROLE_NAME")
	private String roleName;

	/**
	 * A list of User objects with a same role
	 */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> Users;

    /**
     * Constructs a new Role
     */
	public Role() {
		super();
	}

	/**
	 * Constructs a new Role
	 * @param roleName Role name in String
	 */
	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return Users;
	}

	public void setUsers(List<User> users) {
		Users = users;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + "]";
	}
    
    
}
