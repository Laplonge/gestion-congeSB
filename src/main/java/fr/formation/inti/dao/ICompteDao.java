package fr.formation.inti.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.inti.entities.Compte;

public interface ICompteDao extends JpaRepository<Compte, Integer> {
	
}
