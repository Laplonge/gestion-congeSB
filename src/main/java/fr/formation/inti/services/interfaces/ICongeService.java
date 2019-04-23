package fr.formation.inti.services.interfaces;


import java.util.Date;
import java.util.List;

import fr.formation.inti.entities.Conge;

public interface ICongeService extends IGenericService<Conge>{

	List<Conge> getCongeStartDate(Date date);
	List<Conge> getUnavailableDate();

}