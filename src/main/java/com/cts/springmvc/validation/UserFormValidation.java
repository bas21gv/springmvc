package com.cts.springmvc.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cts.springmvc.model.User;

@Component
public class UserFormValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
		
		if(user.getAge()<=21 || user.getAge()>60){
			errors.rejectValue("age", "Valid.userForm.age");
		}
		
		if(user.getSalary()<5000){
			errors.rejectValue("salary", "Valid.userForm.salary");
		}
		
	}

}
