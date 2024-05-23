package plant.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import plant.store.entity.Customer;
import plant.store.entity.Employee;
import plant.store.entity.PlantStore;

@Data
@NoArgsConstructor
public class PlantStoreData {
	
	private Long plantStoreId;

	private String plantStoreName;
	private String plantStoreAddress;
	private String plantStoreCity;
	private String plantStoreState;
	private String plantStoreZip;
	private String plantStorePhone;
	private Set<PlantStoreCustomer> customers = new HashSet<>();
	private Set<PlantStoreEmployee> employees = new HashSet<>();

	public PlantStoreData(PlantStore plantStore) {
		plantStoreId = plantStore.getPlantStoreId();
		plantStoreName = plantStore.getPlantStoreName();
		plantStoreAddress = plantStore.getPlantStoreAddress();
		plantStoreCity = plantStore.getPlantStoreCity();
		plantStoreState = plantStore.getPlantStoreState();
		plantStoreZip = plantStore.getPlantStoreZip();
		plantStorePhone = plantStore.getPlantStorePhone();

		for (Customer customer : plantStore.getCustomers()) {
			customers.add(new PlantStoreCustomer(customer));

		}

		for (Employee employee : plantStore.getEmployees()) {
			employees.add(new PlantStoreEmployee(employee));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class PlantStoreCustomer {
		private Long customerId;

		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;

		public PlantStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class PlantStoreEmployee {
		private Long employeeId;

		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;

		public PlantStoreEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}

}
