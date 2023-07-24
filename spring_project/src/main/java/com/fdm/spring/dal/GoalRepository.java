package com.fdm.spring.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.spring.model.Goal;

/**
 * Spring data repository interface for class Goal
 * @author Yifeng Chen
 *
 */
@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer>
{
	
	/**
	 * 
	 * @param username A user's username
	 * @return A list of Goal objects by given user
	 */
	@Query("FROM Goal g WHERE g.user.username = :username")
	List<Goal> findByUsername(@Param("username") String username);	
	
	/**
	 * 
	 * @param id A Goal type's id
	 * @return A list of Goal objects by given goal type
	 */
	@Query("FROM Goal g WHERE g.goalType.id = :id")
	List<Goal> findByGoalTypeId(@Param("id") int id);	
	
	/**
	 * 
	 * @param goalTypeName A goal type
	 * @return number of goals by given goal type
	 */
	@Query("SELECT COUNT(*) FROM Goal g WHERE g.goalType.goalTypeName = :goalTypeName")
	int countByGoalType(@Param("goalTypeName") String goalTypeName);
	
	/**
	 * 
	 * @param username A user's username
	 * @param goalStatusName goal status
	 * @return number of goals by given user and goal status
	 */
	@Query("SELECT COUNT(*) FROM Goal g WHERE g.user.username = :username AND g.goalStatus.goalStatusName = :goalStatusName")
	int countByUsernameAndGoalStatus(@Param("username") String username, @Param("goalStatusName") String goalStatusName);	

	/**
	 * 
	 * @param username  A user's username
	 * @param goalTypeName goal type
	 * @return number of goals by given user and goal type
	 */
	@Query("SELECT COUNT(*) FROM Goal g WHERE g.user.username = :username AND g.goalType.goalTypeName = :goalTypeName")
	int countByUsernameAndGoalType(@Param("username") String username, @Param("goalTypeName") String goalTypeName);	
	
	/**
	 * 
	 * @param goalStatusName goal status
	 * @return number of goals by given goal status
	 */
	@Query("SELECT COUNT(*) FROM Goal g WHERE g.goalStatus.goalStatusName = :goalStatusName")
	int countByGoalStatus(@Param("goalStatusName") String goalStatusName);	
	
	/**
	 * 
	 * @param keyword Search Term
	 * @param username A user's name
	 * @return A list of Goal objects contains given Search Term in any Goal attributes
	 */
	@Query("FROM Goal g WHERE (g.description LIKE CONCAT('%', :keyword, '%')"
			+ " OR g.startDate LIKE CONCAT('%', :keyword, '%')"
			+ " OR g.targetEndDate LIKE CONCAT('%', :keyword, '%')"
			+ " OR g.goalType.goalTypeName LIKE CONCAT('%', :keyword, '%')"
			+ " OR g.goalStatus.goalStatusName LIKE CONCAT('%', :keyword, '%'))"
			+ " AND (g.user.username = :username)")
	public List<Goal> search(@Param("keyword") String keyword, @Param("username") String username);
}
