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
 * GoalType is the entity we'll be using to store a goal's type.
 * @author Yifeng Chen
 *
 */
@Entity
@Table(name = "GOAL_TYPE")
public class GoalType {

	/**
	 * Surrogate key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	
	/**
	 * Type of a goal in text
	 */
	@Column(name = "goal_type_name")
	private String goalTypeName;

	/**
	 * A list of Goal objects with a same goal type
	 */
    @OneToMany(mappedBy = "goalType", cascade = CascadeType.ALL)
    private List<Goal> goals;
    
    /**
     * Constructs a new GoalType
     */
	public GoalType() {

	}

	/**
	 * Constructs a new GoalType
	 * @param goalTypeName A goal's type in String
	 */
	public GoalType(String goalTypeName) {
		super();
		this.goalTypeName = goalTypeName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoalTypeName() {
		return goalTypeName;
	}

	public void setGoalTypeName(String goalTypeName) {
		this.goalTypeName = goalTypeName;
	}

	
	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		return "GoalType [id=" + id + ", goalTypeName=" + goalTypeName + "]";
	}

	
}
