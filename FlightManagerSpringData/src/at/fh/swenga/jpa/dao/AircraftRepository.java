package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.AircraftModel;

@Repository

public interface AircraftRepository extends JpaRepository<AircraftModel, Integer> {
	@Transactional
	AircraftModel findFirstByModel(String model);
}
