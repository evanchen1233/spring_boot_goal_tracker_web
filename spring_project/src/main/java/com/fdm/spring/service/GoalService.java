package com.fdm.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.spring.dal.GoalRepository;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.User;

/**
 * This is a Spring service class for Goal. It performs CURD and other operations.
 * @author Yifeng Chen
 *
 */
@Service
public class GoalService {

	@Autowired
	GoalRepository goalRepository;
	
	/**
	 * 
	 * @param id Goal Id
	 * @return A Goal Object by given goal id
	 */
	public Goal getGoalById(int id) {
		
		Optional<Goal> goal = goalRepository.findById(id);
		
		if(goal.isPresent())			// process optional
			return goal.get();
		else 
			return null;
	}
	
	/**
	 * 
	 * @param 
	 * @return All Goal Objects
	 */
	public List<Goal> getAllGoals(){
			
		return goalRepository.findAll();
	
	}
	
	/**
	 * 
	 * @param username 
	 * @return All Goal Objects by the given username
	 */
	public List<Goal> getGoalsByUsername(String username){
			
		return goalRepository.findByUsername(username);	
	
	}
	
	/**
	 * 
	 * @param id Goal Type id
	 * @return All Goal objects by given goal type id
	 */
	public List<Goal> getGoalsByGoalTypeId(int id){
		
		return goalRepository.findByGoalTypeId(id);	
	
	}
	
	/**
	 * 
	 * @param username
	 * @param goalStatusName goal status
	 * @return Number of goals with the given username and goal status
	 */
	public int countByUsernameAndGoalStatus(String username, String goalStatusName) {
		
		return goalRepository.countByUsernameAndGoalStatus(username, goalStatusName);
		
	}
	
	/**
	 * 
	 * @param username
	 * @param goalTypeName goal type
	 * @return Number of goals with the given username and goal type
	 */
	public int countByUsernameAndGoalType(String username, String goalTypeName) {
		
		return goalRepository.countByUsernameAndGoalType(username, goalTypeName);
		
	}
	
	/**
	 * 
	 * @return A list contains number of goals by each goal status
	 */
	public List<Integer> countByGoalStatus() {
		
		List<Integer> totalCounts = new ArrayList<>();
		
		totalCounts.add(goalRepository.countByGoalStatus("Planning"));
		totalCounts.add(goalRepository.countByGoalStatus("In Progress"));
		totalCounts.add(goalRepository.countByGoalStatus("Done"));
		totalCounts.add(goalRepository.countByGoalStatus("Discontinued"));
		
		return totalCounts;
		
	}
	
	/**
	 * 
	 * @return A list contains number of goals by each goal type
	 */
	public List<Integer> countByGoalType() {
		
		List<Integer> totalCounts = new ArrayList<>();
		
		totalCounts.add(goalRepository.countByGoalType("Learning path"));
		totalCounts.add(goalRepository.countByGoalStatus("Training"));
		totalCounts.add(goalRepository.countByGoalStatus("Interview"));
		totalCounts.add(goalRepository.countByGoalStatus("Self-Study"));
		
		return totalCounts;
		
	}
	
	/**
	 * Provide Search operation
	 * @param keyword Search Term
	 * @param username 
	 * @return All goals contain the Search Term in their attributes. 
	 *         If the Search Term is not found, return all goals.
	 */
    public List<Goal> filteredFindAll(String keyword, String username) {
    	
        if (keyword != null) {
            return goalRepository.search(keyword, username);
        }
        return goalRepository.findByUsername(username);
    }
    
    /**
     * Save a goal entity
     * @param goal
     */
	public void saveGoal(Goal goal) {
		
		goalRepository.save(goal);
	}
	
	/**
	 * Update a goal entity
	 * @param goal
	 */
	public void updateGoal(Goal goal) {
		
		saveGoal(goal);
	}
	
	/**
	 * Remove Goal by given goal id
	 * @param id
	 */
	public void deleteGoalById(int id) {
			
		goalRepository.deleteById(id);
	}	

	/**
	 * Delete All Goal Objects
	 * @return number of objects deleted
	 */
	public int deleteAllGoals() {
		
		int count = 0;
		List<Goal> goals = goalRepository.findAll();
		
		for(Goal goal : goals) {
			goalRepository.delete(goal);
			count++;
		}
		
		return count;
		
	}
}
