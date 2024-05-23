package plant.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import plant.store.entity.PlantStore;

public interface PlantStoreDao extends JpaRepository<PlantStore, Long> {

}
