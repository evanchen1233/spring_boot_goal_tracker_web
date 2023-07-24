package com.fdm.spring.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.spring.model.User;

/**
 * Spring data repository interface for class User
 * @author Yifeng Chen
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	/**
	 * 
	 * @return all usernames
	 */
	@Query("SELECT u.username FROM User u")
	List<String> getUserNames();

	/**
	 * 
	 * @param id role id
	 * @return A list of User objects by given role id
	 */
	@Query("FROM User u WHERE u.role.id = :id")
	List<User> findByRoleId(@Param("id") int id);	
	
	/**
	 * 
	 * @param roleName role name
	 * @return A list of User objects by given role name
	 */
	@Query("FROM User u WHERE u.role.roleName = :roleName")
	List<User> findByRoleName(@Param("roleName") String roleName);
	
	/**
	 * 
	 * @param name user's first name
	 * @return	A user object by given first name
	 */
	@Query("FROM User u WHERE u.firstName = :name")
	User findByName(@Param("name") String name);
	
	/**
	 * 
	 * @param username user's username
	 * @return A user object by given username
	 */
	@Query("FROM User u WHERE u.username = :username")
	User findByUsername(@Param("username") String username);	
	
	/**
	 * 
	 * @param keyword Search Term
	 * @return A list of Users contain the Search Term in their' First Name or Last Name
	 */
	@Query("FROM User u WHERE ((u.firstName LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (u.lastName LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (u.stream.streamName LIKE CONCAT ('%', :keyword, '%')))"
			+ " AND u.role.id = 1")
	List<User> filterByStreamOrName(@Param("keyword") String keyword);
}

