package com.fdm.spring.controller;

import java.security.Principal;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class GoalController {

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
	
	@GetMapping("/user/listGoals")
	public String listGoals(Principal principal,Model model) {
	
		List<Goal> goals = goalService.getGoalsByUsername(principal.getName());
		
		model.addAttribute("goals", goals);
		
		return ("listGoals");
	}
	
	@GetMapping("/user/addGoal")
	public String addGoal(Model model)
	{
		
		List<String> options = goalTypeService.getGoalTypeNames();
		List<String> options2 = goalStatusService.getGoalStatusNames();

		model.addAttribute("options", options);
		model.addAttribute("options2", options2);
		model.addAttribute("goal", new Goal());
		
		return ("addGoal_form");
	}
	
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
		
		return("redirect:/user/listGoals");
	}	
	
	@GetMapping("/user/deleteGoal/{id}")
	public String deleteGoal(@PathVariable("id") int id, Model model) {
		
		goalService.deleteGoalById(id);
			
		return ("redirect:/user/listGoals");
	}
	
	@GetMapping("/user/editGoal/{id}")
	public String editGoal(@PathVariable("id") int id, Model model) {
		
		List<String> options = goalTypeService.getGoalTypeNames();
		List<String> options2 = goalStatusService.getGoalStatusNames();

		model.addAttribute("options", options);
		model.addAttribute("options2", options2);
		model.addAttribute("goal", goalService.getGoalById(id));
		
		return("editGoal_form");
	}
	
    @PostMapping("/user/updateGoalObject/{id}")
    public String updateGoalObject
    (
    	 @RequestParam String option2, @ModelAttribute Goal goal,
    	 Model model, Principal principal,@PathVariable("id") int id    
    ) 
    {

    	goal = goalService.getGoalById(id);
		goal.setGoalStatus(goalStatusService.getGoalStatusByGoalStatusName(option2));
		
		if (option2.equals("Done") || option2.equals("Discontinued")) {

			goal.setEndDate(new java.sql.Date(System.currentTimeMillis()));

		}
		goalService.updateGoal(goal);
        return "redirect:/user/listGoals";
    }
}
