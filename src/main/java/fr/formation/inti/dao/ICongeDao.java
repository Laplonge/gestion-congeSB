package fr.formation.inti.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.inti.entities.Conge;

public interface ICongeDao extends JpaRepository<Conge, Integer> {
	
}
