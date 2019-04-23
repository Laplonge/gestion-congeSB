package fr.formation.inti.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.formation.inti.entities.Conge;
import fr.formation.inti.entities.Employe;
import fr.formation.inti.services.interfaces.ICongeService;
import fr.formation.inti.services.interfaces.IEmployeService;

@Controller
public class TestConflit  {
	private static final Log log = LogFactory.getLog(TestConflit.class);
	@Autowired
	IEmployeService es;

	@Autowired
	ICongeService cs;

	@ResponseBody
	@RequestMapping("/testConflit")
	public String testConflit() throws ParseException {
		String html = "";
//		for (Conge c : cs.getAll()) {
//			html += "" + c.getIdConge() + "<br>";
//		}
		List<Conge> Unav = cs.getUnavailableDate();
		for (Conge c : Unav) {
			html += "Date début: " + c.getDateDebut() + " Date de Fin: " + c.getDateFin() + "<br>";		
		}
		html += "<br>";
		html += "-----------------------------";
		html += "<br>";	
		html += "<br>";		
		
		for(int i = 0; i<Unav.size()-1; i++) {
			int interval = (int) ChronoUnit.DAYS.between(((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(1),((java.sql.Date) Unav.get(i+1).getDateDebut()).toLocalDate());
			html += "Date fin item " + i + " : " + Unav.get(i).getDateFin() + " // Date debut item " + (i+1) + " : " + Unav.get(i+1).getDateDebut() + " // Durée de la periode : " + interval + "<br>";
		}
		
		html += "<br>";
		html += "-----------------------------";
		html += "<br>";	
		html += "<br>";		

		Date current = new Date();
		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());
		List<Conge> Demande = cs.getCongeStartDate(sqlcurrent);
		
		for(Conge dem : Demande) {
			html += "Nom de l'employe : " + dem.getEmploye().getNom() + " Date de debut: " + dem.getDateDebut() + " Date de fin: " + dem.getDateFin() + "<br>";
			for(int i = 0; i<Unav.size()-1; i++) {
				int interval = (int) ChronoUnit.DAYS.between(((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(1),((java.sql.Date) Unav.get(i+1).getDateDebut()).toLocalDate());
				int demvac = (int) ChronoUnit.DAYS.between(((java.sql.Date) dem.getDateDebut()).toLocalDate(),((java.sql.Date) dem.getDateFin()).toLocalDate());
				if(interval < demvac )
				html += dem.getEmploye().getNom()+ " Interval trop petit, " +  demvac + "<br>";
				else {
				LocalDate datedebutprop = ((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(1);
				dem.setDateDebut(java.sql.Date.valueOf(datedebutprop));
				datedebutprop = ((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(demvac);
				dem.setDateFin(java.sql.Date.valueOf(datedebutprop));
				dem.setStatutDeLaDemande("Proposition");
				cs.save(dem);
				html += "Nom: " + dem.getEmploye().getNom()+ " Date de début: " + dem.getDateDebut() + " Date de fin: " + dem.getDateFin() + " Nouveau Statut: " + dem.getStatutDeLaDemande() + "<br>";
				}
			}
		}
		return html;	
	}
}
// List<Conge> html = cs.getStartDate();
// List<Conge> html2 =
//	List<Employe> allEmps = es.getAll();

//		Date current = new Date();
//		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());
//		
//		for (Conge c : cs.getStartDate(sqlcurrent))
//			html += "" + c.getEmploye().getNom()+ "\t" + c.getEmploye().getPrenom()+ "\t" + c.getDateDebut() + "\t" + c.getDateFin() + "\t" + c.getStatutDeLaDemande() +  "<br>";
//		return html;
//	}
//}
//	for (Employe emp : allEmps) {
//		html += (emp);
//	}

//		List<Conge> allCong = cs.getAll();
//		for (Conge Cong : allCong) {
//			Employe emp = es.findById(Cong.getIdConge());
//
//			html += ("Employe: " + emp.getNom() + " " + emp.getPrenom() + ", Date de Début: " + Cong.getDateDebut()
//					+ ", Date de fin: " + Cong.getDateFin() + ", Date de demande: " + Cong.getDateDemande());
//		}

		/*------------------------------*/
//		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date date = dateformat.parse("2019-04-17");
//		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
//
//		date = dateformat.parse("2019-04-22");
//		java.sql.Date sqlFinDate = new java.sql.Date(date.getTime());
//
//		Date current = new Date();
//
//		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());
//		// Calcul le nombre de jour de vacances
//		// Test si weekend ou pas
//		int jourdevac = 0;
//		List<Conge> allDate = cs.getStartDate(sqlcurrent);
//		
//		for (Conge dates : allDate) {
//			html += (dates.getDateDebut() + " ");
//
//			for (LocalDate datetest = ((java.sql.Date) dates.getDateDebut()).toLocalDate(); datetest.isBefore(
//					((java.sql.Date) dates.getDateFin()).toLocalDate().plusDays(1)); datetest = datetest.plusDays(1)) {
//				// html += (datetest.getDayOfWeek());
//				if (datetest.getDayOfWeek() == DayOfWeek.SATURDAY || datetest.getDayOfWeek() == DayOfWeek.SUNDAY) {
//					// html += ("C'est le weekend");
//				} else {
//					jourdevac++;
//					// html += ("C'est pas le weekend :(");
//				}
//			}
//			if (jourdevac > dates.getEmploye().getJoursCongeRestant()) {
//				html += dates.getEmploye().getNom() + " " + "Nombre de jours insufissants demande refusée" + "<br>";
//
//			} else {
//				html += dates.getEmploye().getNom() + " " +"Demande acceptée, Nombre de jour utilisés: " + jourdevac + "<br>" ;
//			}
//
//			jourdevac = 0;
//		}
//	return html;
//	}
//}
		
		/*---------------------------------*/

		//		int jourdevac = 0;
//		for (LocalDate datetest = sqlStartDate.toLocalDate(); datetest
//				.isBefore(sqlFinDate.toLocalDate().plusDays(1)); datetest = datetest.plusDays(1)) {
//			html += (datetest.getDayOfWeek());
//			if (datetest.getDayOfWeek() == DayOfWeek.SATURDAY || datetest.getDayOfWeek() == DayOfWeek.SUNDAY) {
//				html += ("C'est le weekend");
//			} else {
//				jourdevac++;
//				html += ("C'est pas le weekend :(");
//			}
//		}

//		for (Conge cong : allCong) {
//
//			//check if resquest is old or not
//			if (!(cong.getDateDebut().before(sqlcurrent)) && cong.getStatutDeLaDemande().equals("en cours")) {
//
//				html += (cong.getDateDebut() + " " + cong.getDateFin());
//				// check if employee holidays is okay with the database
//				if ((sqlStartDate.after(cong.getDateDebut()) && sqlStartDate.before(cong.getDateFin()))
//						|| (sqlFinDate.after(cong.getDateDebut()) && sqlFinDate.before(cong.getDateFin()))
//						|| (sqlStartDate.equals(cong.getDateDebut()) || sqlFinDate.equals(cong.getDateFin()))) {
//					html += ("Demande refusé");
//					return;
//				}
//				// check if database is okay with the employee holidays
//				if ((cong.getDateDebut().after(sqlStartDate) && cong.getDateDebut().before(sqlFinDate))
//						|| (cong.getDateFin().after(sqlStartDate) && cong.getDateFin().before(sqlFinDate))) {
//					html += ("Demande refusé_2");
//					return;
//				} else {
//					html += ("Demande acceptée");
//				}
//			}
//		}
// check si vacances disponible

//		EmployeDao edao = new EmployeDao();
//		for (Employe emp : edao.getAll()) {
//			System.out.println(emp);
//		}
//		Integer id = 1;
//		log.debug(edao.findById(id));
//		Employe emp = new Employe();
//		emp.setPrenom("Marc");
//		emp.setNom("Heurindélébil");

// test
//	}
