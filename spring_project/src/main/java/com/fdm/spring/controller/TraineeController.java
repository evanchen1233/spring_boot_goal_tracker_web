package com.fdm.spring.controller;

import java.security.Principal;

import java.util.Collections;
import java.util.List;
import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.spring.model.Goal;
import com.fdm.spring.service.GoalService;
import com.fdm.spring.service.GoalStatusService;
import com.fdm.spring.service.GoalTypeService;
import com.fdm.spring.service.UserService;


@Controller
public class TraineeController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserService userService;
	
	@Autowired
	GoalService goalService;
	
	@Autowired
	GoalTypeService goalTypeService;
	
	@Autowired
	GoalStatusService goalStatusService;
	
	/**
	 * Map the HTTP GET request of "/user/listGoals"
	 * A page contains a table of all goals belongs to the currently authenticated user
	 * @param principal currently authenticated user
	 * @param model
	 * @param keyword Search Term
	 * @return listGoals.html
	 */
	@GetMapping("/user/listGoals")
	public String listGoals(Principal principal,Model model, @Param("keyword") String keyword) {
	
		List<Goal> goals = goalService.filteredFindAll(keyword, principal.getName());
	
		Collections.sort(goals, new GoalComparator());
		
		model.addAttribute("goals", goals);
		model.addAttribute("user", userService.getUserByUsername(principal.getName()));
		model.addAttribute("keyword", keyword);
		
		return ("listGoals");
	}
	
	/**
	 * Map the HTTP GET request of "/user/addGoal"
	 * It brings the user to a page for adding goal
	 * @param model
	 * @return addGoal_form.html
	 */
	@GetMapping("/user/addGoal")
	public String addGoal(Model model)
	{
		
		// populate two SELECT input (goal type and status two )
		List<String> options = goalTypeService.getGoalTypeNames();
		List<String> options2 = goalStatusService.getGoalStatusNames();

		model.addAttribute("options", options);
		model.addAttribute("options2", options2);
		model.addAttribute("goal", new Goal());
		
		return ("addGoal_form");
	}
	
	/**
	 * Map the HTTP POST request of "/user/addGoalObject"
	 * It adds a goal to currently authenticated user.
	 * @param option selected goal type
	 * @param option2 selected goal status
	 * @param goal
	 * @param model
	 * @param principal currently authenticated user
	 * @return listGoals.html
	 */
	@PostMapping("/user/addGoalObject")
	public String addGoalObject
	(
		@RequestParam String option, @RequestParam String option2, @ModelAttribute Goal goal, 
		Model model, Principal principal
	)
	{
		goal.setGoalStatus(goalStatusService.getGoalStatusByGoalStatusName(option2));
		goal.setGoalType(goalTypeService.getGoalTypeByGoalTypeName(option));
		goal.setUser(userService.getUserByUsername(principal.getName()));
		goalService.saveGoal(goal);		
		
		logger.info("New goal added");
		return("redirect:/user/listGoals");
	}	
	
	/**
	 * This method deletes a goal
	 * @param id path variable refer to goal id
	 * @param model
	 * @return listGoals.html
	 */
	@GetMapping("/user/deleteGoal/{id}")
	public String deleteGoal(@PathVariable("id") int id, Model model) {
		
		goalService.deleteGoalById(id);
		
		logger.info("Goal deleted");
		return ("redirect:/user/listGoals");
	}
	
	/**
	 * Map the HTTP GET request of "/user/editGoal/{id}"
	 * This brings user to a page for editing a goal 
	 * @param id  path variable refer to goal id
	 * @param model
	 * @return editGoal_form.html
	 */
	@GetMapping("/user/editGoal/{id}")
	public String editGoal(@PathVariable("id") int id, Model model) {
		
		// populate SELECT input for goal type and goal status
		List<String> options = goalTypeService.getGoalTypeNames();
		List<String> options2 = goalStatusService.getGoalStatusNames();

		model.addAttribute("options", options);
		model.addAttribute("options2", options2);
		model.addAttribute("goal", goalService.getGoalById(id));
		
		return("editGoal_form");
	}
	
	/**
	 * Map the HTTP POST request of "/user/updateGoalObject/{id}"
	 * This method updates a goal and adds today's date as the goal end date, 
	 * if goal status is changed to "Done" or "Discontinued"
	 * @param option2 selected goal status
	 * @param goal
	 * @param model
	 * @param principal currently authenticated user
	 * @param id path variable refer to goal id
	 * @return listGoals.html
	 */
    @PostMapping("/user/updateGoalObject/{id}")
    public String updateGoalObject
    (
    	 @RequestParam String option2, @ModelAttribute Goal goal,
    	 Model model, Principal principal,@PathVariable("id") int id    
    ) 
    {

    	goal.setGoalType(goalService.getGoalById(id).getGoalType());
    	goal.setUser(userService.getUserByUsername(principal.getName()));
		goal.setGoalStatus(goalStatusService.getGoalStatusByGoalStatusName(option2));
		
		//set end date if status is changed to "Done" or "Discontinued"
		if (option2.equals("Done") || option2.equals("Discontinued")) {

			goal.setEndDate(new Date(System.currentTimeMillis()));

		}
		goalService.updateGoal(goal);
		logger.info("Goal updated");
        return "redirect:/user/listGoals";
    }
}

/*
 * Sorts Goal by start date in reverse order
 */
class GoalComparator implements java.util.Comparator<Goal> {
    @Override
    public int compare(Goal a, Goal b) {
        return b.getStartDate().compareTo(a.getStartDate());
    }
}