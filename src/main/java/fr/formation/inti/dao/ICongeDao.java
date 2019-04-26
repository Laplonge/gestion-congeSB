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
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.date_Debut > :date AND c.statut_De_La_Demande = 'en cours'")
	List<Conge> getCongeStartDate(@Param("date")Date date);
	//List<Conge> getStartDate();
	
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.Statut_de_la_demande = 'acceptee' AND c.date_Debut > :date ORDER BY c.Date_Debut ASC")
	List<Conge> getUnavailableDate(@Param("date")Date date);
	
	@Query(nativeQuery = true, value = "SELECT * FROM Conge c WHERE c.id_employe = :id ORDER BY c.Date_Demande ASC")
	List<Conge> getCongeByIdEmploye(@Param("id")Integer id);
}


