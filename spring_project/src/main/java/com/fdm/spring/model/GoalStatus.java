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
 * GoalStatus is the entity we'll be using to store a goal's status.
 * @author Yifeng Chen
 *
 */
@Entity
@Table(name = "GOAL_STATUS")
public class GoalStatus {

	/**
	 * Surrogate key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	/**
	 * Status of a goal in text
	 */
	@Column(name = "goal_status_name")
	private String goalStatusName;

	/**
	 * A list of Goal objects with a same goal status
	 */
    @OneToMany(mappedBy = "goalStatus", cascade = CascadeType.ALL)
    private List<Goal> goals; 
    
    /**
     *  Constructs a new GoalStatus
     */
	public GoalStatus() {
    	super();
	}

	/**
	 * Constructs a new GoalStatus
	 * @param goalStatusName A goal's status in String
	 */
	public GoalStatus(String goalStatusName) {
		super();
		this.goalStatusName = goalStatusName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoalStatusName() {
		return goalStatusName;
	}

	public void setGoalStatusName(String goalStatusName) {
		this.goalStatusName = goalStatusName;
	}

	
	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		return "GoalStatus [id=" + id + ", goalStatusName=" + goalStatusName + "]";
	}

	
}
