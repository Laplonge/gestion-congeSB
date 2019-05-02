package fr.formation.inti.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.formation.inti.entities.Conge;

public interface ICongeDao extends JpaRepository<Conge, Integer> {
	//@Query(nativeQuery = true, value = "SELECT * FROM conge c")
	//@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.date_Debut > :date")
	
	// Pour le boss
	// Query utilisee pour le manager, elle permet d'afficher toutes les demandes en cours faites par les employés
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.date_Debut > :date AND c.statut_De_La_Demande = 'en cours'")
	List<Conge> getCongeStartDate(@Param("date")Date date);
	//List<Conge> getStartDate();
	
	//Calendrier
	// Query qui permet de retourner les toutes les dates des demandes déjà acceptée dans le but de les envoyer dans le calendrier 
	// pour déactiver les dates non disponible
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.Statut_de_la_demande = 'acceptee' AND c.date_Debut > :date ORDER BY c.Date_Debut ASC")
	List<Conge> getUnavailableDate(@Param("date")Date date);
	
	// Employe 
	// retourne toutes les propositions 
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.id_employe = :id AND c.Statut_de_la_demande = 'Proposition' ORDER BY c.Date_Demande ASC")
	List<Conge> getPropositionByIdEmploye(@Param("id")Integer id);
	
	// Employee
	// Query qui permet d'afficher toutes les demandes faite par l'employé dans le but d'avoir un historique des demandes en cours, refusee et acceptee
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.id_employe = :id AND c.Statut_de_la_demande <> 'Proposition' ORDER BY c.Date_Demande ASC")
	List<Conge> getHistoriqueByIdEmploye(@Param("id")Integer id);

	
	
//	@Query(nativeQuery = true, value = "UPDATE employe\r\n" + 
//			"SELECT * FROM Conge c WHERE c.id_employe = employe.ID_EMPLOYE"
//			+ "SET employe.JOURS_CONGE_RESTANT = employe.JOURS_CONGE_RESTANT-conge.DUREE_JOURS\r\n" + 
//			"WHERE employe.ID_EMPLOYE = conge.ID_EMPLOYE")
//	void employeUpdate();
}


