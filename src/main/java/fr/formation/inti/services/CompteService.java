package fr.formation.inti.services;
// Generated 14 avr. 2019 19:56:00 by Hibernate Tools 5.1.10.Final

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.ICompteDao;
import fr.formation.inti.entities.Compte;
import fr.formation.inti.services.interfaces.ICompteService;

@Service("compteService")
public class CompteService implements ICompteService{
	
	@Autowired
	ICompteDao compteDao;
	
	public void setEdao(ICompteDao compteDao) {
		this.compteDao = compteDao;
	}

	public Optional<Compte> findById(Integer id) {
		return compteDao.findById(id);
	}

	public List<Compte> getAll() {
		return compteDao.findAll();
	}

	public void save(Compte newInstance) {
		compteDao.save(newInstance);
	}

	public void delete(Compte persistentObject) {
		compteDao.delete(persistentObject);
	}
}