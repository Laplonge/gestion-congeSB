package fr.formation.inti.services.interfaces;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import fr.formation.inti.entities.Conge;

public interface ICongeService extends IGenericService<Conge>{

	List<Conge> getCongeStartDate(Date date);
	List<Conge> getUnavailableDate(Date date);
	List<Conge> getCongeByIdEmploye(Integer id);
	Integer calculDureeVacances(Conge conge);
	String validationDeLaPeriode(Conge conge);
	String TestDeLaValiditeDeLaRequete(String debut, String fin) throws ParseException;
	String AlgoDePropo(Date debut, Date fin) throws ParseException;
	String TestDeConflitDemandeEnCours();
}