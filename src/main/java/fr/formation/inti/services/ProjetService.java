package fr.formation.inti.services;
// Generated 14 avr. 2019 19:56:00 by Hibernate Tools 5.1.10.Final

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.IProjetDao;
import fr.formation.inti.entities.Projet;
import fr.formation.inti.services.interfaces.IProjetService;

@Service("projetService")
public class ProjetService implements IProjetService{
	
	@Autowired
	IProjetDao projetDao;
	
	public void setEdao(IProjetDao projetDao) {
		this.projetDao = projetDao;
	}

	public Optional<Projet> findById(Integer id) {
		return projetDao.findById(id);
	}

	public List<Projet> getAll() {
		return projetDao.findAll();
	}

	public void save(Projet newInstance) {
		projetDao.save(newInstance);
	}

	public void delete(Projet persistentObject) {
		projetDao.delete(persistentObject);
	}
}