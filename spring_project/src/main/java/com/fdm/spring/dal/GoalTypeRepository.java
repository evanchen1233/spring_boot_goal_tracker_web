package com.fdm.spring.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.spring.model.GoalType;

/**
 * Spring data repository interface for class GoalType
 * @author Yifeng Chen
 *
 */
@Repository
public interface GoalTypeRepository extends JpaRepository<GoalType, Integer>
{
	/**
	 * 
	 * @return All goal type names
	 */
	@Query("SELECT goalTypeName FROM GoalType")
	List<String> 
	getGoalTypeNames();
	
	/**
	 * 
	 * @param goalTypeName goal type name
	 * @return	GoalType object by given goal type name
	 */
	@Query("FROM GoalType s WHERE s.goalTypeName = :goalTypeName")
	GoalType findByGoalTypeName(@Param("goalTypeName") String goalTypeName);
}