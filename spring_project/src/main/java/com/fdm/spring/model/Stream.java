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
 * Stream is the entity we'll be using to store a user's stream
 * @author Yifeng Chen
 *
 */
@Entity
@Table(name = "STREAM")
public class Stream {

	/**
	 * Surrogate key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	/**
	 * Stream name in text
	 */
	@Column(name = "STREAM_NAME")
	private String streamName;

	/**
	 * A list of User objects with a same stream
	 */
    @OneToMany(mappedBy = "stream", cascade = CascadeType.ALL)
    private List<User> Users;

    /**
     * Constructs a new Stream
     */
	public Stream() {
		super();
	}

	/**
	 * Constructs a new Stream
	 * @param streamName Stream name in String
	 */
	public Stream(String streamName) {
		super();
		this.streamName = streamName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public List<User> getUsers() {
		return Users;
	}

	public void setUsers(List<User> users) {
		Users = users;
	}

	@Override
	public String toString() {
		return "Stream [id=" + id + ", streamName=" + streamName + "]";
	}
    
    
}
