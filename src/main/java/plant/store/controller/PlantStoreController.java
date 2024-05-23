package plant.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import plant.store.controller.model.CustomerData;
import plant.store.controller.model.EmployeeData;
import plant.store.controller.model.PlantStoreData;
import plant.store.service.PlantStoreService;

@RestController
@RequestMapping("/plant_store")
@Slf4j
public class PlantStoreController {

	@Autowired
	private PlantStoreService plantStoreService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlantStoreData insertPlantStore(@RequestBody PlantStoreData plantStoreData) {
		log.info("Creating plant store {}", plantStoreData);
		return plantStoreService.savePlantStore(plantStoreData);
	}

	@PutMapping("/{plantStoreId}")
	public PlantStoreData updatePlantStore(@PathVariable Long plantStoreId,
			@RequestBody PlantStoreData plantStoreData) {
		plantStoreData.setPlantStoreId(plantStoreId);
		log.info("Updating plant store with id: {}", plantStoreId);
		return plantStoreService.savePlantStore(plantStoreData);
	}

	@PostMapping("/{plantStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData addEmployeeToStore(@PathVariable Long plantStoreId, @RequestBody EmployeeData employeeData) {
		log.info("Adding employee with id: {} to store with id: {}", plantStoreId, employeeData);
		return plantStoreService.saveEmployee(plantStoreId, employeeData);
	}

	@PostMapping("/{plantStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerData addCustomerToStore(@PathVariable Long plantStoreId, @RequestBody CustomerData customerData) {
		log.info("Adding customer to store with id: {}", plantStoreId);
		return plantStoreService.saveCustomer(plantStoreId, customerData);
	}

	@GetMapping
	public List<PlantStoreData> retrieveAllPlantStores() {
		log.info("Retrieving all plant stores");
		return plantStoreService.retrieveAllPlantStores();

	}

	@GetMapping("/{plantStoreId}")
	public PlantStoreData retrievePetStoreById(@PathVariable Long plantStoreId) {
		log.info("Retrieving plant store by id: {}", plantStoreId);
		return plantStoreService.retrievePlantStoreById(plantStoreId);
	}

	@DeleteMapping("/{plantStoreId}")
	public Map<String, String> deletePlantStoreById(@PathVariable Long plantStoreId) {
		log.info("attempting to delete plant store with id: {}", plantStoreId);
		plantStoreService.deletePlantStoreById(plantStoreId);
		return Map.of("message", "Plant Store with id: " + plantStoreId + " was deleted");
	}

	@DeleteMapping("/{plantStoreId}/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(@PathVariable Long plantStoreId, @PathVariable Long employeeId) {
		log.info("attempting to delete employee with id: {} from plant store with id: {}", employeeId, plantStoreId);
		plantStoreService.deleteEmployeeById(plantStoreId, employeeId);
		return Map.of("message",
				"Employee with id: " + employeeId + " was deleted from pet store with id: " + plantStoreId);
	}

}
