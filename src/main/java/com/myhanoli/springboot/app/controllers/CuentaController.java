package com.myhanoli.springboot.app.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myhanoli.springboot.app.models.entity.Cliente;
import com.myhanoli.springboot.app.models.entity.Gasto;
import com.myhanoli.springboot.app.models.service.IClienteService;
import com.myhanoli.springboot.app.models.service.IGastoService;

@Controller
public class CuentaController {

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private IGastoService gastoService;
	
	@GetMapping("/cuenta")
	public String cuenta(
			Model model ) {
		return "cuenta";
	}


	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/altaCuenta")
	public String crear(Map<String, Object> model, Locale locale) {
		
		Gasto gasto = new Gasto();
		model.put("gasto", gasto);
		model.put("titulo", messageSource.getMessage("text.gasto.form.titulo.crear", null, locale));
		return "altaCuenta";
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/altaCuenta", method = RequestMethod.POST)
	public String guardaGasto(@Valid Gasto gasto, BindingResult result, Model model,
			 RedirectAttributes flash, SessionStatus status, Locale locale) {
		System.out.println("result: " + result);
		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.gasto.form.titulo.crear", null, locale));
			return "altaCuenta";
		}
		System.out.println("Antes de mensajeFlash");
		String mensajeFlash = (gasto.getId() != null) ? messageSource.getMessage("text.gasto.flash.editar.success", null, locale) : messageSource.getMessage("text.gasto.flash.crear.success", null, locale);
System.out.println("Despues de mensajeFlash");
		gastoService.save(gasto);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:cuenta";
	}
	
	
}
