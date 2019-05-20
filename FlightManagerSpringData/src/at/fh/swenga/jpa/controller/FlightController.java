package at.fh.swenga.jpa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.AircraftRepository;
import at.fh.swenga.jpa.dao.FlightRepository;
import at.fh.swenga.jpa.model.AircraftModel;
import at.fh.swenga.jpa.model.FlightModel;

@Controller
public class FlightController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	AircraftRepository aircraftRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<FlightModel> flights = flightRepository.findAll();
		model.addAttribute("flights", flights);
		model.addAttribute("count", flights.size());
		return "index";
	}

	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page, Model model) {
		Page<FlightModel> flightsPage = flightRepository.findAll(page);
		model.addAttribute("flightsPage", flightsPage);
		model.addAttribute("flights", flightsPage.getContent());
		model.addAttribute("count", flightsPage.getTotalElements());
		return "index";
	}

	@RequestMapping(value = { "/find" })
	public String find(Model model, @RequestParam String searchString, @RequestParam String searchType) {
		List<FlightModel> flights = null;
		int count = 0;

		switch (searchType) {
		case "query1":
			flights = flightRepository.findAll();
			break;
		case "query2":
			flights = flightRepository.findByOrigin(searchString);
			break;
		case "query3":
			flights = flightRepository.findByDestination(searchString);
			break;
		case "query4":
			flights = flightRepository.findByOriginOrDestination(searchString);
			break;
		case "query5":
			flights = flightRepository.findByNamedQuery(searchString);
			break;
		case "query6":
			count = flightRepository.countByOrigin(searchString);
			break;
		case "query7":
			flights = flightRepository.removeByOrigin(searchString);
			break;
		case "query8":
			flights = flightRepository.deleteByAircraftModel(searchString);
			break;
		case "query9":
			flights = flightRepository.findByOriginContainingOrDestinationContainingAllIgnoreCase(searchString, searchString);
			break;
		case "query10":
			flights = flightRepository.sortByDestinationAsc();
			break;
		case "query11":
			flights = flightRepository.findTop10ByOrderByDestinationAsc();
			break;
		default:
			flights = flightRepository.findAll();
		}

		model.addAttribute("flights", flights);

		if (flights != null) {
			model.addAttribute("count", flights.size());
		} else {
			model.addAttribute("count", count);
		}
		return "index";
	}

	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") FlightModel e, Model model) {
		if (e != null) {
			List<FlightModel> flights = new ArrayList<FlightModel>();
			flights.add(e);
			model.addAttribute("flights", flights);
		}
		return "index";
	}

	@RequestMapping("/fillFlightList")
	@Transactional
	public String fillData(Model model) {

		/*AircraftModel aircraft = null;

		for (int i = 0; i < aircrafts.length; i++) {
			if (i % 10 == 0) {
				String aircraftModel = aircrafts[i];
				aircraft = aircraftRepository.findFirstByModel(aircraftModel);
				
				if (aircraft == null) {
					aircraft = new AircraftModel(aircraftModel);
				}
			}
		}*/

		/*FlightModel flightModel = new FlightModel();
		flightModel.setAircraft(aircraft);
		flightRepository.save(flightModel);*/
		
		Date now = new Date();
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.save(new AircraftModel("Boeing 737")), "Germany", "Spain", now, now, 148, "Lufthansa", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.save(new AircraftModel("Boeing 767")), "Austria", "Belgium", now, now, 72, "Austrian", true));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.save(new AircraftModel("Boeing 777")), "France", "Portugal", now, now, 65, "Eurowings", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.save(new AircraftModel("Airbus A380")), "Germany", "Thailand", now, now, 225, "Emirates", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.findFirstByModel("Boeing 767"), "Switzerland", "Germany", now, now, 103, "Swiss", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.save(new AircraftModel("Airbus A320")), "Spain", "Portugal", now, now, 62, "Iberia", true));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.save(new AircraftModel("Boeing 747")), "France", "Sweden", now, now, 98, "Norwegian", false));
		flightRepository.save(new FlightModel(flightRepository.findAll().size() + 1, aircraftRepository.findFirstByModel("Boeing 737"), "Sweden", "Spain", now, now, 46, "Iberia", false));

		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		flightRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
}
