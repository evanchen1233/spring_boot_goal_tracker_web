package com.fdm.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdm.spring.service.GoalService;
import com.fdm.spring.service.GoalStatusService;
import com.fdm.spring.service.GoalTypeService;
import com.fdm.spring.service.RoleService;
import com.fdm.spring.service.StreamService;
import com.fdm.spring.service.UserService;
import com.fdm.spring.model.Role;
import com.fdm.spring.model.Stream;
import com.fdm.spring.model.User;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.GoalStatus;
import com.fdm.spring.model.GoalType;

import java.sql.Date;

@SpringBootTest
class ServiceTest {
	
	@Autowired 
	UserService userService;
	
	@Autowired 
	GoalService goalService;
	
	@Autowired 
	GoalTypeService goalTypeService;
	
	@Autowired 
	GoalStatusService goalStatusService;
	
	@Autowired 
	RoleService roleService;
	
	@Autowired 
	StreamService streamService;
	
	GoalType goalType;
	Role role;
	Stream stream;
	User user;
	Goal goal;
	GoalStatus goalStatus;
	
	@Test
	void contextLoads() {
	}
	
	@BeforeEach
	void setup() {
		
		goalService.deleteAllGoals();
		userService.deleteAllUsers();
		goalTypeService.deleteAllGoalTypes();
		goalStatusService.deleteAllGoalStatus();
		roleService.deleteAllRoles();
		streamService.deleteAllStreams();
		
		goalStatus = new GoalStatus("Done");
		goalType = new GoalType("Interview");
		role = new Role("USER");
		stream = new Stream("BABI");
		
		user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "123");
		user.setRole(role);
		user.setStream(stream);
		
