package at.fh.swenga.jpa.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.FlightModel;

@Repository
@Transactional
public interface FlightRepository extends JpaRepository<FlightModel, Integer> {
	List<FlightModel> findByOrigin(String origin);

	List<FlightModel> findByDestination(String destination);

	@Query("SELECT e FROM FlightModel e WHERE e.origin = :country OR e.destination = :country")
	List<FlightModel> findByOriginOrDestination(@Param("country") String searchString);

	List<FlightModel> findByNamedQuery(@Param("searchString") String searchString);

	@Query("SELECT COUNT(e) FROM FlightModel e WHERE e.origin = :searchString")
	int countByOrigin(@Param("searchString") String searchString);

	// @Modifying
	// @Transactional
	// @Query("DELETE FROM FlightModel e WHERE e.lastName = :searchString")
	List<FlightModel> removeByOrigin(String searchString); // @Param("searchString") // braucht void

	List<FlightModel> deleteByAircraftModel(String model);

	List<FlightModel> findByOriginContainingOrDestinationContainingAllIgnoreCase(String origin, String destination);

	@Query("SELECT e FROM FlightModel e ORDER BY e.destination ASC")
	List<FlightModel> sortByDestinationAsc();

	// @Query(value = "SELECT e FROM FlightModel e ORDER BY e.lastName LIMIT 10", nativeQuery = true)
	List<FlightModel> findTop10ByOrderByDestinationAsc();
}
