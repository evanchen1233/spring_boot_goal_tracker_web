package com.fdm.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.spring.model.Role;

/**
 * Spring data repository interface for class Role
 * @author Yifeng Chen
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
