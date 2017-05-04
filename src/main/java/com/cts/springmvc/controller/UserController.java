package com.cts.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.springmvc.model.User;
import com.cts.springmvc.service.UserService;
import com.cts.springmvc.validation.UserFormValidation;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserFormValidation userFormValidation;
	
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder){
		dataBinder.setValidator(userFormValidation);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model){
		return "redirect:/users";
	}
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String showAllUsers(Model model){
		//System.out.println(userService.getAll());
		model.addAttribute("users", userService.getAll());
		return "users/list";
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public String showUser(@PathVariable("id") int id, Model model){
		User user = userService.getUser(id);
		if(user == null){
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("user", user);
		return "users/show";
	}
	
	@RequestMapping(value="/users/add", method=RequestMethod.GET)
	public String showAddUserForm(Model model){
		model.addAttribute("userForm", new User());
		return "users/userform";
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
			BindingResult result, Model model,final RedirectAttributes redirectAttributes){
		
		if(result.hasErrors()){
			return "users/userform";
		}else{
			redirectAttributes.addFlashAttribute("css","success");
			if(user.isNew()){
				redirectAttributes.addFlashAttribute("msg", "User added successfully");
			}else{
				redirectAttributes.addFlashAttribute("msg", "User updated successfully");
			}
			userService.saveOrUpdate(user);
			return "redirect:/users/"+user.getId();
		}
	}
	
	@RequestMapping(value="/users/{id}/update", method=RequestMethod.GET)
	public String showUpdateUserForm(@PathVariable("id") int id, Model model){
		User user = userService.getUser(id);
		model.addAttribute("userForm", user);
		return "users/userform";
	}
	
	@RequestMapping(value="/users/{id}/delete", method=RequestMethod.GET)
	public String deleteUser(@PathVariable("id") int id, 
			final RedirectAttributes redirectAttributes){
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User deleted successfully");
		return "redirect:/users";
	}
}
