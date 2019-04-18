package fr.formation.inti.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.inti.entities.Employe;

public interface IEmployeDao extends JpaRepository<Employe, Integer> {
	
}
