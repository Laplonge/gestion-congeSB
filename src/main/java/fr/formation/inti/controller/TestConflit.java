package fr.formation.inti.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
public class TestConflit {
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
		Date current = new Date();
		java.sql.Date sqlcurrent = new java.sql.Date(current.getTime());
		
		List<Conge> Unav = cs.getUnavailableDate(sqlcurrent);
		for (Conge c : Unav) {
			html += "Date début: " + c.getDateDebut() + " Date de Fin: " + c.getDateFin() + "<br>";
		}
		html += "<br>";
		html += "-----------------------------";
		html += "<br>";
		html += "<br>";

		Optional<Employe> emp = es.findById(16);
		emp.get().setUrlPhoto("../photos/uzi.jpg");
		es.save((Employe) emp.get());
		// return html += "<img src=\"" + emp.get().getUrlPhoto() + "\"/>";
		// return html += "<img src=
		// \"/gestion-congeSB/src/main/resources/photos/pikachu.jpg\"/>";
		// return html += "<img src= \"../photos/pikachu.jpg\"/>";

//		Optional<Conge> conge = cs.findById(4);
//		cs.calculDureeVacances(conge.get());
//		return cs.validationDeLaPeriode(conge.get());

//	List<Conge> allCong = cs.getCongeByIdEmploye(1);
//	for (Conge Cong : allCong) {
//		html += ("Employe: " + Cong.getEmploye().getNom() + " " + Cong.getEmploye().getPrenom() + ", Date de Début: " + Cong.getDateDebut()
//				+ ", Date de fin: " + Cong.getDateFin() + ", Date de demande: " + Cong.getDateDemande()) + "<br>";
//	}

		for (int i = 0; i < Unav.size() - 1; i++) {
			int interval = (int) ChronoUnit.DAYS.between(
					((java.sql.Date) Unav.get(i).getDateFin()).toLocalDate().plusDays(1),
					((java.sql.Date) Unav.get(i + 1).getDateDebut()).toLocalDate());
			html += "Date fin item " + i + " : " + Unav.get(i).getDateFin() + " // Date debut item " + (i + 1) + " : "
					+ Unav.get(i + 1).getDateDebut() + " // Durée de la periode : " + interval + "<br>";
		}

		html += "<br>";
		html += "-----------------------------";
		html += "<br>";
		html += "<br>";
		html += cs.TestDeLaValiditeDeLaRequete("2019-07-22", "2019-07-26");
		//html += cs.getUnavailableDate();
		return html;
	}
}
