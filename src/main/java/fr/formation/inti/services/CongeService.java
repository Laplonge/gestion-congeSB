package fr.formation.inti.services;
// Generated 14 avr. 2019 19:56:00 by Hibernate Tools 5.1.10.Final

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.ICongeDao;
import fr.formation.inti.entities.Conge;
import fr.formation.inti.services.interfaces.ICongeService;

@Service("congeService")
public class CongeService implements ICongeService{
	
	@Autowired
	ICongeDao congeDao;
	
	public void setEdao(ICongeDao congeDao) {
		this.congeDao = congeDao;
	}

	public Optional<Conge> findById(Integer id) {
		return congeDao.findById(id);
	}

	public List<Conge> getAll() {
		return congeDao.findAll();
	}

	public void save(Conge newInstance) {
		congeDao.save(newInstance);
	}

	public void delete(Conge persistentObject) {
		congeDao.delete(persistentObject);
	}
	
	public List<Conge> getCongeStartDate(Date date) {
		return congeDao.getCongeStartDate(date);
	}
	
	public List<Conge> getUnavailableDate() {
		return congeDao.getUnavailableDate();
	}
}