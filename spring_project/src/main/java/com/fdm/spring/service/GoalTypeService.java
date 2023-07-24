package com.fdm.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.spring.dal.GoalTypeRepository;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.GoalType;

/**
 * This is a Spring service class for Goal Type. It performs CURD and other operations.
 * @author Yifeng Chen
 *
 */
@Service
public class GoalTypeService {
	
	@Autowired
	GoalTypeRepository goalTypeRepository;
	
	/**
	 * 
	 * @param id
	 * @return GoalType object by given goal type id
	 */
	public GoalType getGoalTypeById(int id) {
		
		Optional<GoalType> goalType = goalTypeRepository.findById(id);
		
		if(goalType.isPresent())			// process optional
			return goalType.get();
		else 
			return null;
	}
	
	/**
	 * 
	 * @param goalTypeName goal type name
	 * @return GoalType object by given goal type name
	 */
	public GoalType getGoalTypeByGoalTypeName(String goalTypeName) {
		
		GoalType goalType = goalTypeRepository.findByGoalTypeName(goalTypeName);
		
		return goalType;

	}
	
	/**
	 * 
	 * @return All GoalType Objects
	 */
	public List<GoalType> getAllGoalTypes(){
		
		return goalTypeRepository.findAll(); 
		
	}
	
	/**
	 * 
	 * @return All GoalType names
	 */
	public List<String> getGoalTypeNames(){
		
		return goalTypeRepository.getGoalTypeNames();
		
	}
	
	/**
	 * Save a goal type entity
	 * @param goalType
	 */
	public void saveGoalType(GoalType goalType) {
		
		goalTypeRepository.save(goalType);
		
	}
	
	/**
	 * Update a goal type entity
	 * @param goalType
	 */
	public void updateGoalType(GoalType goalType){
	
		saveGoalType(goalType);
	}
	
	/**
	 * Delete a goal type by given goal type id
	 * @param id
	 */
	public void deleteGoalTypeById(int id) {
		
		goalTypeRepository.deleteById(id);
	}
	
	/**
	 * Delete All GoalType Objects
	 * @return number of object deleted
	 */
	public int deleteAllGoalTypes() {
		
		int count = 0;
		List<GoalType> goalTypes = goalTypeRepository.findAll();
		
		for(GoalType goalType : goalTypes) {
			goalTypeRepository.delete(goalType);
			count++;
		}
		
		return count;
		
	}
}