		goal = new Goal(Date.valueOf("2023-04-1"), Date.valueOf("2023-04-11"), Date.valueOf("2023-04-10"), "Something");
		goal.setUser(user);
		goal.setGoalStatus(goalStatus);
		goal.setGoalType(goalType);
	}
	

	
	// test UserService
	
	@Test
	public void add_a_user() {
	
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		User userTest = userService.getUserByUsername(user.getUsername());
		List<User> users = userService.getAllUsers();
		
		assertEquals(user.toString(), userTest.toString());
		assertEquals(1, users.size());	
		
	}
	
	@Test
	public void add_a_user_and_delete_it() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		userService.deleteUserByUsername(user.getUsername());	
		List<User> users = userService.getAllUsers();
		
		assertEquals(0, users.size());
			
	}
	
	@Test
	public void update_a_user() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		user.setFirstName("Y");	
		userService.updateUser(user);	
		
		
		assertEquals("Y", user.getFirstName());
			
	}
	
	@Test
	public void get_all_usernames_from_user() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		User user2 = new User("chen.yifeng@fdmgroup.com", "Chen", "Yifeng", "yc123");	
		user2.setRole(role);
		user2.setStream(stream);
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
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		User user2 = new User("chen.yifeng@fdmgroup.com", "Chen", "Yifeng", "yc123");	
		user2.setRole(role);
		user2.setStream(stream);
		userService.saveUser(user2);
		
		List<User> users = List.of(user2,user);
		List<User> usersTest = userService.getUsersByRoleId(role.getId());
		
		assertEquals(usersTest.size(),users.size());
		assertEquals(usersTest.get(0).toString(), users.get(0).toString());
		assertEquals(usersTest.get(1).toString(), users.get(1).toString());
	}
	
	@Test
	public void get_a_user_by_first_name() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		User usersTest = userService.getUserByName("Yifeng");
		
		assertEquals(usersTest.toString(), user.toString());
	}
	
	@Test
	public void get_users_when_search_term_is_null() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		List<User> users = userService.searchByStreamOrName(null);
		
		assertEquals(userService.getUsersByRoleName("USER").get(0).toString(), users.get(0).toString());
	}
	
	@Test
	public void change_password() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		
		userService.changePassword(user, "321");
		userService.saveUser(user);
	    
		 assertNotEquals("123", user.getPassword());
	}
	//Test GoalService
	
	
	@Test
	public void add_a_goal_to_user() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
				
		List<Goal> goals = goalService.getGoalsByUsername("yifeng.chen@fdmgroup.com");
		
		assertEquals(goal.toString(), goals.get(0).toString());
		assertEquals(1, goals.size());
		assertEquals(user.toString(), goals.get(0).getUser().toString());
				
	}
	
	@Test
	public void update_a_goal() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
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
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		List<Goal> goals = goalService.getGoalsByUsername(user.getUsername());
		
		assertEquals(1,goals.size());
		assertEquals(goal.toString(), goals.get(0).toString());

	}
	
	@Test
	public void get_all_goals_by_goal_type_id() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		List<Goal> goals = goalService.getGoalsByGoalTypeId(goalType.getId());
		
		assertEquals(1,goals.size());
		assertEquals(goal.toString(), goals.get(0).toString());


	}
	
	@Test
	public void count_goals_by_Username_and_goal_status() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		int count = goalService.countByUsernameAndGoalStatus(user.getUsername(), goalStatus.getGoalStatusName());
	
		assertEquals(1,count);
	}
	
	@Test
	public void count_goals_by_Username_and_goal_type() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		int count = goalService.countByUsernameAndGoalType(user.getUsername(), goalType.getGoalTypeName());
	
		assertEquals(1,count);
	}
	
	@Test
	public void count_by_each_goal_status() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		List<Integer> count = goalService.countByGoalStatus();
		
		assertEquals(List.of(0,0,1,0),count);
	}
	
	@Test
	public void count_by_each_goal_type() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		List<Integer> count = goalService.countByGoalType();
		
		assertEquals(List.of(0,0,0,0),count);
	}
	
	@Test
	public void delete_a_goal_by_id() {
		
		roleService.saveRole(role);
		streamService.saveStream(stream);
		userService.saveUser(user);
		goalStatusService.saveGoalStatus(goalStatus);
		goalTypeService.saveGoalType(goalType);
		goalService.saveGoal(goal);
		
		goalService.deleteGoalById(goal.getId());
		List<Goal> goals = goalService.getAllGoals();
		
		assertEquals(0, goals.size());
		
		
	}
	// test GoalTypeService
	
	@Test
	public void add_a_goal_type() {
		
		goalTypeService.saveGoalType(goalType);
		
		GoalType goalTypeTest = goalTypeService.getGoalTypeById(goalType.getId());
		List<GoalType> goalTypes = goalTypeService.getAllGoalTypes();
		
		assertEquals(goalType.toString(), goalTypeTest.toString());
		assertEquals(1, goalTypes.size());	
		
	}
	
	@Test
	public void add_a_goal_type_and_delete_it() {
		
		goalTypeService.saveGoalType(goalType);
		
		goalTypeService.deleteGoalTypeById(goalType.getId());	
		List<GoalType> goalTypes = goalTypeService.getAllGoalTypes();
		
		assertEquals(0, goalTypes.size());
			
	}
	
	@Test
	public void update_a_goal_type() {
		
		goalTypeService.saveGoalType(goalType);
		
		goalType.setGoalTypeName("Interview");
		goalTypeService.updateGoalType(goalType);
			
		assertEquals("Interview", goalTypeService.getGoalTypeById(goalType.getId()).getGoalTypeName());
			
	}
	
	@Test
	public void get_a_goal_type_return_null() {
		
		assertEquals(null, goalTypeService.getGoalTypeById(0));
	}
	
	@Test
	public void get_a_goal_type_by_goal_type_name(){
		
		goalTypeService.saveGoalType(goalType);
		
		GoalType goalTypeTest = goalTypeService.getGoalTypeByGoalTypeName("Interview");
		
		assertEquals(goalType.toString(), goalTypeTest.toString());
	}
	
	@Test
	public void get_all_goal_type_names(){
		
		goalTypeService.saveGoalType(goalType);	
		GoalType goalType2 = new GoalType("Training");	
		goalTypeService.saveGoalType(goalType2);
		
		List<String> goalTypes = goalTypeService.getGoalTypeNames();
		List<String> goalTypesTest = List.of("Training","Interview");
	
		assertEquals(goalTypes.size(), goalTypesTest.size());
		assertEquals(true, goalTypes.containsAll(goalTypes));
		assertEquals(true, goalTypesTest.containsAll(goalTypes));
		
	}
	
	// test RoleService
	
	@Test
	public void add_a_role() {
				
		roleService.saveRole(role);
		
		Role roleTest = roleService.getRoleById(role.getId());
		List<Role> roles = roleService.getAllRoles();
		
		assertEquals(role.toString(), roleTest.toString());
		assertEquals(1, roles.size());	
		
	}
	
	@Test
	public void add_a_role_and_delete_it() {
			
		roleService.saveRole(role);
		
		roleService.deleteRoleById(role.getId());		
		List<Role> roles = roleService.getAllRoles();
		
		assertEquals(0, roles.size());
			
	}
	
	@Test
	public void update_a_role() {
		
		roleService.saveRole(role);
		
		role.setRoleName("ADMIN");
		roleService.updateRole(role);
			
		assertEquals("ADMIN", roleService.getRoleById(role.getId()).getRoleName());
			
	}
	
	@Test
	public void get_a_role_return_null() {
		
		assertEquals(null, roleService.getRoleById(0));
	}
	
	// test GoalStatusService
	@Test
	public void add_a_goal_status() {
				
		goalStatusService.saveGoalStatus(goalStatus);
		
		GoalStatus goalStatusTest = goalStatusService.getGoalStatusById(goalStatus.getId());
		
		assertEquals(goalStatus.toString(),goalStatusTest.toString());
		
	}
	
	@Test
	public void add_a_goal_status_and_delete_it() {
			
		goalStatusService.saveGoalStatus(goalStatus);	
			
		goalStatusService.deleteGoalStatusById(goalStatus.getId());		
		List<GoalStatus> goalStatues = goalStatusService.getAllGoalStatus();
		
		assertEquals(0, goalStatues.size());
	}
	
	@Test
	public void get_a_goal_status_return_null() {
		
		assertEquals(null, goalStatusService.getGoalStatusById(0));
	}
	
	@Test
	public void update_a_goal_status() {
		
		goalStatusService.saveGoalStatus(goalStatus);
		
		goalStatus.setGoalStatusName("Done");
		goalStatusService.updateGoalStatus(goalStatus);
			
		assertEquals("Done", goalStatusService.getGoalStatusById(goalStatus.getId()).getGoalStatusName());			
	}
	
	@Test
	public void get_a_goal_status_by_goal_status_name(){
		
		goalStatusService.saveGoalStatus(goalStatus);
		
		GoalStatus goalStatusTest = goalStatusService.getGoalStatusByGoalStatusName("Done");
		
		assertEquals(goalStatus.toString(), goalStatusTest.toString());
	}
	
	@Test
	public void get_all_goal_status_names(){
		
		goalStatusService.saveGoalStatus(goalStatus);	
		GoalStatus goalStatus2 = new GoalStatus("Planning");	
		goalStatusService.saveGoalStatus(goalStatus2);
		
		List<String> goalStatuses = goalStatusService.getGoalStatusNames();
		List<String> goalStatusesTest = List.of("Done","Planning");
	
		assertEquals(goalStatuses.size(), goalStatusesTest.size());
		assertEquals(true, goalStatuses.containsAll(goalStatuses));
		assertEquals(true, goalStatusesTest.containsAll(goalStatuses));
		
	}
	
	// test StreamService
		@Test
		public void add_a_stream() {
					
			streamService.saveStream(stream);
			
			Stream streamTest = streamService.getStreamById(stream.getId());
			
			assertEquals(stream.toString(),streamTest.toString());
			
		}
		
		@Test
		public void add_a_stream_and_delete_it() {
				
			streamService.saveStream(stream);	
				
			streamService.deleteStreamById(stream.getId());		
			List<Stream> goalStatues = streamService.getAllStreams();
			
			assertEquals(0, goalStatues.size());
		}
		
		@Test
		public void get_a_stream_return_null() {
			
			assertEquals(null, streamService.getStreamById(0));
		}
		
		@Test
		public void update_a_stream() {
			
			streamService.saveStream(stream);
			
			stream.setStreamName("TA");
			streamService.updateStream(stream);
				
			assertEquals("TA", streamService.getStreamById(stream.getId()).getStreamName());			
		}
		
		@Test
		public void get_a_stream_by_stream_name(){
			
			streamService.saveStream(stream);
			
			Stream streamTest = streamService.getStreamByStreamName("BABI");
			
			assertEquals(stream.toString(), streamTest.toString());
		}
		
		@Test
		public void get_all_stream_names(){
			
			streamService.saveStream(stream);	
			Stream stream2 = new Stream("TA");	
			streamService.saveStream(stream2);
			
			List<String> streames = streamService.getStreamNames();
			List<String> streamesTest = List.of("BABI","TA");
		
			assertEquals(streames.size(), streamesTest.size());
			assertEquals(true, streames.containsAll(streames));
			assertEquals(true, streamesTest.containsAll(streames));
			
		}
}
