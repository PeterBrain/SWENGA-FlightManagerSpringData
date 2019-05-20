package at.fh.swenga.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

@Entity
public class AircraftModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int aircraftId;

	private String model;

	@OneToMany(mappedBy = "aircraft", fetch = FetchType.LAZY)
	@OrderBy("origin, destination")
	private Set<FlightModel> flights;

	@Version
	long version;

	public AircraftModel() {}

	public AircraftModel(String model) {
		super();
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Set<FlightModel> getFlights() {
		return flights;
	}

	public void setFlights(Set<FlightModel> flights) {
		this.flights = flights;
	}

	public void addFlight(FlightModel flight) {
		if (flights == null) {
			flights = new HashSet<FlightModel>();
		}
		flights.add(flight);
	}

}
