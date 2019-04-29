package fr.formation.inti.services;
// Generated 14 avr. 2019 19:56:00 by Hibernate Tools 5.1.10.Final

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.ICongeDao;
import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Conge;
import fr.formation.inti.entities.Employe;
import fr.formation.inti.services.interfaces.ICongeService;

@Service("congeService")
public class CongeService implements ICongeService {

	@Autowired
	ICongeDao congeDao;

	@Autowired
	IEmployeDao employeDao;

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

	public List<Conge> getUnavailableDate(Date date) {
		return congeDao.getUnavailableDate(date);
	}

	public List<Conge> getCongeByIdEmploye(Integer id) {
		return congeDao.getCongeByIdEmploye(id);
	}

	/* Cette fonction retourne le jour de vacances de la semaine */
	public Integer calculDureeVacances(Conge conge) {
		int jourdevac = 0;

		for (LocalDate datetest = ((java.sql.Date) conge.getDateDebut()).toLocalDate(); datetest.isBefore(
				((java.sql.Date) conge.getDateFin()).toLocalDate().plusDays(1)); datetest = datetest.plusDays(1)) {
			if (datetest.getDayOfWeek() == DayOfWeek.SATURDAY || datetest.getDayOfWeek() == DayOfWeek.SUNDAY) {
			} else {
				jourdevac++;
			}
		}
		return jourdevac;
	}

	public Integer calculDureeVacances2(Date debut, Date fin) {
		int jourdevac = 0;

		for (LocalDate datetest = ((java.sql.Date) debut).toLocalDate(); datetest
				.isBefore(((java.sql.Date) fin).toLocalDate().plusDays(1)); datetest = datetest.plusDays(1)) {
			if (datetest.getDayOfWeek() == DayOfWeek.SATURDAY || datetest.getDayOfWeek() == DayOfWeek.SUNDAY) {
			} else {
				jourdevac++;
			}
		}
		return jourdevac;
	}

	/*
	 * Test si la requete est valide ou non
	 * 
	 * Reste à faire implémenter un trigger qui commit la base employe et la base
	 * conge
	 */
	public String validationDeLaPeriode(Conge conge) {
		String html = "";
		if (conge.getDureeJours() > conge.getEmploye().getJoursCongeRestant()) {
			html += conge.getEmploye().getNom() + " " + "Nombre de jours insufissants demande refusée" + "<br>";

		} else {
			html += conge.getEmploye().getNom() + " " + "Demande acceptée, Nombre de jour utilisés: "
					+ conge.getDureeJours() + "<br>";
			// conge.getEmploye().ge
		}
		return html;
	}

	/*
	 * Test la validité de la requete avant de pouvoir continuer dans la demande de
	 * requete
	 */
	public String TestDeLaValiditeDeLaRequete(String debut, String fin) throws ParseException {
		String html = "";
		boolean ReqOk = true;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = dateformat.parse(debut);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

		date = dateformat.parse(fin);
		java.sql.Date sqlFinDate = new java.sql.Date(date.getTime());

		Date current = new Date();
		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());

		for (Conge cong : getUnavailableDate(sqlcurrent)) {
			html += "validation2";
			// check if employee holidays is okay with the agree database request
			if ((sqlStartDate.after(cong.getDateDebut()) && sqlStartDate.before(cong.getDateFin()))
					|| (sqlFinDate.after(cong.getDateDebut()) && sqlFinDate.before(cong.getDateFin()))
					|| (sqlStartDate.equals(cong.getDateDebut()) || sqlFinDate.equals(cong.getDateFin()))) {
				html += ("Demande refuse1");
				AlgoDePropo(sqlStartDate, sqlFinDate);
				ReqOk = false;
				break;
			}
			// check if agree database request is okay with the employee holidays
			if ((cong.getDateDebut().after(sqlStartDate) && cong.getDateDebut().before(sqlFinDate))
					|| (cong.getDateFin().after(sqlStartDate) && cong.getDateFin().before(sqlFinDate))) {
				html += ("Demande refuse2");
				AlgoDePropo(sqlStartDate, sqlFinDate);
				ReqOk = false;
				break;
			}
		}

