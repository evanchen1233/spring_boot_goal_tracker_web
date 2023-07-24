package com.fdm.spring.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.User;
import com.fdm.spring.service.GoalService;
import com.fdm.spring.service.UserService;

@Controller
public class AdminController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserService userService;
	
	@Autowired
	GoalService goalService;
	
	/**
	 * Map the HTTP GET request of "/admin/listTrainees"
	 * This method passed aggregated data for table and charts for Admin overview page
	 * It displays filtered information if Search Term is founded.
	 * @param model
	 * @param keyword Search Term
	 * @return listTrainees.html
	 */
	@GetMapping("/admin/listTrainees")
	public String listTrainees(Model model, @Param("keyword") String keyword) {

		List<User> users = userService.searchByStreamOrName(keyword);
	
		List<Integer> planningCounts = new ArrayList<>();
		List<Integer> progressCounts = new ArrayList<>();
		List<Integer> completionCounts = new ArrayList<>();
		List<Integer> discontinuedCounts = new ArrayList<>();
			
		List<Integer> learningPathCounts = new ArrayList<>();
		List<Integer> trainingCounts = new ArrayList<>();
		List<Integer> interviewCounts = new ArrayList<>();
		List<Integer> selfStudyCounts = new ArrayList<>();
		
		// aggregated data for table
		for (User user : users) {
			planningCounts.add(goalService.countByUsernameAndGoalStatus(user.getUsername(),"Planning"));
			progressCounts.add(goalService.countByUsernameAndGoalStatus(user.getUsername(),"In Progress"));
			completionCounts.add(goalService.countByUsernameAndGoalStatus(user.getUsername(),"Done"));
			discontinuedCounts.add(goalService.countByUsernameAndGoalStatus(user.getUsername(),"Discontinued"));
			
			learningPathCounts.add(goalService.countByUsernameAndGoalType(user.getUsername(),"Learning Path"));
			trainingCounts.add(goalService.countByUsernameAndGoalType(user.getUsername(),"Training"));
			interviewCounts.add(goalService.countByUsernameAndGoalType(user.getUsername(),"Interview"));
			selfStudyCounts.add(goalService.countByUsernameAndGoalType(user.getUsername(),"Self-Study"));
		}
			
		model.addAttribute("users", users);
		model.addAttribute("planningCounts", planningCounts);
		model.addAttribute("progressCounts", progressCounts);
		model.addAttribute("completionCounts", completionCounts);
		model.addAttribute("discontinuedCounts", discontinuedCounts);
		
		// data for donut chart
		List<Integer> chartData1 = new ArrayList<>();
		chartData1.add(sumofList(planningCounts));
		chartData1.add(sumofList(progressCounts));
		chartData1.add(sumofList(completionCounts));
		chartData1.add(sumofList(discontinuedCounts));
		
		// data for bar chart
		List<Integer> chartData2 = new ArrayList<>();
		chartData2.add(sumofList(learningPathCounts));
		chartData2.add(sumofList(trainingCounts));
		chartData2.add(sumofList(interviewCounts));
		chartData2.add(sumofList(selfStudyCounts));
		
		model.addAttribute("totalGoalStatusCounts", chartData1);
		model.addAttribute("totalGoalTypeCounts", chartData2);
		model.addAttribute("keyword", keyword);
		
		return ("listTrainees");
	}
	
	/**
	 * Map the HTTP Get request of "/admin/userDetail/{id}"
	 * This displays selected user profile
	 * @param model
	 * @param keyword Search Term
	 * @param id variable path id refer to user's name
	 * @return userDetails.html
	 */
	@GetMapping("/admin/userDetail/{id}")
	public String listGoals(Model model, @Param("keyword") String keyword, @PathVariable("id") String id) {
		
		List<Goal> goals = new ArrayList<>();

		User trainee = new User();
		trainee = userService.getUserByName(id);

		if (trainee != null) {
			
			goals = goalService.filteredFindAll(keyword, trainee.getUsername());

			Collections.sort(goals, new GoalComparator());
			
			model.addAttribute("goals", goals);
			model.addAttribute("user", trainee);
			model.addAttribute("keyword", keyword);

			return ("userdetails");
		}
		return ("listTrainees");

	}
	
	/**
	 * This method returns the sum of a integer list
	 * @param list A list of integer
	 * @return Sum of all integer
	 */
	public int sumofList(List<Integer> list) {
		
		int sum = 0;
		
		for(int i = 0; i < list.size(); i++)		
		    sum += list.get(i);
		
		return sum;
	}

	
}
