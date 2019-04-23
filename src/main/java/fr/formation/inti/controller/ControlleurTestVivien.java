package fr.formation.inti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.inti.dao.ICompteDao;
import fr.formation.inti.dao.IEmployeDao;
import fr.formation.inti.entities.Compte;
import fr.formation.inti.entities.Employe;

@Controller
public class ControlleurTestVivien {
	@Autowired
	IEmployeDao employeDao;
	@Autowired
	ICompteDao compteDao;
	
	//récupère la liste de tous les employés, la passe en attribut du modèle listeEmployes.html et l'affiche 
	@GetMapping(value = { "/listeEmployes", "/employes", "/allEmps" })
    public String personList(Model model) {
        model.addAttribute("employes", employeDao.findAll());
        return "listeEmployes";
    }
	
	//page d'accueil du login
	@GetMapping("/")
	public String testLogin(Model model, HttpSession session) {
		//récupère l'attribut de session pour le renvoyer en attribut du modèle
		String loginSession = (String) session.getAttribute("loginSession");
		//valeur par défaut de l'attribut loginSession, si personne n'est connecté
		if (loginSession == null) {
			loginSession = "personne";
		} 
		model.addAttribute("loginSession", loginSession);
		
		//message d'erreur si il y en a un. ça plante pas si il est null.
		model.addAttribute("messageErreur", (String) session.getAttribute("messageErreur"));
		return "login";
		
	} 
	
	//methode appellée quand le on essaie de se connecter avec un login et un mdp.
	@PostMapping("/connexion")
	public String connexion(@RequestParam("login") String login, @RequestParam("motDePasse") String motDePasse, HttpServletRequest request) {
		
		//erreur si login ou mdp est null.
		if (login.equals("") || motDePasse.equals("")) {
			request.getSession().setAttribute("messageErreur", "Veuillez entrer un login et un mot de passe.");
		}
		else {
			String loginSession = null;
			//checke si le mdp/login correspond à un compte
			for (Compte compte : compteDao.findAll()) {
				if (login.equals(compte.getLogin()) && motDePasse.equals(compte.getMotDePasse())) {
					
					request.getSession().setAttribute("messageErreur", "Connexion réussie");
					request.getSession().setAttribute("testAttribute", "test /connexion");
					loginSession = compte.getEmploye().getPrenom() + " " + compte.getEmploye().getNom();
					request.getSession().setAttribute("loginSession", loginSession);
				} 
			}
			if (loginSession == null) {
				request.getSession().setAttribute("messageErreur", "Login inconnu, ou mot de passe incorrect.");
			}
		}
		return "redirect:/";
	}
	
	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		request.getSession().setAttribute("messageErreur", "Session invalidée");
		return "redirect:/";
	}
}