		if (ReqOk == true) {
			// Request okay, creation of a new request with on going statut"
			// Actually just miss the trigger to commit employee and conge database
			// Id employee will be set with the employee session
			html += "Demande acceptee";
			Conge demande = new Conge();
			Optional<Employe> emp = employeDao.findById(1);
			demande.setEmploye(emp.get());
			emp.get().setJoursCongeRestant(emp.get().getJoursCongeRestant()-demande.getDureeJours());
			demande.setDateDebut(sqlStartDate);
			demande.setDateFin(sqlFinDate);
			demande.setDureeJours(calculDureeVacances2(sqlStartDate, sqlFinDate));
			demande.setDateDemande(sqlcurrent);
			demande.setStatutDeLaDemande("en cours");
			try {
				save(demande);
				employeDao.save(emp.get());
			} catch (Exception e) {
			}
		}
		return html;
	}

	public String AlgoDePropo(Date debut, Date fin) throws ParseException {

		String html = "";
		Date current = new Date();
		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());
		java.sql.Date sqldebut = new java.sql.Date(debut.getTime());
		java.sql.Date sqlfin = new java.sql.Date(fin.getTime());
		html += "test test2";
		// Retourne la liste de toutes les dates déjà "acceptee" dans la base de donnée
		List<Conge> Unav = getUnavailableDate(sqlcurrent);
		html += "test test3";
		// Test si la date peut inserer dans un interval entre les dates en bases de
		// données
		// Si c'est possible alors retourner une date de debut avec comme paramettre
		// date de debut de la periode precedente +1

		// Si non alors retourner une date de début avec comme paramettre
		// date de debut de la dernière periode +1

		html += "test test4";
		for (int i = 0; i < Unav.size() - 1; i++) {
			// Dans cette boucle, on test chaque interval contenu dans la liste Unav
			// pour cela, on prend comme valeur de début, la valeur de la date de fin de la
			// requete actuelle
			// +1 et comme date de fin la date de début de la requette suivante
			html += "test test";
			// Calcul des periodes entre les différentes requetes validées
			int interval = (int) ChronoUnit.DAYS.between(
					((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(1),
					((java.sql.Date) Unav.get(i + 1).getDateDebut()).toLocalDate());
			// Calcul de la periode de vacances demandée
			int demvac = (int) ChronoUnit.DAYS.between((sqldebut.toLocalDate()), (sqlfin.toLocalDate()).plusDays(1));

			// Test si la periode de la demande est compatible avec la duree l'interval
			// entre les requetes
			// Retourne une requete avec une proposition de date dans le premier interval
			// possible
			if (demvac <= interval) {
				LocalDate datedebutprop = ((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(1);
				LocalDate datefinprop = ((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(demvac);
				// test si le premier jour de la demande de vacance est un dimanche ou un samedi
				// Si c'est le cas on decale la date jusqu'a lundi
//					while ((datedebutprop.getDayOfWeek() == DayOfWeek.SATURDAY
//							|| datedebutprop.getDayOfWeek() == DayOfWeek.SUNDAY)) {
//						datedebutprop.plusDays(1);
//					}
				TestDeLaValiditeDeLaRequete(datedebutprop.toString(), datefinprop.toString());
				break;
			}

			else if (i == Unav.size() - 2) {
				LocalDate datedebutprop = ((java.sql.Date) Unav.get(Unav.size() - 1).getDateFin()).toLocalDate()
						.plusDays(1);

//					while ((datedebutprop.getDayOfWeek() == DayOfWeek.SATURDAY
//							|| datedebutprop.getDayOfWeek() == DayOfWeek.SUNDAY)) {
//						datedebutprop.plusDays(1);
//					}

				Conge demande = new Conge();
				demande.setEmploye(employeDao.findById(1).get());
				demande.setDateDebut(java.sql.Date.valueOf(datedebutprop));
				LocalDate datefinprop = ((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(demvac);
				demande.setDateFin(java.sql.Date.valueOf(datedebutprop));
				demande.setDureeJours(
						calculDureeVacances2(java.sql.Date.valueOf(datedebutprop), java.sql.Date.valueOf(datefinprop)));
				demande.setDateDemande(sqlcurrent);
				demande.setStatutDeLaDemande("Proposition");
				save(demande);
				break;
			}
		}
		// Test si on arrive à la dernière periode disponible
		// Puis retourne une requete avec un interval identique juste après la dernière
		// requête

		return html;
	}

	
	public String TestDeConflitDemandeEnCours() {
		Date current = new Date();
		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());
		List<Conge> Conflit = getCongeStartDate(sqlcurrent);
		int start = 1;
		String html = "";
		
		for(Conge TestConf : Conflit) {
			html += "Id Conge : " + TestConf.getIdConge() + "<br>" ;
			for(int i=start; i<Conflit.size(); i++) {
				
				if ((TestConf.getDateDebut().after(Conflit.get(i).getDateDebut()) && TestConf.getDateDebut().before(Conflit.get(i).getDateFin()))
						|| (TestConf.getDateFin().after(Conflit.get(i).getDateDebut()) && TestConf.getDateFin().before(Conflit.get(i).getDateFin()))
						|| (TestConf.getDateDebut().equals(Conflit.get(i).getDateDebut()) || TestConf.getDateFin().equals(Conflit.get(i).getDateFin()))) {
					html +="----------- Conflit avec la requete : " + Conflit.get(i).getIdConge() + "<br>";
				}
				// check if agree database request is okay with the employee holidays
				else if ((Conflit.get(i).getDateDebut().after(TestConf.getDateDebut()) && Conflit.get(i).getDateDebut().before(TestConf.getDateFin()))
						|| (Conflit.get(i).getDateFin().after(TestConf.getDateDebut()) && Conflit.get(i).getDateFin().before(TestConf.getDateFin()))) {
					html += "Conflit avec la requete : " + Conflit.get(i).getIdConge() + "<br>";
				}	
				
			}
		start++;	
		}
	return html;
	}
}
