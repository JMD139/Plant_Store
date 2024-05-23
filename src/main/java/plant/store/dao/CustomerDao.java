package plant.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import plant.store.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
