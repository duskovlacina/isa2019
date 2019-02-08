package company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.AvioCompany;

@Repository
public interface AvioCompanyRepository extends JpaRepository<AvioCompany,Long> {
	
	List<AvioCompany> findByNameContaining(String name);
	
	List<AvioCompany> findByAddressContaining(String address);
	
	List<AvioCompany> findByNameContainingAndAddressContaining(String name, String address);
	
}
