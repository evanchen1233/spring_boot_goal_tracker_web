package com.fdm.spring.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.spring.model.GoalStatus;

/**
 * Spring data repository interface for class GoalStatus
 * @author Yifeng Chen
 *
 */
@Repository
public interface GoalStatusRepository extends JpaRepository<GoalStatus, Integer>
{
	/**
	 * 
	 * @return all goal status' names
	 */
	@Query("SELECT goalStatusName FROM GoalStatus")
	List<String> getGoalStatusNames();
	
	/**
	 * 
	 * @param goalStatusName goal status
	 * @return	return GoalStatus objects by given goal status name
	 */
	@Query("FROM GoalStatus s WHERE s.goalStatusName = :goalStatusName")
	GoalStatus findByGoalStatusName(@Param("goalStatusName") String goalStatusName);
}