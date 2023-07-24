package com.fdm.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.spring.dal.RoleRepository;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.Role;

/**
 * This is a Spring service class for Role. It performs CURD and other operations.
 * @author Yifeng Chen
 *
 */
@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * 
	 * @param id role id
	 * @return Role object by given role id
	 */
	public Role getRoleById(int id) {
		
		Optional<Role> role = roleRepository.findById(id);
		
		if(role.isPresent())			// process optional
			return role.get();
		else 
			return null;
	}
	
	/**
	 * 
	 * @return A list of all Role objects
	 */
	public List<Role> getAllRoles(){
		
		return roleRepository.findAll(); 
		
	}
	
	/**
	 * Save a Role entity
	 * @param role
	 */
	public void saveRole(Role role) {
		
		roleRepository.save(role);
		
	}
	
	/**
	 * Update a Role entity
	 * @param role
	 */
	public void updateRole(Role role){
	
		saveRole(role);
	}
	
	/**
	 * Delete a role object by given role id
	 * @param id
	 */
	public void deleteRoleById(int id) {
		
		roleRepository.deleteById(id);
	}
	
	/**
	 * Delete All Role Objects
	 * @return number of objects deleted
	 */
	public int deleteAllRoles() {
		
		int count = 0;
		List<Role> roles = roleRepository.findAll();
		
		for(Role role : roles) {
			roleRepository.delete(role);
			count++;
		}
		
		return count;
		
	}
}
