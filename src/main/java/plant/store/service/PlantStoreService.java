package plant.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import plant.store.controller.model.CustomerData;
import plant.store.controller.model.EmployeeData;
import plant.store.controller.model.PlantStoreData;
import plant.store.dao.CustomerDao;
import plant.store.dao.EmployeeDao;
import plant.store.dao.PlantStoreDao;
import plant.store.entity.Customer;
import plant.store.entity.Employee;
import plant.store.entity.PlantStore;

@Service
public class PlantStoreService {

	@Autowired
	private PlantStoreDao plantStoreDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	public PlantStoreData savePlantStore(@RequestBody PlantStoreData plantStoreData) {
		Long plantStoreId = plantStoreData.getPlantStoreId();
		PlantStore plantStore = findOrCreatePlantStore(plantStoreId);
		copyPlantStoreFields(plantStore, plantStoreData);
		return new PlantStoreData(plantStoreDao.save(plantStore));
	}

	private void copyPlantStoreFields(PlantStore plantStore, PlantStoreData plantStoreData) {
		plantStore.setPlantStoreId(plantStoreData.getPlantStoreId());
		plantStore.setPlantStoreName(plantStoreData.getPlantStoreName());
		plantStore.setPlantStoreAddress(plantStoreData.getPlantStoreAddress());
		plantStore.setPlantStoreCity(plantStoreData.getPlantStoreCity());
		plantStore.setPlantStoreState(plantStoreData.getPlantStoreState());
		plantStore.setPlantStoreZip(plantStoreData.getPlantStoreZip());
		plantStore.setPlantStorePhone(plantStoreData.getPlantStorePhone());

	}

	private PlantStore findOrCreatePlantStore(Long plantStoreId) {
		if (Objects.isNull(plantStoreId)) {
			return new PlantStore();
		} else {
			return findPlantStoreById(plantStoreId);
		}
	}

	private PlantStore findPlantStoreById(Long plantStoreId) {
		return plantStoreDao.findById(plantStoreId).orElseThrow(
				() -> new NoSuchElementException("Plant store with ID = " + plantStoreId + "was not found"));
	}

	@Transactional(readOnly = false)
	public EmployeeData saveEmployee(Long plantStoreId, EmployeeData employeeData) {
		Long employeeId = employeeData.getEmployeeId();
		PlantStore plantStore = findPlantStoreById(plantStoreId);
		Employee employee = findOrCreateEmployee(plantStoreId, employeeId);
		copyEmployeeFields(employee, employeeData);

		employee.setPlantStore(plantStore);
		plantStore.getEmployees().add(employee);
		Employee databaseEmployee = employeeDao.save(employee);
		return new EmployeeData(databaseEmployee);

	}

	private void copyEmployeeFields(Employee employee, EmployeeData employeeData) {
		employee.setEmployeeId(employeeData.getEmployeeId());
		employee.setEmployeeFirstName(employeeData.getEmployeeFirstName());
		employee.setEmployeeLastName(employeeData.getEmployeeLastName());
		employee.setEmployeePhone(employeeData.getEmployeePhone());
		employee.setEmployeeJobTitle(employeeData.getEmployeeJobTitle());

	}

	private Employee findOrCreateEmployee(Long plantStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(employeeId, plantStoreId);
		}
	}

	private Employee findEmployeeById(Long employeeId, Long plantStoreId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID = " + employeeId + "was not found"));
		if (employee.getPlantStore().getPlantStoreId() != plantStoreId) {
			throw new IllegalArgumentException("Employee with Id= )" + employeeId
					+ " does not have a relatioship with Plant Store with ID = " + plantStoreId);

		}
		return employee;
	}

	@Transactional(readOnly = false)
	public CustomerData saveCustomer(Long plantStoreId, CustomerData customerData) {
		PlantStore plantStore = findPlantStoreById(plantStoreId);
		Long customerId = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(plantStoreId, customerId);
		copyCustomerFields(customer, customerData);
		for (PlantStore ps : customer.getPlantStores()) {
			customer.getPlantStores().add(ps);
		}
		customer.getPlantStores().add(plantStore);
		plantStore.getCustomers().add(customer);
		Customer databaseCustomer = customerDao.save(customer);
		CustomerData customerAsCustomerData = new CustomerData(databaseCustomer);
		return customerAsCustomerData;

	}

	private void copyCustomerFields(Customer customer, CustomerData customerData) {
		customer.setCustomerId(customerData.getCustomerId());
		customer.setCustomerFirstName(customerData.getCustomerFirstName());
		customer.setCustomerLastName(customerData.getCustomerLastName());
		customer.setCustomerEmail(customerData.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long plantStoreId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		}
		return findCustomerById(plantStoreId, customerId);
	}
	
	private Customer findCustomerById (Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID = " + customerId + "was not found"));
		boolean found = false;
		for (PlantStore ps : customer.getPlantStores()) {
			if (ps.getPlantStoreId() == petStoreId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Customer with Id= )" + customerId
					+ " does not have a relationship with Pet Store with ID = " + petStoreId);
		}
		return customer;

	}

	@Transactional(readOnly = true)
	public List<PlantStoreData> retrieveAllPlantStores() {

		List<PlantStore> plantStores = plantStoreDao.findAll();
		List<PlantStoreData> result = new LinkedList<>();
		for (PlantStore plantStore : plantStores) {
			PlantStoreData plantStoreData = new PlantStoreData(plantStore);
			plantStoreData.getEmployees().clear();
			plantStoreData.getCustomers().clear();
			result.add(plantStoreData);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public PlantStoreData retrievePlantStoreById(Long plantStoreId) {
		PlantStore plantStore = findPlantStoreById(plantStoreId);
		return new PlantStoreData(plantStore);
	}

	public void deletePlantStoreById(Long plantStoreId) {
		PlantStore plantStore = findPlantStoreById(plantStoreId);
		plantStoreDao.delete(plantStore);

	}

	public void deleteEmployeeById(Long plantStoreId, Long employeeId) {
		Employee employee = findEmployeeById(employeeId, plantStoreId);
		employeeDao.delete(employee);

	}

}
