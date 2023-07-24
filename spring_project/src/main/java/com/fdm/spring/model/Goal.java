package com.fdm.spring.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Goal is the entity we'll be using to store all goals created by users.
 * @author Yifeng Chen
 *
 */
@Entity
@Table(name = "GOAL")
public class Goal {

	/**
	 * Surrogate key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	/**
	 * Start date of a goal
	 */
	@Column(name = "start_date")
	private Date startDate;
	
	/**
	 * Planning end date of a goal
	 */
	@Column(name = "target_end_date")
	private Date targetEndDate;
	
	/**
	 * Actual end date of a goal
	 */
	@Column(name = "end_date")
	private Date endDate;
	
	/**
	 * Description or comment of a goal, can be null
	 */
	@Column(length = 255)
	private String description;
	
	/**
	 * An User object represents the owner of a goal
	 */
	@ManyToOne()
	@JoinColumn(name = "USERNAME")
	private User user;
	
	/**
	 * An GoalType object represents the type of a goal
	 */
	@ManyToOne()
	@JoinColumn(name = "GOAL_TYPE_ID")
	private GoalType goalType;

	/**
	 * An GoalStatus object represents the status of a goal
	 */
	@ManyToOne()
	@JoinColumn(name = "GOAL_STATUS_ID")
	private GoalStatus goalStatus;
	
	/**
	 * Constructs a new Goal
	 */
	public Goal() {
		super();
	}

	/**
	 * Constructs a new Goal
	 * @param startDate
	 * @param targetEndDate
	 * @param endDate
	 * @param description
	 */
	public Goal(Date startDate, Date targetEndDate, Date endDate, String description) {
		super();
		this.startDate = startDate;
		this.targetEndDate = targetEndDate;
		this.endDate = endDate;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getTargetEndDate() {
		return targetEndDate;
	}

	public void setTargetEndDate(Date targetEndDate) {
		this.targetEndDate = targetEndDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GoalType getGoalType() {
		return goalType;
	}

	public void setGoalType(GoalType goalType) {
		this.goalType = goalType;
	}

	
	public GoalStatus getGoalStatus() {
		return goalStatus;
	}

	public void setGoalStatus(GoalStatus goalStatus) {
		this.goalStatus = goalStatus;
	}

	@Override
	public String toString() {
		return "Goal [id=" + id + ", startDate=" + startDate + ", targetEndDate=" + targetEndDate + ", endDate="
				+ endDate + ", description=" + description + "]";
	}

	
}
