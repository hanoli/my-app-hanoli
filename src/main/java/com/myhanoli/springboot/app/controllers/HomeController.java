package com.myhanoli.springboot.app.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value = {"/home","/"})
	public String home(		
			Model model, Principal principal) {
		return "home";
	}
	
}
