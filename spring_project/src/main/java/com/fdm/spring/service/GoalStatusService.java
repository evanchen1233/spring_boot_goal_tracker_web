package com.fdm.spring.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdm.spring.dal.GoalStatusRepository;
import com.fdm.spring.model.GoalStatus;

/**
 * This is a Spring service class for GoalStatus. It performs CURD and other operations.
 * @author Yifeng Chen
 *
 */
@Service
public class GoalStatusService {
	
	@Autowired
	GoalStatusRepository goalStatusRepository;
	
	/**
	 * 
	 * @param id goal status id
	 * @return	goal status by given goal status id
	 */
	public GoalStatus getGoalStatusById(int id) {
		
		Optional<GoalStatus> goalStatus = goalStatusRepository.findById(id);
		
		if(goalStatus.isPresent())			// process optional
			return goalStatus.get();
		else 
			return null;
	}
	
	/**
	 * 
	 * @return A list of all GoalStatus objects
	 */
	public List<GoalStatus> getAllGoalStatus(){
		
		return goalStatusRepository.findAll(); 
		
	}
	
	/**
	 * 
	 * @param goalStatusName 
	 * @return goal status by given goal status name
	 */
	public GoalStatus getGoalStatusByGoalStatusName(String goalStatusName) {
		
		GoalStatus goalStatus = goalStatusRepository.findByGoalStatusName(goalStatusName);
		
		return goalStatus;

	}
	
	/**
	 * 
	 * @return All goal status names
	 */
	public List<String> getGoalStatusNames(){
		
		return goalStatusRepository.getGoalStatusNames();
		
	}
	
	/**
	 * Save a goal status entity
	 * @param goalStatus
	 */
	public void saveGoalStatus(GoalStatus goalStatus) {
		
		goalStatusRepository.save(goalStatus);
		
	}
	
	/**
	 * Update a goal status entity
	 * @param goalStatus
	 */
	public void updateGoalStatus(GoalStatus goalStatus){
	
		saveGoalStatus(goalStatus);
	}
	
	/**
	 * Delete a goal status by given goal status id
	 * @param id
	 */
	public void deleteGoalStatusById(int id) {
		
		goalStatusRepository.deleteById(id);
	}
	
	/**
	 * Delete All GoalStatus Objects
	 * @return number of objects deleted
	 */
	public int deleteAllGoalStatus() {
		
		int count = 0;
		List<GoalStatus> goalStatuses = goalStatusRepository.findAll();
		
		for(GoalStatus goalStatus : goalStatuses) {
			goalStatusRepository.delete(goalStatus);
			count++;
		}
		
		return count;
		
	}
}
