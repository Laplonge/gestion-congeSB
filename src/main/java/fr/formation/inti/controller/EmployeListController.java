package fr.formation.inti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Employe;

@RestController
@RequestMapping("/rest")
class EmployeListController {
	
	@Autowired
    private IEmployeDao employeDao;

	@CrossOrigin
    @RequestMapping("/employes")
    public List<Employe> employes() {
        return employeDao.findAll();
    }
	
	@CrossOrigin
    @GetMapping("/string")
    public String restString() {
    	String message = "[{\"message\": \"REST\"}]";
        return message;
    }
}	