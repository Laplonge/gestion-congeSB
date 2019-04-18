package fr.formation.inti.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.inti.entities.Projet;

public interface IProjetDao extends JpaRepository<Projet, Integer> {
	
}
