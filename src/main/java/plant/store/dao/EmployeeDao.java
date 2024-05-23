package plant.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import plant.store.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
