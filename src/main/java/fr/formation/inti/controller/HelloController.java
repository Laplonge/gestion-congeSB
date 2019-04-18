package fr.formation.inti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Employe;
import fr.formation.inti.services.interfaces.IEmployeService;

@Controller
public class HelloController {
	
	@ResponseBody
	@RequestMapping("/springboot")
	public String hello() {
		String html = "<h1>Spring Boot c'est cool</h1>";
		return html;
	}
	
	@Autowired
	IEmployeService es;
	
	@ResponseBody
	@RequestMapping("/employes")
	public String employes() {
		String html = "";
		for( Employe e : es.getAll()) {
			html += e + "<hr>";
		}
		return html;
	}
	
	@ResponseBody
	@RequestMapping("/employebyid")
	public String employeById(@RequestParam("id") Integer id) {

		return " "+es.findById(id);
	}
	
}
