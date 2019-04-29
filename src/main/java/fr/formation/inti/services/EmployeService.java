package fr.formation.inti.services;
// Generated 14 avr. 2019 19:56:00 by Hibernate Tools 5.1.10.Final

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Employe;
import fr.formation.inti.services.interfaces.IEmployeService;

@RestController
@RequestMapping("/restEmployeService")
@Service("employeService")
public class EmployeService implements IEmployeService{
	
	@Autowired
	IEmployeDao edao;
	
	public void setEdao(IEmployeDao edao) {
		this.edao = edao;
	}
	
	//la methode de l'interface générique utilise un Integer
	public Optional<Employe> findById(Integer id) {return null;}
	//mais je sais pas récuperer un @pathparam pour un integer donc je parse une string
	@CrossOrigin
    @RequestMapping("/findById/{id}")
	public Optional<Employe> findById(@PathVariable("id") String id) {
		Integer intId = Integer.parseInt(id);
		return edao.findById(intId);
	}

	@CrossOrigin
    @RequestMapping("/getAll")
	public List<Employe> getAll() {
		return edao.findAll();
	}

	@CrossOrigin
    @RequestMapping("/save")
	public void save(@PathParam("employe") Employe employe) {
		edao.save(employe);
	}

	@CrossOrigin
    @RequestMapping("/delete")
	public void delete(@PathParam("employe") Employe persistentObject) {
		edao.delete(persistentObject);
	}

}