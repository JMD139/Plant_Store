package plant.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import plant.store.entity.Employee;

@Data
@NoArgsConstructor
public class EmployeeData {
	Long employeeId;
	

	String employeeFirstName;
	String employeeLastName;
	String employeePhone;
	String employeeJobTitle;

	public EmployeeData(Employee employee) {
		this.employeeId = employee.getEmployeeId();
		this.employeeFirstName = employee.getEmployeeFirstName();
		this.employeeLastName = employee.getEmployeeLastName();
		this.employeePhone = employee.getEmployeePhone();
		this.employeeJobTitle = employee.getEmployeeJobTitle();
	}

}
