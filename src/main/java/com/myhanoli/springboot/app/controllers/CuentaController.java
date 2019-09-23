package com.myhanoli.springboot.app.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.myhanoli.springboot.app.models.entity.Gasto;
import com.myhanoli.springboot.app.models.service.IGastoService;
import com.myhanoli.springboot.app.util.paginator.PageRender;

@Controller
public class CuentaController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private IGastoService gastoService;


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

		String mensajeFlash = (gasto.getId() != null) ? messageSource.getMessage("text.gasto.flash.editar.success", null, locale) : messageSource.getMessage("text.gasto.flash.crear.success", null, locale);

		gastoService.save(gasto);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:cuenta";
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/verGasto/{id}")
	public String verGasto(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {
      System.out.println("llegue al controlador ver");
		List<Gasto> gasto = gastoService.findAll();
		if (gasto.size() > 0) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/cuenta";
		}

		model.put("gasto", gasto);
		model.put("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ").concat(gasto.get(0).getNombre()));
		return "ver";
	}
	
	
	@RequestMapping(value = {"/cuenta"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			HttpServletRequest request,
			Locale locale) {
				
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Gasto> gastos = gastoService.findAll(pageRequest);
		PageRender<Gasto> pageRender = new PageRender<Gasto>("/cuenta", gastos);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("gastos", gastos);
		model.addAttribute("page", pageRender);
		return "cuenta";
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/altaCuenta/{id}")
	public String editarGasto(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale) {

		Gasto gasto = null;

		if (id > 0) {
			
			gasto = gastoService.findOne(id);
			if (gasto == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
				return "redirect:/cuenta";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
			return "redirect:/cuenta";
		}
		model.put("gasto", gasto);
		model.put("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
		return "altaCuenta";
	}
	
	
	
}
