package fr.formation.inti.services.interfaces;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T> {

	Optional<T> findById(Integer id);

	List<T> getAll();

	void save(T newInstance);

	void delete(T persistentObject);
}