package com.myhanoli.springboot.app.controllers;

import java.security.Principal;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myhanoli.springboot.app.models.entity.Cliente;

@Controller
public class HomeController {

	@Autowired
    private MessageSource messageSource;

	
	@GetMapping("/home")
	public String home(		
			Model model, Principal principal) {
		return "home";
	}
	
}
