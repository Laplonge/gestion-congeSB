package fr.formation.inti.controller;

import javax.websocket.server.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.inti.dao.ICompteDao;
import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Compte;
import fr.formation.inti.entities.Employe;

@RestController
public class ControlleurRestLogin {

	private static final Log log = LogFactory.getLog(ControlleurRestLogin.class);
	
	@Autowired
	IEmployeDao employeDao;
	@Autowired
	ICompteDao compteDao;
		
	
	//methode appellée quand le on essaie de se connecter avec un login et un mdp.
	@CrossOrigin
	@GetMapping(value="/restlogin/connexion/{login}/{motDePasse}")
	public Employe connexion(@PathVariable("login") String login, @PathVariable("motDePasse") String motDePasse) {
		log.info("login = " + login + " | motDePasse = " + motDePasse);
		Employe employeConnecte = null;
		//erreur si login ou mdp est null.
		if (null == login || null == motDePasse	) {
			log.info("mot de passe ou login null");
		}
		else {
			log.info("recherche d'un employé : login = " + login + ", mot de passe = " + motDePasse);
			//checke si le mdp/login correspond à un compte
			for (Compte compte : compteDao.findAll()) {
				if (login.equals(compte.getLogin()) && motDePasse.equals(compte.getMotDePasse())) {
					log.info("connecté en tant que " + compte.getLogin() + " " + compte.getMotDePasse());
					employeConnecte = compte.getEmploye();
				} 
				else {
					log.info("combinaison login / mot de passe incorrecte");
				}
			}
		}
		return employeConnecte;
	}
	
	@PostMapping("/deconnexion")
	public String destroySession() {
//		TODO
		return null;
	}
}
