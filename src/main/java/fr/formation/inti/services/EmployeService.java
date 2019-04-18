package fr.formation.inti.services;
// Generated 14 avr. 2019 19:56:00 by Hibernate Tools 5.1.10.Final

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Employe;
import fr.formation.inti.services.interfaces.IEmployeService;

@Service("employeService")
public class EmployeService implements IEmployeService{
	
	@Autowired
	IEmployeDao edao;
	
	public void setEdao(IEmployeDao edao) {
		this.edao = edao;
	}

	public Optional<Employe> findById(Integer id) {
		return edao.findById(id);
	}

	public List<Employe> getAll() {
		return edao.findAll();
	}

	public void save(Employe newInstance) {
		edao.save(newInstance);
	}

	public void delete(Employe persistentObject) {
		edao.delete(persistentObject);
	}
}