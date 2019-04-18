package fr.formation.inti.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.formation.inti.entities.Conge;

public interface ICongeDao extends JpaRepository<Conge, Integer> {
	
	@Query(value="SELECT * FROM conge", nativeQuery = true)
	List<Conge> getStartDate(Date date);
	// WHERE date_debut > :date AND statut_de_la_demande = 'en cours';", nativeQuery = true
	//@Param(value="date")
}
