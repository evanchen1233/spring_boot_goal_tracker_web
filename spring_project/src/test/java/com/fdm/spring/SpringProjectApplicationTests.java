package com.fdm.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdm.spring.service.GoalService;
import com.fdm.spring.service.GoalTypeService;
import com.fdm.spring.service.RoleService;
import com.fdm.spring.service.UserService;
import com.fdm.spring.model.Role;
import com.fdm.spring.model.Stream;
import com.fdm.spring.model.User;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.GoalType;

import java.sql.Date;

@SpringBootTest
class SpringProjectApplicationTests {

	@Autowired 
	UserService userService;
	
	@Autowired 
	GoalService goalService;
	
	@Autowired 
	GoalTypeService goalTypeService;
	
	@Autowired 
	RoleService roleService;
	
	GoalType goalType;
	Role role;
	Stream stream;
	User user;
	
	@Test
	void contextLoads() {
	}
	
	@BeforeEach
	void setup() {
		
		goalService.deleteAllGoals();
		userService.deleteAllUsers();
		goalTypeService.deleteAllGoalTypes();
		roleService.deleteAllRoles();
		
		goalType = new GoalType("Interview");
		role = new Role("trainee");
		stream = new Stream("BABI");
		user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "123");
	}
	

	
	// test UserService
	
	@Test
	public void add_a_user() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");
		user.setRole(role);
		userService.saveUser(user);
		
		User userTest = userService.getUserByUsername(user.getUsername());
		List<User> users = userService.getAllUsers();
		
		assertEquals(user.toString(), userTest.toString());
		assertEquals(1, users.size());	
		
	}
	
	@Test
	public void add_a_user_and_delete_it() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");
		user.setRole(role);
		userService.saveUser(user);
		
		userService.deleteUserByUsername(user.getUsername());	
		List<User> users = userService.getAllUsers();
		
		assertEquals(0, users.size());
			
	}
	
	@Test
	public void update_a_user() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");
		user.setRole(role);
		userService.saveUser(user);
		
		User userTest = new User("yifeng.chen@fdmgroup.com", "Y", "Chen", "yc123");		
		user.setFirstName("Y");	
		userService.updateUser(user);	
		
		
		assertEquals(userTest.toString(), userService.getUserByUsername(user.getUsername()).toString());
			
	}
	
	@Test
	public void get_all_usernames_from_user() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");
		user.setRole(role);
		userService.saveUser(user);
		
		User user2 = new User("chen.yifeng@fdmgroup.com", "Chen", "Yifeng", "yc123");	
		user2.setRole(role);
		userService.saveUser(user2);
		
		List<String> usernames = userService.getUsernames();
		List<String> usernamesTest = List.of("yifeng.chen@fdmgroup.com","chen.yifeng@fdmgroup.com");
	
		assertEquals(usernamesTest.size(), usernames.size());
		assertEquals(true, usernames.containsAll(usernamesTest));
		assertEquals(true, usernamesTest.containsAll(usernames));
	}
	
	@Test
	public void get_a_user_return_null() {
		
		assertEquals(null, userService.getUserByUsername("cyf"));
	}
	
	@Test
	public void get_all_users_by_role_id() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");
		user.setRole(role);
		userService.saveUser(user);
		
		User user2 = new User("chen.yifeng@fdmgroup.com", "Chen", "Yifeng", "yc123");	
		user2.setRole(role);
		userService.saveUser(user2);
		
		List<User> users = List.of(user2,user);
		List<User> usersTest = userService.getUsersByRoleId(role.getId());
		
		assertEquals(usersTest.size(),users.size());
		assertEquals(usersTest.get(0).toString(), users.get(0).toString());
		assertEquals(usersTest.get(1).toString(), users.get(1).toString());
	}
	
	//Test GoalService
	
	
	@Test
	public void add_a_goal_to_user() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");	
		user.setRole(role);
		userService.saveUser(user);
		
		GoalType goalType = new GoalType("Interview");		
		goalTypeService.saveGoalType(goalType);
		
		Goal goal = new Goal(Date.valueOf("2023-04-1"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "None");
		goal.setUser(userService.getUserByUsername(user.getUsername()));
		goal.setGoalType(goalType);
		goalService.saveGoal(goal);
			
		List<Goal> goals = goalService.getGoalsByUsername("yifeng.chen@fdmgroup.com");
		
		assertEquals(goal.toString(), goals.get(0).toString());
		assertEquals(1, goals.size());
		assertEquals(user.toString(), goals.get(0).getUser().toString());
				
	}
	
	@Test
	public void update_a_goal() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");	
		user.setRole(role);
		userService.saveUser(user);
		
		GoalType goalType = new GoalType("Interview");		
		goalTypeService.saveGoalType(goalType);
		
		Goal goal = new Goal(Date.valueOf("2023-04-1"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "None");
		goal.setUser(userService.getUserByUsername(user.getUsername()));
		goal.setGoalType(goalType);
		goalService.saveGoal(goal);
		
		goal.setDescription("Not None");
		goalService.updateGoal(goal);
		
		
		assertEquals("Not None", goalService.getGoalById(goal.getId()).getDescription());
			
	}
	
	@Test
	public void get_a_goal_return_null() {
		
		assertEquals(null, goalService.getGoalById(0));
	}
	
	@Test
	public void get_all_goals_by_usernames() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");	
		user.setRole(role);
		userService.saveUser(user);
		
		GoalType goalType = new GoalType("Interview");
		goalTypeService.saveGoalType(goalType);
		
		Goal goal = new Goal(Date.valueOf("2023-04-1"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "None");	
		Goal goal2 = new Goal(Date.valueOf("2023-04-1"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "None");
		
		goal.setUser(userService.getUserByUsername(user.getUsername()));
		goal.setGoalType(goalType);
		goal2.setUser(userService.getUserByUsername(user.getUsername()));
		goal2.setGoalType(goalType);
		goalService.saveGoal(goal);
		goalService.saveGoal(goal2);
		
		List<Goal> goals = List.of(goal,goal2);	
		List<Goal> goalsTest = goalService.getGoalsByUsername(user.getUsername());
		
		assertEquals(goalsTest.size(),goals.size());
		assertEquals(goalsTest.get(0).toString(), goals.get(0).toString());
		assertEquals(goalsTest.get(1).toString(), goals.get(1).toString());

	}
	
	@Test
	public void get_all_goals_by_goal_type_id() {
		
		Role role = new Role("trainee");
		roleService.saveRole(role);
		
		User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "yc123");	
		user.setRole(role);
		userService.saveUser(user);
		
		GoalType goalType = new GoalType("Interview");
		goalTypeService.saveGoalType(goalType);
		
		Goal goal = new Goal(Date.valueOf("2023-04-2"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "None");	
		Goal goal2 = new Goal(Date.valueOf("2023-04-1"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "None");
		
		goal.setUser(userService.getUserByUsername(user.getUsername()));
		goal.setGoalType(goalType);
		goal2.setUser(userService.getUserByUsername(user.getUsername()));
		goal2.setGoalType(goalType);
		goalService.saveGoal(goal);
		goalService.saveGoal(goal2);
		
		List<Goal> goals = List.of(goal,goal2);	
		List<Goal> goalsTest = goalService.getGoalsByGoalTypeId(goalType.getId());
		
		assertEquals(goalsTest.size(),goals.size());
		assertEquals(goalsTest.get(0).toString(), goals.get(0).toString());
		assertEquals(goalsTest.get(1).toString(), goals.get(1).toString());

	}
	
	// test GoalTypeService
	
	@Test
	public void add_a_goal_type() {
		
		GoalType goalType = new GoalType("Interview");		
		goalTypeService.saveGoalType(goalType);
		
		GoalType goalTypeTest = goalTypeService.getGoalTypeById(goalType.getId());
		List<GoalType> goalTypes = goalTypeService.getAllGoalTypes();
		
		assertEquals(goalType.toString(), goalTypeTest.toString());
		assertEquals(1, goalTypes.size());	
		
	}
	
	@Test
	public void add_a_goal_type_and_delete_it() {
		
		GoalType goalType = new GoalType("Interview");		
		goalTypeService.saveGoalType(goalType);
		
		goalTypeService.deleteGoalTypeById(goalType.getId());
		
		List<GoalType> goalTypes = goalTypeService.getAllGoalTypes();
		
		assertEquals(0, goalTypes.size());
			
	}
	
	@Test
	public void update_a_goal_type() {
		
		GoalType goalType = new GoalType("Training");	
		goalTypeService.saveGoalType(goalType);
		
		goalType.setGoalTypeName("Interview");
		goalTypeService.updateGoalType(goalType);
		
		
		assertEquals("Interview", goalTypeService.getGoalTypeById(goalType.getId()).getGoalTypeName());
			
	}
	
	@Test
	public void get_a_goal_type_return_null() {
		
		assertEquals(null, goalTypeService.getGoalTypeById(0));
	}
	
	// test RoleService
	
	@Test
	public void add_a_role() {
		
		Role role = new Role("trainee");		
		roleService.saveRole(role);
		
		Role roleTest = roleService.getRoleById(role.getId());
		List<Role> roles = roleService.getAllRoles();
		
		assertEquals(role.toString(), roleTest.toString());
		assertEquals(1, roles.size());	
		
	}
	
	@Test
	public void add_a_role_and_delete_it() {
		
		Role role = new Role("trainee");		
		roleService.saveRole(role);
		
		roleService.deleteRoleById(role.getId());
		
		List<Role> roles = roleService.getAllRoles();
		
		assertEquals(0, roles.size());
			
	}
	
	@Test
	public void update_a_role() {
		
		Role role = new Role("trainee");	
		roleService.saveRole(role);
		
		role.setRoleName("admin");
		roleService.updateRole(role);
			
		assertEquals("admin", roleService.getRoleById(role.getId()).getRoleName());
			
	}
	
	@Test
	public void get_a_role_return_null() {
		
		assertEquals(null, roleService.getRoleById(0));
	}
}
