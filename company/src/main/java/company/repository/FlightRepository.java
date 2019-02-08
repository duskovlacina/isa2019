package company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long>{

}
